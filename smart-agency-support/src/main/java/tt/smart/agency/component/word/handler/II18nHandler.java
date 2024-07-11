package tt.smart.agency.component.word.handler;

/**
 * <p>
 * 国际化处理器
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public interface II18nHandler {

    /**
     * 获取当前名称
     *
     * @param name 注解配置的
     * @return 返回国际化的名字
     */
    String getLocaleName(String name);

}
