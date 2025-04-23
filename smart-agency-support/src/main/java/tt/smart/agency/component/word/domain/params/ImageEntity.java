package tt.smart.agency.component.word.domain.params;

import lombok.Data;

/**
 * <p>
 * 图片设置和图片信息
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
public class ImageEntity {
    /**
     * 嵌入的
     */
    public static int EMBED = 0;
    /**
     * 之前的
     */
    public static int ABOVE = 1;
    /**
     * 之后的
     */
    public static int BEHIND = 2;
    /**
     * 地址
     */
    public static String URL = "url";
    /**
     * 数据
     */
    public static String Data = "data";
    /**
     * 图片输入方式
     */
    private String type = URL;
    /**
     * 图片宽度
     */
    private int width;
    /**
     * 图片高度
     */
    private int height;
    /**
     * 图片地址
     */
    private String url;
    /**
     * 图片信息
     */
    private byte[] data;
    /**
     * 单元格应跨越的行数
     */
    private int rowspan = 1;
    /**
     * 单元格应跨越的列数
     */
    private int colspan = 1;
    /**
     * 图片方式
     * EMBED 围绕
     * ABOVE 浮于上方
     * BEHIND 浮于下方
     */
    private int locationType = ImageEntity.EMBED;

}
