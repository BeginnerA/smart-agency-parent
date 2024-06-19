package tt.smart.agency.im.session;

/**
 * <p>
 * 即时通讯会话
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public interface ImSession {

    /**
     * 获取会话
     *
     * @param <S> 会话
     * @return 会话
     */
    <S> S getSession();
}
