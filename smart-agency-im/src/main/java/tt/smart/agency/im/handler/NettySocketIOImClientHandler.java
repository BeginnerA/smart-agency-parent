package tt.smart.agency.im.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * <p>
 * Netty SocketIO 即时通讯客户端处理器
 * </p>
 *
 * @author YangMC
 * @version V1.0
 **/
public class NettySocketIOImClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(@NotNull ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered=====================");
        ctx.fireChannelRegistered();
    }

    @Override
    public void channelUnregistered(@NotNull ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered=====================");
        ctx.fireChannelUnregistered();
    }

    @Override
    public void channelActive(@NotNull ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive=====================");
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(@NotNull ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive=====================");
        ctx.fireChannelInactive();
    }

    @Override
    public void channelRead(@NotNull ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead=====================");
        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelReadComplete(@NotNull ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete=====================");
        ctx.fireChannelReadComplete();
    }

    @Override
    public void userEventTriggered(@NotNull ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("userEventTriggered=====================");
        ctx.fireUserEventTriggered(evt);
    }

    @Override
    public void channelWritabilityChanged(@NotNull ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelWritabilityChanged=====================");
        ctx.fireChannelWritabilityChanged();
    }

    @Override
    public void exceptionCaught(@NotNull ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught=====================");
        ctx.fireExceptionCaught(cause);
    }

}
