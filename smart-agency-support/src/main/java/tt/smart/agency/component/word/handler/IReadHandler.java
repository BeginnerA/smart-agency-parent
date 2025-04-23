package tt.smart.agency.component.word.handler;

/**
 * <p>
 * 读取处理器
 * </p>
 *
 * @param <T>
 * @author MC_Yang
 * @version V1.0
 **/
public interface IReadHandler<T> {

    /**
     * 处理解析对象
     *
     * @param t T
     */
    void handler(T t);


    /**
     * 处理完成之后的业务
     */
    void doAfterAll();

}
