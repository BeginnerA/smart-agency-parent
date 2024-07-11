package tt.smart.agency.component.word;

import cn.hutool.core.util.StrUtil;
import org.apache.poi.xwpf.usermodel.*;
import tt.smart.agency.component.word.cache.WordCache;
import tt.smart.agency.component.word.domain.CustomXWPFDocument;
import tt.smart.agency.component.word.domain.params.ExcelListEntity;
import tt.smart.agency.component.word.domain.params.ImageEntity;
import tt.smart.agency.component.word.parse.ExcelEntityParse;
import tt.smart.agency.component.word.parse.ExcelMapParse;
import tt.smart.agency.component.word.tools.PoiPublicTools;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static tt.smart.agency.component.word.tools.PoiElTools.*;

/**
 * <p>
 * 解析07版的 Word，替换文字，生成表格，生成图片
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@SuppressWarnings({"unchecked", "rawtypes"})
public class ParseWord07 {

    /**
     * 根据条件改变值
     *
     * @param paragraph   段落
     * @param currentRun  当前运行
     * @param currentText 目前解析的模板
     * @param runIndex    运行指标
     * @param map         数据
     */
    private void changeValues(XWPFParagraph paragraph, XWPFRun currentRun, String currentText,
                              List<Integer> runIndex, Map<String, Object> map) {
        // 判断是不是迭代输出
        if (currentText.contains(FOREACH) && currentText.startsWith(START_STR)) {
            currentText = currentText.replace(FOREACH, EMPTY).replace(START_STR, EMPTY).replace(END_STR, EMPTY);
            String[] keys = currentText.replaceAll("\\s+", BLANK_SPACE).trim().split(BLANK_SPACE);
            List list = (List) PoiPublicTools.getParamsValue(keys[0], map);
            if (list == null) {
                // 防止无参数内容报错
                list = new ArrayList();
            }
            list.forEach(obj -> {
                if (obj instanceof ImageEntity) {
                    currentRun.setText("", 0);
                    ExcelMapParse.addAnImage((ImageEntity) obj, currentRun);
                } else {
                    PoiPublicTools.setWordText(currentRun, obj.toString());
                }
            });
        } else {
            Object obj = PoiPublicTools.getRealValue(currentText, map);
            // 如果是图片就设置为图片
            if (obj instanceof ImageEntity) {
                currentRun.setText("", 0);
                ExcelMapParse.addAnImage((ImageEntity) obj, currentRun);
            } else {
                currentText = obj.toString();
                PoiPublicTools.setWordText(currentRun, currentText);
            }
        }

        for (Integer index : runIndex) {
            paragraph.getRuns().get(index).setText("", 0);
        }
        runIndex.clear();
    }

    /**
     * 判断是不是迭代输出
     *
     * @param cells 表单元格列表
     * @param map   数据
     * @return 迭代内容
     */
    private Object checkThisTableIsNeedIterator(List<XWPFTableCell> cells, Map<String, Object> map) {
        Optional<XWPFTableCell> cell = cells.stream().filter(e -> StrUtil.isNotBlank(e.getText().trim())).findFirst();
        if (cell.isEmpty()) {
            return null;
        }
        String text = cell.get().getText().trim();
        // 判断是不是迭代输出
        if (text.contains(FOREACH) && text.startsWith(START_STR)) {
            // 匹配多个"\\{\\{([^}]+)}}";
            Pattern pattern = Pattern.compile("\\{\\{(.*?)}}");
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                text = matcher.group(1);
            }
            text = text.replace(FOREACH_NOT_CREATE, EMPTY).replace(FOREACH_AND_SHIFT, EMPTY)
                    .replace(FOREACH, EMPTY).replaceAll("\\s+", BLANK_SPACE).trim();
            // 防止内容中间出现多个空格
            String[] keys = text.split(BLANK_SPACE);
            Object result = PoiPublicTools.getParamsValue(keys[0], map);
            // 添加 list 默认值，避免将{{$fe: list t.sn t.hoby	t.remark}} 这类标签直接显示出来
            return Objects.nonNull(result) ? result : new ArrayList<Map<String, Object>>(0);
        }
        return null;
    }

    /**
     * 解析所有的文本
     *
     * @param paragraphs 段落
     * @param map        数据
     */
    private void parseAllParagraph(List<XWPFParagraph> paragraphs, Map<String, Object> map) {
        XWPFParagraph paragraph;
        for (XWPFParagraph xwpfParagraph : paragraphs) {
            paragraph = xwpfParagraph;
            if (paragraph.getText().contains(START_STR)) {
                parseThisParagraph(paragraph, map);
            }
        }
    }

    /**
     * 解析这个段落
     *
     * @param paragraph 段落
     * @param map       数据
     */
    private void parseThisParagraph(XWPFParagraph paragraph, Map<String, Object> map) {
        XWPFRun run;
        // 拿到的第一个 run，用来 set 值，可以保存格式
        XWPFRun currentRun = null;
        // 存放当前的 text
        String currentText = "";
        String text;
        // 判断是不是已经遇到{{
        boolean isfinde = false;
        // 存储遇到的 run，把他们置空
        List<Integer> runIndex = new ArrayList<>();
        for (int i = 0; i < paragraph.getRuns().size(); i++) {
            run = paragraph.getRuns().get(i);
            text = run.getText(0);
            if (StrUtil.isEmpty(text)) {
                continue;
            }
            if (isfinde) {
                currentText += text;
                if (!currentText.contains(START_STR)) {
                    // 不是开始标签
                    isfinde = false;
                    runIndex.clear();
                } else {
                    runIndex.add(i);
                }
                if (currentText.contains(END_STR)) {
                    // 是结束标签
                    changeValues(paragraph, currentRun, currentText, runIndex, map);
                    currentText = "";
                    isfinde = false;
                }
            } else if (text.contains(START_STR)) {
                // 是开始标签
                currentText = text;
                isfinde = true;
                currentRun = run;
            } else {
                currentText = "";
            }
            if (currentText.contains(END_STR)) {
                changeValues(paragraph, currentRun, currentText, runIndex, map);
                isfinde = false;
            }
        }
    }

    /**
     * 解析这一行
     *
     * @param cells 单元格
     * @param map   数据
     */
    private void parseThisRow(List<XWPFTableCell> cells, Map<String, Object> map) {
        for (XWPFTableCell cell : cells) {
            parseAllParagraph(cell.getParagraphs(), map);
        }
    }


    /**
     * 解析这个表格
     *
     * @param table 表格
     * @param map   数据
     */
    private void parseThisTable(XWPFTable table, Map<String, Object> map) {
        XWPFTableRow row;
        List<XWPFTableCell> cells;
        Object listobj;
        for (int i = 0; i < table.getNumberOfRows(); i++) {
            row = table.getRow(i);
            cells = row.getTableCells();
            listobj = checkThisTableIsNeedIterator(cells, map);
            if (listobj == null) {
                parseThisRow(cells, map);
            } else if (listobj instanceof ExcelListEntity) {
                new ExcelEntityParse().parseNextRowAndAddRow(table, i, (ExcelListEntity) listobj);
                // 删除之后要往上挪一行，然后加上跳过新建的行数
                i = i + ((ExcelListEntity) listobj).getList().size() - 1;
            } else {
                ExcelMapParse.parseNextRowAndAddRow(table, i, (List) listobj);
                // 删除之后要往上挪一行，然后加上跳过新建的行数
                i = i + ((List) listobj).size() - 1;
            }
        }
    }

    /**
     * 解析07版的 Word 并且进行赋值
     *
     * @param url 模板地址
     * @param map 数据
     * @return XWPFDocument
     */
    public XWPFDocument parseWord(String url, Map<String, Object> map) {
        CustomXWPFDocument doc = WordCache.getXWPFDocument(url);
        assert doc != null;
        parseWordSetValue(doc, map);
        return doc;
    }

    /**
     * 解析07版的 Work 并且进行赋值但是进行多页拼接
     *
     * @param url  模板地址
     * @param list 数据列表
     * @return XWPFDocument
     */
    public XWPFDocument parseWord(String url, List<Map<String, Object>> list) {
        if (list == null || list.isEmpty()) {
            return null;
        } else if (list.size() == 1) {
            return parseWord(url, list.getFirst());
        } else {
            CustomXWPFDocument doc = WordCache.getXWPFDocument(url);
            assert doc != null;
            parseWordSetValue(doc, list.getFirst());
            // 插入分页(推荐这种分页方式，每页上方不会产生留白)
            doc.createParagraph().createRun().addBreak(BreakType.PAGE);
            for (int i = 1; i < list.size(); i++) {
                CustomXWPFDocument tempDoc = WordCache.getXWPFDocument(url);
                assert tempDoc != null;
                parseWordSetValue(tempDoc, list.get(i));
                //最后一页不插入分页
                if (i < list.size() - 1) {
                    tempDoc.createParagraph().createRun().addBreak(BreakType.PAGE);
                }
                doc.getDocument().addNewBody().set(tempDoc.getDocument().getBody());

            }
            return doc;
        }

    }

    /**
     * 解析07版的 Word 并且进行赋值
     *
     * @param document XWPFDocument
     * @param map      数据
     */
    public void parseWord(XWPFDocument document, Map<String, Object> map) {
        parseWordSetValue((CustomXWPFDocument) document, map);
    }

    /**
     * 解析07版的 Word 并且进行赋值
     *
     * @param doc XWPFDocument
     * @param map 数据
     */
    private void parseWordSetValue(CustomXWPFDocument doc, Map<String, Object> map) {
        // 第一步解析文档
        parseAllParagraph(doc.getParagraphs(), map);
        // 第二步解析页眉,页脚
        parseHeaderAndFoot(doc, map);
        // 第三步解析所有表格
        XWPFTable table;
        Iterator<XWPFTable> itTable = doc.getTablesIterator();
        while (itTable.hasNext()) {
            table = itTable.next();
            if (table.getText().contains(START_STR)) {
                parseThisTable(table, map);
            }
        }

    }

    /**
     * 解析页眉和页脚
     *
     * @param doc CustomXWPFDocument
     * @param map 数据
     */
    private void parseHeaderAndFoot(CustomXWPFDocument doc, Map<String, Object> map) {
        List<XWPFHeader> headerList = doc.getHeaderList();
        for (XWPFHeader xwpfHeader : headerList) {
            for (int i = 0; i < xwpfHeader.getListParagraph().size(); i++) {
                parseThisParagraph(xwpfHeader.getListParagraph().get(i), map);
            }
        }
        List<XWPFFooter> footerList = doc.getFooterList();
        for (XWPFFooter xwpfFooter : footerList) {
            for (int i = 0; i < xwpfFooter.getListParagraph().size(); i++) {
                parseThisParagraph(xwpfFooter.getListParagraph().get(i), map);
            }
        }

    }

}
