package tt.smart.agency.component.word.handler;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典处理器
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface IExcelDictHandler {

    /**
     * 返回字典所有值
     * key: dictKey
     * value: dictValue
     *
     * @param dict 字典 Key
     * @return 字典所有值
     */
    default List<Map<?, ?>> getList(String dict) {
        return null;
    }

    /**
     * 从值翻译到名称
     *
     * @param dict  字典Key
     * @param obj   对象
     * @param name  属性名称
     * @param value 属性值
     * @return 翻译结果
     */
    String toName(String dict, Object obj, String name, Object value);

    /**
     * 从名称翻译到值
     *
     * @param dict  字典Key
     * @param obj   对象
     * @param name  属性名称
     * @param value 属性值
     * @return 翻译结果
     */
    String toValue(String dict, Object obj, String name, Object value);

}
