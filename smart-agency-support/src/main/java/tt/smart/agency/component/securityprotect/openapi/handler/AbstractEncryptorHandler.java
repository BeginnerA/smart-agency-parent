package tt.smart.agency.component.securityprotect.openapi.handler;

/**
 * <p>
 * 抽象（加/密）处理程序
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public abstract class AbstractEncryptorHandler implements EncryptorHandler {

    protected final EncryptContext context;

    protected AbstractEncryptorHandler(EncryptContext context) {
        this.context = context;
    }
}
