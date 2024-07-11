package tt.smart.agency.tools;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import tt.smart.agency.component.word.ParseWord07;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Word使用模板导出工具类
 *
 * @author JueYue
 * 2013-11-16
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WordExportTools {

    /**
     * 解析 Word2007 版本
     *
     * @param url 模板地址
     * @param map 解析数据源
     * @return XWPFDocument
     */
    public static XWPFDocument exportWord07(String url, Map<String, Object> map) {
        return new ParseWord07().parseWord(url, map);
    }

    /**
     * 解析 Word2007 版本
     *
     * @param document 模板
     * @param map      解析数据源
     */
    public static void exportWord07(XWPFDocument document, Map<String, Object> map) {
        new ParseWord07().parseWord(document, map);
    }

    /**
     * 一个模板生成多页
     *
     * @param url  模板地址
     * @param list 解析数据源
     * @return XWPFDocument
     */
    public static XWPFDocument exportWord07(String url, List<Map<String, Object>> list) {
        return new ParseWord07().parseWord(url, list);
    }

    /**
     * word 模板导出 模板格式为 {.属性}
     *
     * @param filename     文件名
     * @param templatePath 模板路径 resource 目录下的路径包括模板文件名
     *                     例如: word/temp.docx
     *                     重点: 模板文件必须放置到启动类对应的 resource 目录下
     * @param data         模板需要的数据
     * @param response     响应体
     */
    public static void exportWord(Map<String, Object> data, String filename, String templatePath, HttpServletResponse response) {
        try {
            XWPFDocument word = exportWord07(getCompleteUrl(templatePath), data);
            resetResponse(filename, response);
            word.write(response.getOutputStream());
        } catch (Exception e) {
            throw new RuntimeException("Word 模板导出失败");
        }
    }

    /**
     * word 模板导出 模板格式为 {.属性}
     *
     * @param filename     文件名
     * @param templatePath 模板路径 resource 目录下的路径包括模板文件名
     *                     例如: word/temp.docx
     *                     重点: 模板文件必须放置到启动类对应的 resource 目录下
     * @param data         模板需要的数据
     * @param response     响应体
     */
    public static void exportWord(List<Map<String, Object>> data, String filename, String templatePath, HttpServletResponse response) {
        try {
            XWPFDocument word = exportWord07(getCompleteUrl(templatePath), data);
            resetResponse(filename, response);
            word.write(response.getOutputStream());
        } catch (Exception e) {
            throw new RuntimeException("Word 模板导出失败");
        }
    }

    /**
     * 得到完整的 url
     *
     * @return url
     */
    private static String getCompleteUrl(String templatePath) {
        ClassPathResource templateResource = new ClassPathResource(templatePath);
        return templateResource.getUrl().getPath();
    }

    /**
     * 重置响应体
     *
     * @param filename 文件名称
     * @param response 响应体
     */
    private static void resetResponse(String filename, HttpServletResponse response) {
        String currentTime = DateUtil.format(new Date(), "yyyyMMddHHmmss");
        if (!FileNameUtil.isType(filename, "docx")) {
            filename = filename + "_" + currentTime + ".docx";
        } else {
            String prefix = FileNameUtil.getPrefix(filename);
            String suffix = FileNameUtil.getSuffix(filename);
            filename = prefix + "_" + currentTime + "." + suffix;
        }

        // header
        String percentEncodedFileName = URLEncoder.encode(filename, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        String contentDispositionValue = "attachment; filename=%s;filename*=utf-8''%s".formatted(percentEncodedFileName, percentEncodedFileName);
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition,download-filename");
        response.setHeader("Content-disposition", contentDispositionValue);
        response.setHeader("download-filename", percentEncodedFileName);
        // 内容类型
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
    }

}
