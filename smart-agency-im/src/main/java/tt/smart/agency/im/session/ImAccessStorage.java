package tt.smart.agency.im.session;

/**
 * <p>
 * 即时通讯会话访问存储
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public interface ImAccessStorage {

    /**
     * 用户上线
     *
     * @param userId 用户
     */
    void userOnline(String userId);

    /**
     * 用户离线
     *
     * @param userId 用户
     */
    void userOffline(String userId);

    /**
     * 刷新该用户标识下的全部资源（如：会话过期时间、主题过期时间）
     *
     * @param userId 用户
     */
    void refreshExpirationTime(String userId);

    /**
     * 将用户与对应的会话凭证保存
     *
     * @param userId  用户
     * @param session 会话
     */
    void saveUserAccess(String userId, String session);

    /**
     * 获取用户会话凭证
     *
     * @param userId 用户
     * @return 会话
     */
    String getUserAccess(String userId);

    /**
     * 从存储中删除用户及会话凭证
     *
     * @param userId 用户
     */
    void removeUserAccess(String userId);

}
