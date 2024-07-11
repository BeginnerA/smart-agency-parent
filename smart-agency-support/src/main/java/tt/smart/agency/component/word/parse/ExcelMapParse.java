package tt.smart.agency.component.word.parse;

import cn.hutool.core.util.StrUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import tt.smart.agency.component.word.domain.CustomXWPFDocument;
import tt.smart.agency.component.word.domain.params.ExcelForEachParams;
import tt.smart.agency.component.word.domain.params.ImageEntity;
import tt.smart.agency.component.word.tools.PoiElTools;
import tt.smart.agency.component.word.tools.PoiPublicTools;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static tt.smart.agency.component.word.tools.PoiElTools.*;

/**
 * <p>
 * 处理和生成 Map 类型的数据变成表格
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Log4j2
public final class ExcelMapParse {

    /**
     * 添加图片
     *
     * @param obj        图片设置和图片信息
     * @param currentRun 当前运行
     */
    public static void addAnImage(ImageEntity obj, XWPFRun currentRun) {
        try {
            Object[] isAndType = PoiPublicTools.getIsAndType(obj);
            String picId;
            picId = currentRun.getDocument().addPictureData((byte[]) isAndType[0],
                    (Integer) isAndType[1]);
            if (obj.getLocationType() == ImageEntity.EMBED) {
                ((CustomXWPFDocument) currentRun.getDocument()).createPicture(currentRun,
                        picId, currentRun.getDocument()
                                .getNextPicNameNumber((Integer) isAndType[1]),
                        obj.getWidth(), obj.getHeight());
            } else if (obj.getLocationType() == ImageEntity.ABOVE) {
                ((CustomXWPFDocument) currentRun.getDocument()).createPicture(currentRun,
                        picId, currentRun.getDocument()
                                .getNextPicNameNumber((Integer) isAndType[1]),
                        obj.getWidth(), obj.getHeight(), false);
            } else if (obj.getLocationType() == ImageEntity.BEHIND) {
                ((CustomXWPFDocument) currentRun.getDocument()).createPicture(currentRun,
                        picId, currentRun.getDocument()
                                .getNextPicNameNumber((Integer) isAndType[1]),
                        obj.getWidth(), obj.getHeight(), true);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    /**
     * 添加图像
     *
     * @param obj  图片设置和图片信息
     * @param cell 表单元格
     */
    private static void addAnImage(ImageEntity obj, XWPFTableCell cell) {
        List<XWPFParagraph> paragraphs = cell.getParagraphs();
        XWPFParagraph newPara = paragraphs.getFirst();
        XWPFRun imageCellRun = newPara.createRun();
        addAnImage(obj, imageCellRun);
    }

    /**
     * 解析模板参数行，获取模板参数列表
     *
     * @param currentRow 当前行
     * @param dataList   数据列表
     * @return 模板参数列表
     */
    private static List<ExcelForEachParams> parseCurrentRowGetParamsEntity(XWPFTableRow currentRow, List<Object> dataList) {
        List<XWPFTableCell> cells = currentRow.getTableCells();
        List<ExcelForEachParams> params = new ArrayList<>();
        String text;
        int firstCreate = 0;
        for (XWPFTableCell cell : cells) {
            ExcelForEachParams param = new ExcelForEachParams();
            text = cell.getText();
            if (StrUtil.isNotBlank(text) && text.contains(FOREACH)) {
                boolean isCreate = !text.contains(FOREACH_NOT_CREATE);
                param.setCreate(isCreate);
                text = text.replace(FOREACH_NOT_CREATE, EMPTY).replace(FOREACH_AND_SHIFT, EMPTY)
                        .replace(FOREACH, EMPTY).replace(START_STR, EMPTY).replace(END_STR, EMPTY);
                String[] keys = text.replaceAll("\\s+", BLANK_SPACE).trim().split(BLANK_SPACE);
                boolean isNestIterator = firstCreate > 0;
                param.setName(keys[isNestIterator ? 0 : 1]);
                param.setNestIterator(isNestIterator);
                if (isCreate) {
                    firstCreate++;
                }
            } else if (StrUtil.isNotBlank(text) && text.contains(START_WRAP) && text.contains(END_WRAP)) {
                param.setCreate(true);
                text = text.replace(START_WRAP, EMPTY).replace(END_WRAP, EMPTY).trim();
                param.setName(text);
            } else {
                param.setName(StrUtil.isBlank(text) ? EMPTY : text.trim().replace(START_STR, EMPTY).replace(END_STR, EMPTY));
                param.setRowspan(StrUtil.isBlank(text) ? dataList.size() : 0);
            }
            if (cell.getCTTc().getTcPr().getGridSpan() != null) {
                if (cell.getCTTc().getTcPr().getGridSpan().getVal() != null) {
                    param.setColspan(cell.getCTTc().getTcPr().getGridSpan().getVal().intValue());
                }
            }
            param.setHeight((short) currentRow.getHeight());
            params.add(param);
        }
        return params;
    }

    /**
     * 解析下一行，并且生成更多的行
     *
     * @param table    表
     * @param index    索引
     * @param dataList 数据列表
     */
    public static void parseNextRowAndAddRow(XWPFTable table, int index, List<Object> dataList) {
        XWPFTableRow currentRow = table.getRow(index);
        XWPFTableCell firstCell = currentRow.getTableCells().getFirst();
        String firstText = firstCell.getText();
        if (firstText.contains(FOREACH_AND_SHIFT) && firstText.startsWith(START_STR) && firstText.contains(END_STR)) {
            firstCell.setText(firstText.split(END_STR)[1]);
            currentRow = table.getRow(++index);
        }
        List<ExcelForEachParams> paramsList = parseCurrentRowGetParamsEntity(currentRow, dataList);
        Optional<ExcelForEachParams> forEachParams = paramsList.stream().filter(e -> StrUtil.isNotEmpty(e.getName())).findFirst();
        boolean isCreate = true;
        if (forEachParams.isPresent()) {
            isCreate = forEachParams.get().isCreate();
        }

        // 保存这一行的样式是为了后面好统一设置
        List<XWPFTableCell> tempCellList = new ArrayList<>(table.getRow(index).getTableCells());
        int cellIndex;
        log.debug("从每个数据列表开始 :{}", dataList.size());
        for (Object obj : dataList) {
            currentRow = isCreate ? table.insertNewTableRow(index++) : table.getRow(index++);
            for (cellIndex = 0; cellIndex < currentRow.getTableCells().size(); cellIndex++) {
                Object val = PoiElTools.eval(paramsList.get(cellIndex).getName(), obj);
                clearParagraphText(currentRow.getTableCells().get(cellIndex).getParagraphs());
                if (val instanceof ImageEntity) {
                    addAnImage((ImageEntity) val, tempCellList.get(cellIndex));
                } else {
                    PoiPublicTools.copyCellAndSetValue(tempCellList.get(cellIndex),
                            currentRow.getTableCells().get(cellIndex), val.toString());
                }
            }

            for (; cellIndex < paramsList.size(); cellIndex++) {
                ExcelForEachParams eachParams = paramsList.get(cellIndex);
                Object val = PoiElTools.eval(eachParams.getName(), obj);
                XWPFTableCell cell = currentRow.createCell();
                if (eachParams.getColspan() > 1) {
                    cell.getCTTc().addNewTcPr().addNewGridSpan().setVal(new BigInteger(eachParams.getColspan() + EMPTY));
                }
                if (eachParams.getRowspan() > 1) {
                    cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
                }
                if (val instanceof ImageEntity) {
                    addAnImage((ImageEntity) val, cell);
                } else {
                    PoiPublicTools.copyCellAndSetValue(tempCellList.get(cellIndex), cell, val.toString());
                }
            }
        }
        table.removeRow(index);

    }

    /**
     * 清除段落文本
     *
     * @param paragraphs 段落
     */
    private static void clearParagraphText(List<XWPFParagraph> paragraphs) {
        paragraphs.forEach(pp -> {
            if (pp.getRuns() != null) {
                for (int i = pp.getRuns().size() - 1; i >= 0; i--) {
                    pp.removeRun(i);
                }
            }
        });
    }

}
