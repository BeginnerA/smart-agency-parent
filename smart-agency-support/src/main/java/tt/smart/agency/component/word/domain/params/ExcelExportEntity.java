package tt.smart.agency.component.word.domain.params;

import cn.hutool.core.util.DesensitizedUtil;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * Excel导出实体，对 cell 类型做映射
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Data
public class ExcelExportEntity extends ExcelBaseEntity implements Comparable<ExcelExportEntity> {

    /**
     * 如果是 map 导出，这个是 map 的 key
     */
    private Object key;
    /**
     * 宽度
     */
    private double width = 10;
    /**
     * 高度
     */
    private double height = 10;
    /**
     * 图片的类型,1是文件,2是数据库
     */
    private int exportImageType = 0;
    /**
     * 排序顺序
     */
    private int orderNum = 0;
    /**
     * 是否支持换行
     */
    private boolean isWrap;
    /**
     * 是否需要合并
     */
    private boolean needMerge;
    /**
     * 单元格纵向合并
     */
    private boolean mergeVertical;
    /**
     * 合并依赖
     */
    private int[] mergeRely;
    /**
     * 后缀
     */
    private String suffix;
    /**
     * 统计
     */
    private boolean isStatistics;
    /**
     * 数字格式
     */
    private String numFormat;
    /**
     * 是否隐藏列
     */
    private boolean isColumnHidden;
    /**
     * 枚举导出属性字段
     */
    private String enumExportField;
    /**
     * 数据脱敏类型
     */
    private DesensitizedUtil.DesensitizedType desensitizationType;
    /**
     * Excel 导出实体列表
     */
    private List<ExcelExportEntity> list;

    @Override
    public int compareTo(ExcelExportEntity prev) {
        return this.getOrderNum() - prev.getOrderNum();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ExcelExportEntity other = (ExcelExportEntity) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }
}
