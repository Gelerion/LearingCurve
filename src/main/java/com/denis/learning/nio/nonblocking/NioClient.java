package com.denis.learning.nio.nonblocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import static java.net.StandardSocketOptions.*;
import static java.nio.charset.StandardCharsets.UTF_8;

/*• First, the client socket channel is registered with the SelectionKey.OP_CONNECT
option, since the client wants to be informed by the selector when the server
accepts the connection.
• Second, the client does not attempt a connection infinitely, since the server may
not be active; therefore, the Selector.select() method with timeout is proper for
it (a timeout of 500 to 1,000 milliseconds will do the job).
• Third, the client must check if the key is connectable (i.e., if the
SelectionKey.isConnectable() method returns true). If the key is connectable, it
mixes the socket channel isConnectionPending() and finishConnect() methods in
a conditional statement for closing the pending connections. When you need to
tell whether or not a connection operation is in progress on this channel, call the
SocketChannel.isConnectionPending() method, which returns a Boolean value.
Also, finishing the process of connecting a socket channel can be accomplished by
the SocketChannel.finishConnect() method.*/
public class NioClient {
    public static void main(String[] args) {
        final int DEFAULT_PORT = 5555;
        final String IP = "127.0.0.1";

        ByteBuffer buffer = ByteBuffer.allocateDirect(2 * 1024);
        ByteBuffer randomBuffer;
        CharBuffer charBuffer;
        Charset charset = UTF_8;
        CharsetDecoder decoder = charset.newDecoder();

        try (Selector selector = Selector.open();
             SocketChannel socketChannel = SocketChannel.open()) {

            //check that both of them were successfully opened
            if ((socketChannel.isOpen()) && (selector.isOpen())) {
                //configure non-blocking mode
                socketChannel.configureBlocking(false);

                socketChannel.setOption(SO_RCVBUF, 128 * 1024);
                socketChannel.setOption(SO_SNDBUF, 128 * 1024);
                socketChannel.setOption(SO_KEEPALIVE, true);

                //register the current channel with the given selector
                socketChannel.register(selector, SelectionKey.OP_CONNECT);

                //connect to remote host
                socketChannel.connect(new InetSocketAddress(IP, DEFAULT_PORT));
                System.out.println("Localhost: " + socketChannel.getLocalAddress());

                //waiting for the connection
                while (selector.select(1000) > 0) {
                    //get keys
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> its = keys.iterator();
                    //process each key
                    while (its.hasNext()) {
                        SelectionKey key = its.next();
                        its.remove();

                        try (SocketChannel keySocketChannel = (SocketChannel) key.channel()) {

                            //attempt a connection
                            if (key.isConnectable()) {
                                //signal connection success
                                System.out.println("I am connected!");

                                //close pending connections
                                if (keySocketChannel.isConnectionPending()) {
                                    keySocketChannel.finishConnect();
                                }

                                while (keySocketChannel.read(buffer) != -1) {
                                    buffer.flip();

                                    charBuffer = decoder.decode(buffer);
                                    System.out.println(charBuffer.toString());

                                    if (buffer.hasRemaining()) {
                                        buffer.compact();
                                    }
                                    else {
                                        buffer.clear();
                                    }

                                    int r = new Random().nextInt(100);
                                    if (r == 50) {
                                        System.out.println("50 was generated! Close the socket channel!");
                                        break;
                                    }
                                    else {
                                        randomBuffer = ByteBuffer.wrap("Random number:".concat(String.valueOf(r)).getBytes(UTF_8));
                                        keySocketChannel.write(randomBuffer);
                                        try {
                                            Thread.sleep(1500);
                                        }
                                        catch (InterruptedException ex) {
                                        }
                                    }
                                }

                            }
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
}
