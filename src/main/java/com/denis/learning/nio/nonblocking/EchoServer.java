package com.denis.learning.nio.nonblocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

import static java.net.StandardSocketOptions.SO_RCVBUF;
import static java.net.StandardSocketOptions.SO_REUSEADDR;
import static java.nio.charset.StandardCharsets.UTF_8;

public class EchoServer {

    private Map<SocketChannel, List<byte[]>> keepDataTrack = new HashMap<>(); //IdentityHashMap
    private ByteBuffer buffer = ByteBuffer.allocate(2 * 1024);

    private void start() {
        final int DEFAULT_PORT = 5555;

        try (Selector selector = Selector.open();
             ServerSocketChannel serverSocketChannel= ServerSocketChannel.open()) {

            //check that both of them were successfully opened
            if (selector.isOpen() && serverSocketChannel.isOpen()) {
                serverSocketChannel.configureBlocking(false);

                serverSocketChannel.setOption(SO_RCVBUF, 256 * 1024);
                serverSocketChannel.setOption(SO_REUSEADDR, true);

                //bind the server socket channel to port
                serverSocketChannel.bind(new InetSocketAddress(DEFAULT_PORT));

                //register the current channel with the given selector
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

                //display a waiting message while ... waiting!
                System.out.println("Waiting for connections ...");

                while (true) {
                    //wait for incoming connections
                    selector.select();

                    Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
                    while (selectedKeys.hasNext()) {
                        SelectionKey key = selectedKeys.next();

                        //prevent the same key from coming up again
                        selectedKeys.remove();

                        if (!key.isValid()) {
                            System.out.println("Not valid key");
                            continue;
                        }

                        if (key.isAcceptable()) {
                            accept(key, selector);
                        }
                        else if (key.isReadable()) {
                            read(key);
                        }
                        else if (key.isWritable()) {
                            write(key);
                        }

                    }
                }
            }
            else {
                System.out.println("The server socket channel or selector cannot be opened!");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void accept(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept();
        clientChannel.configureBlocking(false);

        System.out.println("Incoming connection from: " + clientChannel.getRemoteAddress());
        clientChannel.write(ByteBuffer.wrap("Hello!\n".getBytes(UTF_8)));

        //register channel with selector for further I/O
        keepDataTrack.put(clientChannel, new ArrayList<>());
        clientChannel.register(selector, SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        buffer.clear();

        int numRead = -1;
        try {
            numRead = clientChannel.read(buffer);
        }
        catch (IOException e) {
            System.err.println("Cannot read error!");
        }

        if (numRead == -1) {
            this.keepDataTrack.remove(clientChannel);
            System.out.println("Connection closed by: " + clientChannel.getRemoteAddress());
            clientChannel.close();
            key.cancel();
            return;
        }

        byte[] data = new byte[numRead];
        System.arraycopy(buffer.array(), 0, data, 0, numRead);
        System.out.println(new String(data, UTF_8) + " from " + clientChannel.getRemoteAddress());

        // write back to client
        doEchoJob(key, data);
    }

    private void doEchoJob(SelectionKey key, byte[] data) {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        List<byte[]> channelData = keepDataTrack.get(clientChannel);
        channelData.add(data);

        key.interestOps(SelectionKey.OP_WRITE);
    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        List<byte[]> channelData = this.keepDataTrack.get(clientChannel);
        Iterator<byte[]> its = channelData.iterator();
        while (its.hasNext()) {
            byte[] it = its.next();
            its.remove();
            clientChannel.write(ByteBuffer.wrap(it));
        }

        key.interestOps(SelectionKey.OP_READ);
    }

    public static void main(String[] args) {
        new EchoServer().start();
    }


}
