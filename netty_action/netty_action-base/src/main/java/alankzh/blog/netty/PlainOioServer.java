package alankzh.blog.netty;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

@Slf4j
public class PlainOioServer {

    public void server(int port) throws IOException {
        final ServerSocket serverSocket = new ServerSocket(port);

        for (;;){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OutputStream out;
                    try(Socket clientSocket = serverSocket.accept()) {
                        log.info("Accepted connection from {}", clientSocket);
                        out = clientSocket.getOutputStream();
                        out.write("Hi\r\n".getBytes(Charset.forName("UTF-8")));
                        out.flush();
                    } catch (IOException e) {
                        log.error("client get outputStream error", e);
                    }
                }
            }).start();
        }
    }

}
