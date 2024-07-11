package tt.smart.agency.component.word.handler;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;

import java.util.Map;

/**
 * <p>
 * Excel 导入导出 数据处理器
 * </p>
 *
 * @param <T> T
 * @author YangMC
 * @version V1.0
 **/
public interface IExcelDataHandler<T> {

    /**
     * 获取需要处理的字段，导入和导出统一处理了，减少书写的字段
     *
     * @return 字段列表
     */
    String[] getNeedHandlerFields();

    /**
     * 导出处理方法
     *
     * @param obj   当前对象
     * @param name  当前字段名称
     * @param value 当前值
     * @return 结果
     */
    Object exportHandler(T obj, String name, Object value);

    /**
     * 导入处理方法
     *
     * @param obj   当前对象
     * @param name  当前字段名称
     * @param value 当前值
     * @return 结果
     */
    Object importHandler(T obj, String name, Object value);

    /**
     * 设置需要处理的属性列表
     *
     * @param fields 字段列表
     */
    void setNeedHandlerFields(String[] fields);

    /**
     * 设置 Map 导入，自定义 put
     *
     * @param map       map
     * @param originKey key
     * @param value     value
     */
    void setMapValue(Map<String, Object> map, String originKey, Object value);

    /**
     * 获取这个字段的 Hyperlink，07版本需要，03版本不需要
     *
     * @param creationHelper CreationHelper
     * @param obj            当前对象
     * @param name           名称
     * @param value          值
     * @return Hyperlink
     */
    Hyperlink getHyperlink(CreationHelper creationHelper, T obj, String name, Object value);

}
