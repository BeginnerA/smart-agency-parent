package tt.smart.agency.component.word.handler;

import lombok.Data;

/**
 * <p>
 * 验证处理程序结果
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
@Data
public class VerifyHandlerResult {

    /**
     * 是否正确
     */
    private boolean success;
    /**
     * 错误信息
     */
    private String msg;

    public VerifyHandlerResult() {

    }

    public VerifyHandlerResult(boolean success) {
        this.success = success;
    }

    public VerifyHandlerResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }
}
