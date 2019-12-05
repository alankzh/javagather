package alankzh.blog.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class EchoServer {
    private final int port;

    public EchoServer(int port){
        this.port = port;
    }

    public void start() throws InterruptedException {
        final EchoServerHandler echoServerHandler = new EchoServerHandler();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
//        NioSctpServerChannel在windows不支持
        bootstrap.group(eventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // 当一个新的连接被接受时，一个新的子channel被创建，
                        // 此时对这个channel加入echoServerHandler
                        // echoServerHandler 被声明为@Shareable， 故使用的是相同的实例
                        ch.pipeline().addLast(echoServerHandler);
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1){
            log.error("Usage: {} <port>", EchoServer.class.getSimpleName());
            return;
        }
        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }

}
