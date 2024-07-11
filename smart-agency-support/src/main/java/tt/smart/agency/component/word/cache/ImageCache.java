package tt.smart.agency.component.word.cache;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.util.IOUtils;
import tt.smart.agency.component.word.cache.manager.POICacheManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * <p>
 * 图片缓存处理
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Log4j2
public class ImageCache {

    /**
     * 获取图片字节
     *
     * @param imagePath 图片路径
     * @return 图片字节
     */
    public static byte[] getImage(String imagePath) {
        InputStream is = POICacheManager.getFile(imagePath);
        final ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        try {
            int ch;
            while ((ch = is.read()) != -1) {
                swapStream.write(ch);
            }
            return swapStream.toByteArray();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(swapStream);
        }

    }

    /**
     * 缓存图像
     *
     * @param image 图片
     * @return 缓存图像
     */
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        // 这段代码确保图像中的所有像素都被加载
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null),
                    image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // 系统没有屏幕
        }
        if (bimage == null) {
            // 使用默认颜色模型创建一个缓冲图像
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null),
                    image.getHeight(null), type);
        }
        // 将图像复制到缓冲图像
        Graphics g = bimage.createGraphics();
        // 将图像绘制到缓冲图像上
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }
}
