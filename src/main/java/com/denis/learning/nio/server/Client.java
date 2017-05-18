package com.denis.learning.nio.server;

import java.io.IOException;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Client {
    static final int DEFAULT_PORT = 5555;
    static final String IP = "127.0.0.1";

    public static void main(String[] args) throws IOException {
        try(SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.configureBlocking(true);

            socketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 128 * 1024);
            socketChannel.setOption(StandardSocketOptions.SO_SNDBUF, 128 * 1024);
            socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
            socketChannel.setOption(StandardSocketOptions.SO_LINGER, 5);

            socketChannel.connect(new InetSocketAddress(IP, DEFAULT_PORT));

            if (socketChannel.isConnected()) {
                ByteBuffer buffer = ByteBuffer.allocate(4 * 1024);
                ByteBuffer out = ByteBuffer.wrap("Hello!".getBytes());
                socketChannel.write(out);

                while (socketChannel.read(buffer) != -1) {
                    buffer.flip();
                    String answer = StandardCharsets.UTF_8.decode(buffer).toString();
                    System.out.println(answer);

                    if(buffer.hasRemaining()) {
                        buffer.compact();
                    }
                    else {
                        buffer.clear();
                        break;
                    }
                }
            }

        }
    }
}
