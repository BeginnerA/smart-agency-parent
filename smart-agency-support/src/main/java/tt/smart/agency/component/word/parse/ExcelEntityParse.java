package tt.smart.agency.component.word.parse;

import cn.hutool.core.util.StrUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.xwpf.usermodel.*;
import tt.smart.agency.component.word.base.ExportCommonService;
import tt.smart.agency.component.word.domain.params.ExcelExportEntity;
import tt.smart.agency.component.word.domain.params.ExcelListEntity;
import tt.smart.agency.component.word.domain.params.ImageEntity;
import tt.smart.agency.component.word.tools.PoiPublicTools;

import java.lang.reflect.Field;
import java.util.*;

/**
 * <p>
 * 解析实体类对象，复用注解
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Log4j2
public class ExcelEntityParse extends ExportCommonService {

    /**
     * 检查 excel 参数
     *
     * @param entity 导出对象
     */
    private static void checkExcelParams(ExcelListEntity entity) {
        if (entity.getList() == null || entity.getClazz() == null) {
            throw new RuntimeException("Excel 导出参数错误");
        }

    }

    /**
     * 创建列表单元格
     *
     * @param index       索引
     * @param t           当前对象
     * @param excelParams 导出对象列表
     * @param table       表
     * @param rowHeight   行高
     * @return 最大高度
     */
    private int createCells(int index, Object t, List<ExcelExportEntity> excelParams, XWPFTable table, short rowHeight) {
        try {
            ExcelExportEntity entity;
            XWPFTableRow row = table.insertNewTableRow(index);
            if (rowHeight != -1) {
                row.setHeight(rowHeight);
            }
            int maxHeight = 1, cellNum = 0;
            for (ExcelExportEntity excelParam : excelParams) {
                entity = excelParam;
                if (entity.getList() != null) {
                    Collection<?> list = (Collection<?>) entity.getMethod().invoke(t, new Object[]{});
                    int listC = 0;
                    for (Object obj : list) {
                        createListCells(index + listC, cellNum, obj, entity.getList(), table, rowHeight);
                        listC++;
                    }
                    cellNum += entity.getList().size();
                    if (list.size() > maxHeight) {
                        maxHeight = list.size();
                    }
                } else {
                    Object value = getCellValue(entity, t);
                    if (entity.getType() == 1) {
                        setCellValue(row, value, cellNum++);
                    }
                }
            }
            // 合并需要合并的单元格
            cellNum = 0;
            for (ExcelExportEntity excelParam : excelParams) {
                entity = excelParam;
                if (entity.getList() != null) {
                    cellNum += entity.getList().size();
                } else if (entity.isNeedMerge() && maxHeight > 1) {
                    table.setCellMargins(index, index + maxHeight - 1, cellNum, cellNum);
                    cellNum++;
                }
            }
            return maxHeight;
        } catch (Exception e) {
            log.error("Excel 单元格导出错误，数据是：{}", t);
            throw new RuntimeException("Excel 导出错误", e);
        }
    }

    /**
     * 创建 List 之后的各个 Cells
     *
     * @param index       索引
     * @param cellNum     导出对象列表数量
     * @param obj         当前对象
     * @param excelParams 列参数信息
     * @param table       当前表格
     * @param rowHeight   行高
     */
    public void createListCells(int index, int cellNum, Object obj, List<ExcelExportEntity> excelParams,
                                XWPFTable table, short rowHeight) {
        ExcelExportEntity entity;
        XWPFTableRow row;
        if (table.getRow(index) == null) {
            row = table.createRow();
            row.setHeight(rowHeight);
        } else {
            row = table.getRow(index);
        }
        for (ExcelExportEntity excelParam : excelParams) {
            entity = excelParam;
            Object value = getCellValue(entity, obj);
            if (entity.getType() == 1) {
                setCellValue(row, value, cellNum++);
            }
        }
    }

    /**
     * 获取表头数据
     *
     * @param table 表
     * @param index 索引
     * @return 表头数据
     */
    private Map<String, Integer> getTitleMap(XWPFTable table, int index, int headRows) {
        if (index < headRows) {
            throw new RuntimeException("Excel 没有表头");
        }
        Map<String, Integer> map = new HashMap<>(headRows);
        String text;
        for (int j = 0; j < headRows; j++) {
            List<XWPFTableCell> cells = table.getRow(index - j - 1).getTableCells();
            for (int i = 0; i < cells.size(); i++) {
                text = cells.get(i).getText();
                if (StrUtil.isEmpty(text)) {
                    throw new RuntimeException("Excel 表头有的字段为空");
                }
                map.put(text, i);
            }
        }
        return map;
    }

    /**
     * 解析上一行并生成更多行
     *
     * @param table  表
     * @param index  索引
     * @param entity 导出对象
     */
    public void parseNextRowAndAddRow(XWPFTable table, int index, ExcelListEntity entity) {
        // 删除这一行
        table.removeRow(index);
        checkExcelParams(entity);
        // 获取表头数据
        Map<String, Integer> titlemap = getTitleMap(table, index, entity.getHeadRows());
        try {
            // 得到所有字段
            Field[] fields = PoiPublicTools.getClassFields(entity.getClazz());
            // 获取实体对象的导出数据
            List<ExcelExportEntity> excelParams = new ArrayList<>();
            getAllExcelField(fields, excelParams, entity.getClazz(), null);
            // 根据表头进行筛选排序
            sortAndFilterExportField(excelParams, titlemap);
            short rowHeight = getRowHeight(excelParams);
            for (Object t : entity.getList()) {
                index += createCells(index, t, excelParams, table, rowHeight);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 设置单元格值
     *
     * @param row     表行
     * @param value   值
     * @param cellNum 合并单元格数
     */
    private void setCellValue(XWPFTableRow row, Object value, int cellNum) {
        if (row.getCell(cellNum++) != null) {
            row.getCell(cellNum - 1).setText(value == null ? "" : value.toString());
        }
        setWordText(row, value);
    }


    /**
     * 解决 word 导出表格多出的换行符问题
     *
     * @param row   表行
     * @param value 值
     */
    private void setWordText(XWPFTableRow row, Object value) {
        XWPFTableCell cell = row.createCell();
        List<XWPFParagraph> paragraphs = cell.getParagraphs();
        XWPFParagraph paragraph;
        XWPFRun run;
        if (paragraphs != null && !paragraphs.isEmpty()) {
            paragraph = paragraphs.getFirst();
        } else {
            paragraph = row.createCell().addParagraph();
        }
        List<XWPFRun> runs = paragraph.getRuns();
        if (runs != null && !runs.isEmpty()) {
            run = runs.getFirst();
        } else {
            run = paragraph.createRun();
        }
        if (value instanceof ImageEntity) {
            ExcelMapParse.addAnImage((ImageEntity) value, run);
        } else {
            PoiPublicTools.setWordText(run, value == null ? "" : value.toString());
        }
    }

    /**
     * 对导出序列进行排序和塞选
     *
     * @param excelParams 导出参数
     * @param titleMap    表头数据
     */
    private void sortAndFilterExportField(List<ExcelExportEntity> excelParams, Map<String, Integer> titleMap) {
        for (int i = excelParams.size() - 1; i >= 0; i--) {
            if (excelParams.get(i).getList() != null && !excelParams.get(i).getList().isEmpty()) {
                sortAndFilterExportField(excelParams.get(i).getList(), titleMap);
                if (excelParams.get(i).getList().isEmpty()) {
                    excelParams.remove(i);
                } else {
                    excelParams.get(i).setOrderNum(i);
                }
            } else {
                if (titleMap.containsKey(excelParams.get(i).getName())) {
                    excelParams.get(i).setOrderNum(i);
                } else {
                    excelParams.remove(i);
                }
            }
        }
        sortAllParams(excelParams);
    }

}
