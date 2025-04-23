package tt.smart.agency.component.word.cache;

import lombok.extern.log4j.Log4j2;
import tt.smart.agency.component.word.cache.manager.POICacheManager;
import tt.smart.agency.component.word.domain.CustomXWPFDocument;

import java.io.InputStream;

/**
 * <p>
 * Word 缓存中心
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Log4j2
public class WordCache {

    /**
     * 获取 XWPFDocument
     *
     * @param url 模板地址
     * @return XWPFDocument
     */
    public static CustomXWPFDocument getXWPFDocument(String url) {
        InputStream is = null;
        try {
            is = POICacheManager.getFile(url);
            return new CustomXWPFDocument(is);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                assert is != null;
                is.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

}
