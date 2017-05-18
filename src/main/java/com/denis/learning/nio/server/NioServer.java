package com.denis.learning.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NioServer {
    static final int DEFAULT_PORT = 5555;
    static final String IP = "127.0.0.1";

    public static void main(String[] args) throws IOException {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            //Keep in mind that a newly created server socket channel is not bound or connected
            if (serverSocketChannel.isOpen()) {
                System.out.println("Open");
            }

            serverSocketChannel.configureBlocking(true);
            serverSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024);
            serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);

            serverSocketChannel.bind(new InetSocketAddress(IP, DEFAULT_PORT));

            ByteBuffer end = ByteBuffer.wrap("\n\r".getBytes());
            System.out.println("Waiting for connections ...");
            while (true) {
                try (SocketChannel clientSocketChannel = serverSocketChannel.accept()) {
                    System.out.println("Incoming connection from: " + clientSocketChannel.getRemoteAddress());

                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    while (clientSocketChannel.read(buffer) != -1) {
                        buffer.flip();
                        clientSocketChannel.write(buffer);

                        if (buffer.hasRemaining()) {
                            buffer.compact();
                        }
                        else {
                            buffer.clear();
                        }
                    }

                    clientSocketChannel.write(end);

                    //clientSocketChannel.shutdownInput();
                    //clientSocketChannel.shutdownOutput();
                }
            }
        }

    }
}
