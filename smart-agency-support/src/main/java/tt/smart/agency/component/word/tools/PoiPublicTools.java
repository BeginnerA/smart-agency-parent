package tt.smart.agency.component.word.tools;

import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import tt.smart.agency.component.word.cache.ImageCache;
import tt.smart.agency.component.word.domain.params.ExcelListEntity;
import tt.smart.agency.component.word.domain.params.ImageEntity;

import java.lang.reflect.Field;
import java.util.*;

import static tt.smart.agency.component.word.tools.PoiElTools.*;

/**
 * <p>
 * 公共基础类
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@SuppressWarnings("rawtypes")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PoiPublicTools {

    /**
     * 获取 class 字段（包括父类）
     *
     * @param clazz class
     * @return 字段列表
     */
    public static Field[] getClassFields(Class<?> clazz) {
        List<Field> list = new ArrayList<Field>();
        Field[] fields;
        do {
            fields = clazz.getDeclaredFields();
            Collections.addAll(list, fields);
            clazz = clazz.getSuperclass();
        } while (clazz != Object.class && clazz != null);
        return list.toArray(fields);
    }

    /**
     * 获取图片扩展名
     *
     * @param photoByte 图片字节
     * @return 扩展名
     */
    public static String getFileExtendName(byte[] photoByte) {
        String strFileExtendName = "JPG";
        if ((photoByte[0] == 71) && (photoByte[1] == 73) && (photoByte[2] == 70)
                && (photoByte[3] == 56) && ((photoByte[4] == 55) || (photoByte[4] == 57))
                && (photoByte[5] == 97)) {
            strFileExtendName = "GIF";
        } else if ((photoByte[6] == 74) && (photoByte[7] == 70) && (photoByte[8] == 73)
                && (photoByte[9] == 70)) {
            strFileExtendName = "JPG";
        } else if ((photoByte[0] == 66) && (photoByte[1] == 77)) {
            strFileExtendName = "BMP";
        } else if ((photoByte[1] == 80) && (photoByte[2] == 78) && (photoByte[3] == 71)) {
            strFileExtendName = "PNG";
        }
        return strFileExtendName;
    }

    /**
     * 返回流和图片类型
     *
     * @param entity 图片对象
     * @return (byte[]) isAndType[0],(Integer)isAndType[1]
     */
    public static Object[] getIsAndType(ImageEntity entity) {
        Object[] result = new Object[2];
        String type;
        if (entity.getType().equals(ImageEntity.URL)) {
            result[0] = ImageCache.getImage(entity.getUrl());
        } else {
            result[0] = entity.getData();
        }
        assert result[0] != null;
        type = PoiPublicTools.getFileExtendName((byte[]) result[0]);
        result[1] = getImageType(type);
        return result;
    }

    /**
     * 获取 XWPFDocument 图片类型
     *
     * @param type 类型
     * @return XWPFDocument 图片类型
     */
    private static Integer getImageType(String type) {
        if ("JPG".equalsIgnoreCase(type) || "JPEG".equalsIgnoreCase(type)) {
            return XWPFDocument.PICTURE_TYPE_JPEG;
        }
        if ("GIF".equalsIgnoreCase(type)) {
            return XWPFDocument.PICTURE_TYPE_GIF;
        }
        if ("BMP".equalsIgnoreCase(type)) {
            return XWPFDocument.PICTURE_TYPE_GIF;
        }
        if ("PNG".equalsIgnoreCase(type)) {
            return XWPFDocument.PICTURE_TYPE_PNG;
        }
        return XWPFDocument.PICTURE_TYPE_JPEG;
    }

    /**
     * 获取参数值
     *
     * @param params 模板
     * @param object 参数
     * @return 值
     */
    public static Object getParamsValue(String params, Object object) {
        if (params.contains(".")) {
            String[] paramsArr = params.split("\\.");
            return getValueDoWhile(object, paramsArr, 0);
        }
        if (object instanceof Map) {
            return ((Map) object).get(params);
        }
        return PoiReflectorTools.fromCache(object.getClass()).getValue(object, params);
    }

    /**
     * 解析数据
     *
     * @param currentText 目前解析的模板
     * @param map         参数
     * @return 结果
     */
    public static Object getRealValue(String currentText, Map<String, Object> map) {
        String params = EMPTY;
        while (currentText.contains(START_STR)) {
            params = currentText.substring(currentText.indexOf(START_STR) + 2, currentText.indexOf(END_STR));
            Object obj = PoiElTools.eval(params.trim(), map);
            // 判断图片或者是集合
            if (obj instanceof ImageEntity || obj instanceof List || obj instanceof ExcelListEntity) {
                return obj;
            } else if (obj != null) {
                currentText = currentText.replace(START_STR + params + END_STR, obj.toString());
            } else {
                currentText = currentText.replace(START_STR + params + END_STR, EMPTY);
            }
        }
        return currentText;
    }

    /**
     * 通过遍历过去对象值
     *
     * @param object    参数
     * @param paramsArr 模板列表
     * @param index     索引
     * @return 值
     */
    public static Object getValueDoWhile(Object object, String[] paramsArr, int index) {
        String params = paramsArr[index];
        boolean isGetArrayVal = false;
        int arrayIdx = -1;
        if (params.contains("[") && params.contains("]")) {
            isGetArrayVal = true;
            // 获取索引长度
            int startIdx = params.indexOf("[");
            int endIdx = params.indexOf("]");
            String idxStr = params.substring(startIdx + 1, endIdx);
            arrayIdx = Integer.parseInt(idxStr.trim());
            params = params.substring(0, startIdx);
        }
        switch (object) {
            case null -> {
                return EMPTY;
            }
            case ImageEntity ignored -> {
                return object;
            }
            case Map map -> object = map.get(params);
            default -> object = PoiReflectorTools.fromCache(object.getClass()).getValue(object,
                    params);
        }
        if (isGetArrayVal && null != object) {
            // 如果是获取列表中的某一个值，则取值
            if (object instanceof List list) {
                if (arrayIdx < 0 || arrayIdx >= list.size()) {
                    object = null;
                } else {
                    object = ((List) object).get(arrayIdx);
                }
            }
        }

        if (object instanceof Collection) {
            return object;
        }
        return (index == paramsArr.length - 1) ? (object == null ? EMPTY : object)
                : getValueDoWhile(object, paramsArr, ++index);
    }

    /**
     * 支持换行操作
     *
     * @param currentRun  当前运行
     * @param currentText 当前值
     */
    public static void setWordText(XWPFRun currentRun, String currentText) {
        if (StrUtil.isNotEmpty(currentText)) {
            String[] tempArr = currentText.split("\r\n");
            for (int i = 0, le = tempArr.length - 1; i < le; i++) {
                currentRun.setText(tempArr[i], i);
                currentRun.addBreak();
            }
            currentRun.setText(tempArr[tempArr.length - 1], tempArr.length - 1);
        } else {
            //对blank字符串做处理，避免显示"{{"
            currentRun.setText(EMPTY, 0);
        }
    }

    /**
     * 复制单元格和设置值
     *
     * @param tmpCell 模板表单元格
     * @param cell    当前表单元格
     * @param text    值
     */
    public static void copyCellAndSetValue(XWPFTableCell tmpCell, XWPFTableCell cell, String text) {
        CTTc cttc2 = tmpCell.getCTTc();
        CTTcPr ctPr2 = cttc2.getTcPr();
        cell.getTableRow().setHeight(tmpCell.getTableRow().getHeight());
        CTTc cttc = cell.getCTTc();
        CTTcPr ctPr = cttc.addNewTcPr();
        if (tmpCell.getColor() != null) {
            cell.setColor(tmpCell.getColor());
        }
        if (tmpCell.getVerticalAlignment() != null) {
            cell.setVerticalAlignment(tmpCell.getVerticalAlignment());
        }
        if (ctPr2.getTcW() != null) {
            ctPr.addNewTcW().setW(ctPr2.getTcW().getW());
        }
        if (ctPr2.getVAlign() != null) {
            ctPr.addNewVAlign().setVal(ctPr2.getVAlign().getVal());
        }
        if (!cttc2.getPList().isEmpty()) {
            CTP ctp = cttc2.getPList().getFirst();
            if (ctp.getPPr() != null) {
                if (ctp.getPPr().getJc() != null) {
                    cttc.getPList().getFirst().addNewPPr().addNewJc().setVal(ctp.getPPr().getJc().getVal());
                }
            }
        }

        if (ctPr2.getTcBorders() != null) {
            ctPr.setTcBorders(ctPr2.getTcBorders());
        }

        XWPFParagraph tmpP = tmpCell.getParagraphs().getFirst();
        XWPFParagraph cellP = cell.getParagraphs().getFirst();
        XWPFRun tmpR = null;
        if (tmpP.getRuns() != null && !tmpP.getRuns().isEmpty()) {
            tmpR = tmpP.getRuns().getFirst();
        }
        XWPFRun cellR = cellP.createRun();
        cellR.setText(text);
        //复制字体信息
        if (tmpR != null) {
            cellR.setBold(tmpR.isBold());
            cellR.setItalic(tmpR.isItalic());
            cellR.setStrikeThrough(tmpR.isStrikeThrough());
            cellR.setUnderline(tmpR.getUnderline());
            cellR.setColor(tmpR.getColor());
            cellR.setTextPosition(tmpR.getTextPosition());
            if (tmpR.getFontSizeAsDouble() != null) {
                cellR.setFontSize(tmpR.getFontSizeAsDouble());
            }
            if (tmpR.getFontFamily() != null) {
                cellR.setFontFamily(tmpR.getFontFamily());
            }
            if (tmpR.getCTR() != null) {
                if (tmpR.getCTR().isSetRPr()) {
                    CTRPr tmpRPr = tmpR.getCTR().getRPr();
                    if (tmpRPr.sizeOfRStyleArray() != 0) {
                        CTFonts tmpFonts = tmpRPr.getRFontsArray(0);
                        CTRPr cellRPr = cellR.getCTR().isSetRPr() ? cellR.getCTR().getRPr() : cellR.getCTR().addNewRPr();
                        CTFonts cellFonts = cellRPr.sizeOfRStyleArray() != 0 ? cellRPr.getRFontsArray(0) : cellRPr.addNewRFonts();
                        cellFonts.setAscii(tmpFonts.getAscii());
                        cellFonts.setAsciiTheme(tmpFonts.getAsciiTheme());
                        cellFonts.setCs(tmpFonts.getCs());
                        cellFonts.setCstheme(tmpFonts.getCstheme());
                        cellFonts.setEastAsia(tmpFonts.getEastAsia());
                        cellFonts.setEastAsiaTheme(tmpFonts.getEastAsiaTheme());
                        cellFonts.setHAnsi(tmpFonts.getHAnsi());
                        cellFonts.setHAnsiTheme(tmpFonts.getHAnsiTheme());
                    }
                }
            }
        }
        // 复制段落信息
        if (tmpP.getAlignment() != null) {
            cellP.setAlignment(tmpP.getAlignment());
        }
        if (tmpP.getVerticalAlignment() != null) {
            cellP.setVerticalAlignment(tmpP.getVerticalAlignment());
        }
        if (tmpP.getBorderBetween() != null) {
            cellP.setBorderBetween(tmpP.getBorderBetween());
        }
        if (tmpP.getBorderBottom() != null) {
            cellP.setBorderBottom(tmpP.getBorderBottom());
        }
        if (tmpP.getBorderLeft() != null) {
            cellP.setBorderLeft(tmpP.getBorderLeft());
        }
        if (tmpP.getBorderRight() != null) {
            cellP.setBorderRight(tmpP.getBorderRight());
        }
        if (tmpP.getBorderTop() != null) {
            cellP.setBorderTop(tmpP.getBorderTop());
        }
        cellP.setPageBreak(tmpP.isPageBreak());
        if (tmpP.getCTP() != null) {
            if (tmpP.getCTP().getPPr() != null) {
                CTPPr tmpPPr = tmpP.getCTP().getPPr();
                CTPPr cellPPr = cellP.getCTP().getPPr() != null ? cellP.getCTP().getPPr() : cellP.getCTP().addNewPPr();
                // 复制段落间距信息
                CTSpacing tmpSpacing = tmpPPr.getSpacing();
                if (tmpSpacing != null) {
                    CTSpacing cellSpacing = cellPPr.getSpacing() != null ? cellPPr.getSpacing() : cellPPr.addNewSpacing();
                    if (tmpSpacing.getAfter() != null) {
                        cellSpacing.setAfter(tmpSpacing.getAfter());
                    }
                    if (tmpSpacing.getAfterAutospacing() != null) {
                        cellSpacing.setAfterAutospacing(tmpSpacing.getAfterAutospacing());
                    }
                    if (tmpSpacing.getAfterLines() != null) {
                        cellSpacing.setAfterLines(tmpSpacing.getAfterLines());
                    }
                    if (tmpSpacing.getBefore() != null) {
                        cellSpacing.setBefore(tmpSpacing.getBefore());
                    }
                    if (tmpSpacing.getBeforeAutospacing() != null) {
                        cellSpacing.setBeforeAutospacing(tmpSpacing.getBeforeAutospacing());
                    }
                    if (tmpSpacing.getBeforeLines() != null) {
                        cellSpacing.setBeforeLines(tmpSpacing.getBeforeLines());
                    }
                    if (tmpSpacing.getLine() != null) {
                        cellSpacing.setLine(tmpSpacing.getLine());
                    }
                    if (tmpSpacing.getLineRule() != null) {
                        cellSpacing.setLineRule(tmpSpacing.getLineRule());
                    }
                }
                // 复制段落缩进信息
                CTInd tmpInd = tmpPPr.getInd();
                if (tmpInd != null) {
                    CTInd cellInd = cellPPr.getInd() != null ? cellPPr.getInd() : cellPPr.addNewInd();
                    if (tmpInd.getFirstLine() != null) {
                        cellInd.setFirstLine(tmpInd.getFirstLine());
                    }
                    if (tmpInd.getFirstLineChars() != null) {
                        cellInd.setFirstLineChars(tmpInd.getFirstLineChars());
                    }
                    if (tmpInd.getHanging() != null) {
                        cellInd.setHanging(tmpInd.getHanging());
                    }
                    if (tmpInd.getHangingChars() != null) {
                        cellInd.setHangingChars(tmpInd.getHangingChars());
                    }
                    if (tmpInd.getLeft() != null) {
                        cellInd.setLeft(tmpInd.getLeft());
                    }
                    if (tmpInd.getLeftChars() != null) {
                        cellInd.setLeftChars(tmpInd.getLeftChars());
                    }
                    if (tmpInd.getRight() != null) {
                        cellInd.setRight(tmpInd.getRight());
                    }
                    if (tmpInd.getRightChars() != null) {
                        cellInd.setRightChars(tmpInd.getRightChars());
                    }
                }
            }
        }
    }
}
