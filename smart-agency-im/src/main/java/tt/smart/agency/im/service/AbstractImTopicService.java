package tt.smart.agency.im.service;

import java.util.Set;

/**
 * <p>
 * 抽象的即时通讯主题
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public abstract class AbstractImTopicService implements ImTopicService {



    @Override
    public void subscribe(String topic, String tag) {

    }

    @Override
    public void subscribeAll(Set<String> topics, String tag) {

    }

    @Override
    public void unsubscribe(String topic, String tag) {

    }

    @Override
    public void unsubscribeAll(Set<String> topics, String tag) {

    }

    @Override
    public Set<String> getSubscribeTopicList(String tag) {
        return Set.of();
    }

    @Override
    public tt.smart.agency.im.domain.ImTopic getSocketTopic(String topic) {
        return null;
    }

    @Override
    public void destroyTopic(String tag) {

    }
}
