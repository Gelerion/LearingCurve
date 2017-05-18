package com.denis.learning.io.chapter_5.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NewDataStuffer {
    private static byte[] data = new byte[255];

    public static void main(String[] args) {
        for (int i = 0; i < data.length; i++) data[i] = (byte) i;

        try {
            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.socket().bind(new InetSocketAddress(9000));

            Selector selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();

                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();

                    try {
                        if (key.isAcceptable()) {
                            SocketChannel client = server.accept();
                            System.out.println("Accepted from: " + client);
                            client.configureBlocking(false);
                            ByteBuffer source = ByteBuffer.wrap(data);
                            SelectionKey clientKey = client.register(selector, SelectionKey.OP_WRITE);
                            clientKey.attach(source);
                        }
                        else if (key.isWritable()) {
                            SocketChannel client = (SocketChannel) key.channel();
                            ByteBuffer output = (ByteBuffer) key.attachment();
                            if (!output.hasRemaining()) {
                                output.rewind();
                            }
                            client.write(output);
                        }
                    }
                    catch (IOException e) {
                        key.cancel( );
                        try {
                            key.channel().close( );
                        }
                        catch (IOException cex) { /*NOP*/ }
                    }
                }
            }
        }
        catch (IOException e) {
            System.err.println("Failed to open server socket");
            e.printStackTrace();
        }
    }
}
