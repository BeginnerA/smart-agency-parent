package tt.smart.agency.component.word.cache.manager;

/**
 * <p>
 * 缓存读取
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface IFileLoader {
    /**
     * 可以自定义 KEY 的作用
     *
     * @param key key
     * @return 内容字节
     */
    byte[] getFile(String key);

}
