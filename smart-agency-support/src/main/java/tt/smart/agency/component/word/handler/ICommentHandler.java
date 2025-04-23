package tt.smart.agency.component.word.handler;

/**
 * <p>
 * 批注接口
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface ICommentHandler {

    /**
     * 获取作者
     *
     * @return 作者
     */
    default String getAuthor() {
        return null;
    }

    /**
     * 获取当前列名称的批注
     *
     * @param name 列名称
     * @return 批注内容
     */
    default String getComment(String name) {
        return null;
    }

    /**
     * 获取当前 Cell 的批注
     *
     * @param name 注列名称
     * @param val  属性值
     * @return 批注内容
     */
    default String getComment(String name, Object val) {
        return null;
    }

}
