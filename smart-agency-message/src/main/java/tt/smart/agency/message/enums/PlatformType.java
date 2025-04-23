package tt.smart.agency.message.enums;

/**
 * <p>
 * 平台类型
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public interface PlatformType {

    /**
     * 得到平台工厂
     *
     * @return 平台工厂
     */
    <F> F getPlatformFactory();
}
