package tt.smart.agency.component.word.cache.manager;

import lombok.extern.log4j.Log4j2;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

/**
 * <p>
 * 缓存管理
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
@Log4j2
public final class POICacheManager {

    private static IFileLoader fileLoader = new FileLoaderImpl();
    private static final ThreadLocal<IFileLoader> LOCAL_FILE_LOADER = new ThreadLocal<>();

    /**
     * 得到文件输入流
     *
     * @param id ID
     * @return 文件输入流
     */
    public static InputStream getFile(String id) {
        try {
            byte[] result;
            // 复杂数据,防止操作原数据
            if (LOCAL_FILE_LOADER.get() != null) {
                result = LOCAL_FILE_LOADER.get().getFile(id);
            }
            result = fileLoader.getFile(id);
            result = Arrays.copyOf(result, result.length);
            return new ByteArrayInputStream(result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 设置文件加载器
     *
     * @param fileLoader 文件加载器
     */
    public static void setFileLoader(IFileLoader fileLoader) {
        POICacheManager.fileLoader = fileLoader;
    }

    /**
     * 设置一次文件加载器（一次线程有效）
     *
     * @param fileLoader 文件加载器
     */
    public static void setFileLoaderOnce(IFileLoader fileLoader) {
        if (fileLoader != null) {
            LOCAL_FILE_LOADER.set(fileLoader);
        }
    }

}
