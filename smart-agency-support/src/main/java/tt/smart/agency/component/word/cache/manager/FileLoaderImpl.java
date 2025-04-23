package tt.smart.agency.component.word.cache.manager;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.util.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/**
 * <p>
 * 文件加载类,根据路径加载指定文件
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Log4j2
public class FileLoaderImpl implements IFileLoader {

    @Override
    public byte[] getFile(String url) {
        InputStream fileis = null;
        ByteArrayOutputStream baos = null;
        try {

            //判断是否是网络地址
            if (url.startsWith("http")) {
                URL urlObj = new URI(url).toURL();
                URLConnection urlConnection = urlObj.openConnection();
                urlConnection.setConnectTimeout(30 * 1000);
                urlConnection.setReadTimeout(60 * 1000);
                urlConnection.setDoInput(true);
                fileis = urlConnection.getInputStream();
            } else {
                // 先用绝对路径查询，再查询相对路径
                try {
                    fileis = new FileInputStream(url);
                } catch (FileNotFoundException e) {
                    // 获取项目文件
                    fileis = FileLoaderImpl.class.getClassLoader().getResourceAsStream(url);
                }
            }

            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while (true) {
                assert fileis != null;
                if (!((len = fileis.read(buffer)) > -1)) {
                    break;
                }
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return baos.toByteArray();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(fileis);
            IOUtils.closeQuietly(baos);
        }
        log.error("{}这个路径文件没有找到,请查询", fileis);
        return null;
    }

}
