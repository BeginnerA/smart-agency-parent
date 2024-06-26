package tt.smart.agency.im.command;

import tt.smart.agency.im.client.ImClient;

/**
 * <p>
 * SocketIO 命令
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class SocketIOCommand implements ImCommand {

    private ImClient client;

    public SocketIOCommand(ImClient client) {
        this.client = client;
    }

    @Override
    public void execute() {
    }
}
