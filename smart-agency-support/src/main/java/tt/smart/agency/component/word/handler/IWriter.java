package tt.smart.agency.component.word.handler;

import java.util.Collection;

/**
 * <p>
 * 大数据写出服务接口
 * </p>
 *
 * @param <T> T
 * @author YangMC
 * @version V1.0
 **/
public interface IWriter<T> {

    /**
     * 获取输出对象
     *
     * @return T
     */
    default T get() {
        return null;
    }

    /**
     * 写入数据
     *
     * @param data 数据
     * @return IWriter<T>
     */
    IWriter<T> write(Collection<T> data);

    /**
     * 关闭流，完成业务
     *
     * @return T
     */
    T close();

}
