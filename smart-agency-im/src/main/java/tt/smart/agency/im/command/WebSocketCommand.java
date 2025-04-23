package tt.smart.agency.im.command;

import tt.smart.agency.im.client.ImClient;

/**
 * <p>
 * WebSocket 命令
 * </p>
 *
 * @author MC_Yang
 * @version V1.0
 **/
public class WebSocketCommand implements ImCommand {

    private ImClient client;

    public WebSocketCommand(ImClient client) {
        this.client = client;
    }

    @Override
    public void execute() {

    }
}
