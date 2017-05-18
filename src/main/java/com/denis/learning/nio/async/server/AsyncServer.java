package com.denis.learning.nio.async.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketOption;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncServer {
    static final int DEFAULT_PORT = 5555;
    static final String IP = "127.0.0.1";

    public void start() {
        try(AsynchronousServerSocketChannel asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open()) {
            if (asynchronousServerSocketChannel.isOpen()) {
                asynchronousServerSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024);
                asynchronousServerSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);

                Set<SocketOption<?>> options = asynchronousServerSocketChannel.supportedOptions();
                for(SocketOption<?> option : options) System.out.println("Also supported: " + option);

                asynchronousServerSocketChannel.bind(new InetSocketAddress(IP, DEFAULT_PORT));

                acceptConnections(asynchronousServerSocketChannel);
            }
            else {
                System.err.println("Could not open the socket");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void acceptConnections(AsynchronousServerSocketChannel asynchronousServerSocketChannel) throws IOException {
        try {
            Future<AsynchronousSocketChannel> asynchronousSocketChannelFuture = asynchronousServerSocketChannel.accept();
            AsynchronousSocketChannel asynchronousSocketChannel = asynchronousSocketChannelFuture.get();
            System.out.println("Incoming connection from: " + asynchronousSocketChannel.getRemoteAddress());

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (asynchronousSocketChannel.read(buffer).get() != -1) {
                buffer.flip();
                asynchronousSocketChannel.write(buffer).get();

                if (buffer.hasRemaining()) {
                    buffer.compact();
                }
                else {
                    buffer.clear();
                }
            }

            asynchronousSocketChannel.close();
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
