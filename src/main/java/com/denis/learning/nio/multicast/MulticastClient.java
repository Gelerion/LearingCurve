package com.denis.learning.nio.multicast;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.MembershipKey;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class MulticastClient {
    public static void main(String[] args) throws IOException {
        final int DEFAULT_PORT = 5555;
        final int MAX_PACKET_SIZE = 65507;
        final String GROUP = "225.4.5.6";

        CharBuffer charBuffer = null;
        Charset charset = Charset.defaultCharset();
        CharsetDecoder decoder = charset.newDecoder();
        ByteBuffer datetime = ByteBuffer.allocateDirect(MAX_PACKET_SIZE);

        //create a new channel
        try (DatagramChannel datagramChannel = DatagramChannel.open(StandardProtocolFamily.INET)) {
            InetAddress group = InetAddress.getByName(GROUP);

            if(group.isMulticastAddress()) {

                if (datagramChannel.isOpen()) {
                    //get the network interface used for multicast
                    NetworkInterface networkInterface = NetworkInterface.getByName("enp3s0");
                    //set some options
                    datagramChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
                    //bind the channel to the local address
                    datagramChannel.bind(new InetSocketAddress(DEFAULT_PORT));

                    //join the multicast group and get ready to receive datagrams
                    MembershipKey key = datagramChannel.join(group, networkInterface);

                    while (true) {
                        if (key.isValid()) {
                            datagramChannel.receive(datetime);
                            datetime.flip();
                            System.out.println(decoder.decode(datetime).toString());
                            datetime.clear();
                        }
                        else {
                            System.out.println("Key s invalid");
                            break;
                        }
                    }
                }
                else {
                    System.out.println("The channel cannot be opened!");
                }
            }
            else {
                System.out.println("Multicast not supported");
            }

        }
    }
}
