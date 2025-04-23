package tt.smart.agency.message.component.strategy;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import tt.smart.agency.message.component.config.DefaultMessagePushConfig;
import tt.smart.agency.message.component.config.MessagePushConfig;
import tt.smart.agency.message.component.context.ParameterContextModel;
import tt.smart.agency.message.component.domain.Message;
import tt.smart.agency.message.component.domain.MessageTemplateConfig;
import tt.smart.agency.message.component.listener.DefaultMessageLogListener;
import tt.smart.agency.message.component.listener.MessageLogListener;
import tt.smart.agency.message.domain.MessageSendResult;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * 消息推送服务工厂
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Slf4j
public class MessagePushStrategyFactory {

    private final MessageAsyncProvider messageAsyncProvider;

    public MessagePushStrategyFactory(MessageAsyncProvider messageAsyncProvider) {
        this.messageAsyncProvider = messageAsyncProvider;
    }

    /**
     * 推送信息
     *
     * @param strategy         通知策略
     * @param parameterContext 参数上下文
     * @param isAsync          是异步通知
     */
    public void pushMessage(Class<? extends NotificationStrategy> strategy, ParameterContextModel parameterContext, boolean isAsync) {
        if (isAsync) {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                pushMessage(strategy, parameterContext);
                return "异步消息通知执行完成";
            }, messageAsyncProvider);
            future.join();
        } else {
            pushMessage(strategy, parameterContext);
        }
    }

    /**
     * 推送信息
     *
     * @param strategy         通知策略
     * @param parameterContext 参数上下文
     */
    public void pushMessage(Class<? extends NotificationStrategy> strategy, ParameterContextModel parameterContext) {
        List<NotificationTemplate> notificationTemplates = getNotificationTemplates(strategy, parameterContext);
        messagePush(notificationTemplates);
    }

    /**
     * 获取通知模板
     *
     * @param strategy         通知策略
     * @param parameterContext 参数上下文
     * @return 通知模板列表
     */
    private List<NotificationTemplate> getNotificationTemplates(Class<? extends NotificationStrategy> strategy,
                                                                ParameterContextModel parameterContext) {
        NotificationStrategy notificationStrategy = getNotificationStrategy(strategy);
        boolean isCustomNotificationStrategy = !(notificationStrategy instanceof DefaultNotificationStrategy);
        String strategyName = notificationStrategy.getClass().getSimpleName();
        MessageTemplateConfig templateConfig = parameterContext.getMessageTemplateConfig();
        List<MessageTemplateConfig> messageTemplateConfigList;
        if (templateConfig == null) {
            messageTemplateConfigList = getMessageTemplateConfig(strategyName);
        } else {
            messageTemplateConfigList = new ArrayList<>() {{
                add(templateConfig);
            }};
        }
        List<NotificationTemplate> notificationTemplates = new ArrayList<>();
        if (isCustomNotificationStrategy && messageTemplateConfigList.isEmpty()) {
            NotificationTemplate notificationTemplate = executeNotificationStrategy(notificationStrategy,
                    MessageTemplateConfig.builder().build(), parameterContext);
            if (notificationTemplate != null) {
                notificationTemplates.add(notificationTemplate);
            }
        } else {
            for (MessageTemplateConfig messageTemplateConfig : messageTemplateConfigList) {
                String[] idents = parameterContext.getIdents();
                if (idents != null && idents.length > 0) {
                    boolean contains = List.of(idents).contains(messageTemplateConfig.getIdent());
                    if (!contains) {
                        continue;
                    }
                }
                if (messageTemplateConfig.checkBasicIntegrity()) {
                    NotificationTemplate notificationTemplate = executeNotificationStrategy(notificationStrategy,
                            messageTemplateConfig, parameterContext);
                    if (notificationTemplate != null) {
                        notificationTemplates.add(notificationTemplate);
                    }
                } else {
                    log.error("消息[{}]->执行策略[{}]->消息配置标准基本完整性检查未通过，消息内容、推送策略、" +
                            "如果推送策略是短信会检查模板 ID", messageTemplateConfig.getIdent(), strategyName);
                }
            }
        }

        return notificationTemplates;
    }

    /**
     * 执行通知策略
     *
     * @param notificationStrategy  通知策略
     * @param messageTemplateConfig 消息模板配置
     * @param parameterContext      参数上下文模型
     * @return 通知模板
     */
    private NotificationTemplate executeNotificationStrategy(NotificationStrategy notificationStrategy,
                                                             MessageTemplateConfig messageTemplateConfig,
                                                             ParameterContextModel parameterContext) {
        parameterContext.setMessageTemplateConfig(messageTemplateConfig);
        notificationStrategy.setParameterContext(parameterContext);
        String strategyName = notificationStrategy.getClass().getSimpleName();
        log.info("开始执行消息推送策略：消息[{}]->执行策略[{}]", messageTemplateConfig.getIdent(), strategyName);
        NotificationTemplate notification = null;
        try {
            NotificationTemplate notificationTemplate = getNotificationTemplate(messageTemplateConfig);
            notification = notificationStrategy.sendNotification(notificationTemplate, parameterContext);
            if (notification != null) {
                if (!notification.checkIntegrity()) {
                    log.error("消息策略[{}]推送消息失败：消息重要内容缺失（如：推送平台、消息正文等）", strategyName);
                    throw new RuntimeException("消息重要内容缺失（如：推送平台、消息正文等）");
                }
            }
        } catch (Exception e) {
            log.error("执行消息推送策略失败：消息[{}]->执行策略[{}]", messageTemplateConfig.getIdent(), strategyName);
            printError(e);
        }
        return notification;
    }

    /**
     * 获取通知模板
     *
     * @param messageTemplateConfig 消息模板配置
     * @return 通知模板
     */
    private NotificationTemplate getNotificationTemplate(MessageTemplateConfig messageTemplateConfig) {
        NotificationTemplate notificationTemplate = new NotificationTemplate();
        notificationTemplate.addPlatform(messageTemplateConfig.getPlatform());
        Message message = Message.builder().build();
        message.setContent(messageTemplateConfig.getContent());
        message.setAddressee(messageTemplateConfig.getRecipients());
        notificationTemplate.addMessages(message);
        return notificationTemplate;
    }

    /**
     * 获取消息模板配置消息列表
     *
     * @param notificationStrategyClassName 通知策略类名称
     * @return 消息模板配置消息列表
     */
    private List<MessageTemplateConfig> getMessageTemplateConfig(String notificationStrategyClassName) {
        char separator = '$';
        if (StrUtil.contains(notificationStrategyClassName, separator)) {
            notificationStrategyClassName = CharSequenceUtil.subBefore(notificationStrategyClassName, separator, false);
        }
        List<MessageTemplateConfig> messageTemplateConfig = getMessagePushConfig().getMessageTemplateConfig(notificationStrategyClassName);
        return messageTemplateConfig == null ? new ArrayList<>() : messageTemplateConfig;
    }

    /**
     * 消息推送
     *
     * @param notificationTemplates 通知模板列表
     */
    private void messagePush(List<NotificationTemplate> notificationTemplates) {
        if (notificationTemplates != null) {
            try {
                log.info("开始推送消息。。。。。。");
                Map<MessagePushPlatformEnum, Map<Message, MessageSendResult>> messageLog = new HashMap<>();
                for (NotificationTemplate notificationTemplate : notificationTemplates) {
                    Set<MessagePushPlatformEnum> platformSet = notificationTemplate.getPlatformSet();
                    for (MessagePushPlatformEnum platform : platformSet) {
                        MessagePushStrategy messagePushStrategy = getMessagePushStrategy(platform);
                        List<Message> msgList = notificationTemplate.getMessagesList();
                        log.info("推送消息：策略[{}]->消息数量[{}]", platform.getDescription(), msgList.size());
                        Map<Message, MessageSendResult> resultMap = new HashMap<>(msgList.size());
                        for (Message message : msgList) {
                            MessageSendResult result = messagePushStrategy.sendMessage(platform.getPlatformTypeFactory(platform), message);
                            resultMap.put(message, result);
                        }
                        messageLog.put(platform, resultMap);
                    }
                }
                executeMessageLogListener(messageLog);
            } catch (Exception e) {
                printError(e);
            }
        }
    }

    /**
     * 获取消息推送策略
     *
     * @param platform 消息推送平台枚举
     * @return 消息推送策略
     */
    private MessagePushStrategy getMessagePushStrategy(MessagePushPlatformEnum platform) {
        return switch (platform) {
            case NETTY, WEB_SOCKET, SOCKET_IO -> new NettyMessagePushStrategy();
            case MP_TEMPLATE, MP_SUBSCRIBE, CP_APPLY -> new WxMessagePushStrategy();
            case DD_APPLY -> new DdMessagePushStrategy();
            case ALIBABA, HUAWEI, TENCENT, CTYUN, NETEASE -> new SmsMessagePushStrategy();
            case CUSTOM -> new CustomPushStrategy();
        };
    }

    /**
     * 获取通知策略
     *
     * @param strategy 定义通知策略 class
     * @return 通知策略
     */
    private NotificationStrategy getNotificationStrategy(Class<? extends NotificationStrategy> strategy) {
        NotificationStrategy notificationStrategy;
        try {
            notificationStrategy = SpringUtil.getBean(strategy);
        } catch (Exception e) {
            notificationStrategy = ReflectUtil.newInstance(strategy);
        }
        return notificationStrategy;
    }

    /**
     * 执行消息日志监听器
     *
     * @param messageLog 消息日志
     */
    private void executeMessageLogListener(Map<MessagePushPlatformEnum, Map<Message, MessageSendResult>> messageLog) {
        try {
            log.info("开始调用消息推送日志监听器。。。。。。");
            MessageLogListener messageLogListener = getMessageLog();
            messageLog.forEach((k, v) -> v.forEach((m, r) -> messageLogListener.log(k, m, r)));
        } catch (Exception e) {
            log.error("调用消息日志监听器失败：", e);
        }
    }

    /**
     * 获取消息推送配置实现
     *
     * @return 消息推送配置实现
     */
    private MessagePushConfig getMessagePushConfig() {
        try {
            return SpringUtil.getBean(MessagePushConfig.class);
        } catch (BeansException e) {
            return new DefaultMessagePushConfig();
        }
    }

    /**
     * 获取消息日志实现
     *
     * @return 消息日志实现
     */
    private MessageLogListener getMessageLog() {
        try {
            return SpringUtil.getBean(MessageLogListener.class);
        } catch (BeansException e) {
            log.info("在 spring 中未找到任何实现[MessageLogListener]接口的 bean");
            return new DefaultMessageLogListener();
        }
    }

    /**
     * 打印错误日志信息
     *
     * @param e 错误
     */
    private void printError(Exception e) {
        log.error("消息推送失败：", e);
    }
}