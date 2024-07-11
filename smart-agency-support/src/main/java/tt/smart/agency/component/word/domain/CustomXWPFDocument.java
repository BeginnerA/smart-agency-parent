package tt.smart.agency.component.word.domain;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTGraphicalObject;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTAnchor;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;

import java.io.InputStream;

/**
 * <p>
 * 自定义 XWPFDocument, 修复图片插入失败问题问题
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Log4j2
public class CustomXWPFDocument extends XWPFDocument {

    private static final String PICXML = ""
            + "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">"
            + "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
            + "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
            + "         <pic:nvPicPr>"
            + "            <pic:cNvPr id=\"%s\" name=\"Generated\"/>"
            + "            <pic:cNvPicPr/>" + "         </pic:nvPicPr>"
            + "         <pic:blipFill>"
            + "            <a:blip r:embed=\"%s\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"
            + "            <a:stretch>"
            + "               <a:fillRect/>"
            + "            </a:stretch>" + "         </pic:blipFill>"
            + "         <pic:spPr>" + "            <a:xfrm>"
            + "               <a:off x=\"0\" y=\"0\"/>"
            + "               <a:ext cx=\"%s\" cy=\"%s\"/>"
            + "            </a:xfrm>"
            + "            <a:prstGeom prst=\"rect\">"
            + "               <a:avLst/>" + "            </a:prstGeom>"
            + "         </pic:spPr>" + "      </pic:pic>"
            + "   </a:graphicData>" + "</a:graphic>";

    public CustomXWPFDocument() {
        super();
    }

    public CustomXWPFDocument(InputStream in) throws Exception {
        super(in);
    }

    public CustomXWPFDocument(OPCPackage opcPackage) throws Exception {
        super(opcPackage);
    }

    /**
     * 创建图片
     *
     * @param blipId 临时 ID
     * @param id     ID
     * @param width  宽
     * @param height 高
     */
    public void createPicture(String blipId, int id, int width, int height) {
        final int emu = 9525;
        width *= emu;
        height *= emu;
        CTInline inline = createParagraph().createRun().getCTR().addNewDrawing().addNewInline();
        createPicture(blipId, id, width, height, inline);
    }

    /**
     * 创建图片
     *
     * @param run    XWPFRun
     * @param blipId 临时 ID
     * @param id     ID
     * @param width  宽
     * @param height 高
     */
    public void createPicture(XWPFRun run, String blipId, int id, int width, int height) {
        final int emu = 9525;
        width *= emu;
        height *= emu;
        CTInline inline = run.getCTR().addNewDrawing().addNewInline();
        createPicture(blipId, id, width, height, inline);
    }

    /**
     * 创建图片
     *
     * @param blipId 临时 ID
     * @param id     ID
     * @param width  宽
     * @param height 高
     * @param inline CTInline
     */
    private void createPicture(String blipId, int id, int width, int height, CTInline inline) {
        String picXml = String.format(PICXML, id, blipId, width, height);
        XmlToken xmlToken = null;
        try {
            xmlToken = XmlToken.Factory.parse(picXml);
        } catch (XmlException xe) {
            log.error(xe.getMessage(), xe);
        }

        inline.set(xmlToken);

        inline.setDistT(0);
        inline.setDistB(0);
        inline.setDistL(0);
        inline.setDistR(0);

        CTPositiveSize2D extent = inline.addNewExtent();
        extent.setCx(width);
        extent.setCy(height);

        CTNonVisualDrawingProps docPr = inline.addNewDocPr();
        docPr.setId(id);
        docPr.setName("Picture " + id);
        docPr.setDescr("Generated");
    }

    /**
     * 创建图片
     *
     * @param run     XWPFRun
     * @param blipId  临时 ID
     * @param id      ID
     * @param width   宽
     * @param height  高
     * @param isAbove 是之前的
     */
    public void createPicture(XWPFRun run, String blipId, int id, int width, int height, boolean isAbove) {
        createPicture(run, blipId, id, width, height);
        CTDrawing drawing = run.getCTR().getDrawingArray(0);
        CTGraphicalObject graphicalObject = drawing.getInlineArray(0).getGraphic();
        // 拿到新插入的图片替换添加 CTAnchor 设置浮动属性，删除 inline 属性, 设置图片大小
        CTAnchor anchor = getAnchorWithGraphic(graphicalObject, "EasyPoi" + RandomUtil.randomNumbers(10),
                Units.toEMU(width), Units.toEMU(height),
                Units.toEMU(50), Units.toEMU(0), isAbove);
        drawing.setAnchorArray(new CTAnchor[]{anchor});
        drawing.removeInline(0);
    }


    /**
     * @param graphicalObject 图片数据
     * @param deskFileName    图片描述
     * @param width           宽
     * @param height          高
     * @param leftOffset      水平偏移 left
     * @param topOffset       垂直偏移 top
     * @param behind          文字上方，文字下方
     * @return CTAnchor
     */
    public static CTAnchor getAnchorWithGraphic(CTGraphicalObject graphicalObject, String deskFileName, int width,
                                                int height, int leftOffset, int topOffset, boolean behind) {
        String anchorXML =
                "<wp:anchor xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" "
                        + "simplePos=\"0\" relativeHeight=\"0\" behindDoc=\"" + ((behind) ? 1 : 0) + "\" locked=\"0\" layoutInCell=\"1\" allowOverlap=\"1\">"
                        + "<wp:simplePos x=\"0\" y=\"0\"/>"
                        + "<wp:positionH relativeFrom=\"column\">"
                        + "<wp:posOffset>" + leftOffset + "</wp:posOffset>"
                        + "</wp:positionH>"
                        + "<wp:positionV relativeFrom=\"paragraph\">"
                        + "<wp:posOffset>" + topOffset + "</wp:posOffset>" +
                        "</wp:positionV>"
                        + "<wp:extent cx=\"" + width + "\" cy=\"" + height + "\"/>"
                        + "<wp:effectExtent l=\"0\" t=\"0\" r=\"0\" b=\"0\"/>"
                        + "<wp:wrapNone/>"
                        + "<wp:docPr id=\"1\" name=\"Drawing 0\" descr=\"" + deskFileName + "\"/><wp:cNvGraphicFramePr/>"
                        + "</wp:anchor>";

        CTDrawing drawing = null;
        try {
            drawing = CTDrawing.Factory.parse(anchorXML);
        } catch (XmlException e) {
            log.error(e);
        }
        assert drawing != null;
        CTAnchor anchor = drawing.getAnchorArray(0);
        anchor.setGraphic(graphicalObject);
        return anchor;
    }

}
