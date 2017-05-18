package com.denis.learning.nio.multicast;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Date;

import static java.net.StandardSocketOptions.IP_MULTICAST_IF;

public class MulticastServer {
    public static void main(String[] args) throws IOException {
        NetworkInterface networkInterface = NetworkInterface.getByName("enp3s0");

        try(DatagramChannel datagramChannel = DatagramChannel.open(StandardProtocolFamily.INET)) {
            datagramChannel.setOption(IP_MULTICAST_IF, networkInterface);

            datagramChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);

            final int DEFAULT_PORT = 5555;
            final String GROUP = "225.4.5.6";


            datagramChannel.bind(new InetSocketAddress(DEFAULT_PORT));
            System.out.println("Date-time server is ready ... shortly I'll start sending ...");

            ByteBuffer datetime;
            while (true) {
                //sleep for 10 seconds
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {}
                System.out.println("Sending data ...");
                datetime = ByteBuffer.wrap(new Date().toString().getBytes());
                datagramChannel.send(datetime, new InetSocketAddress(InetAddress.getByName(GROUP), DEFAULT_PORT));
                datetime.flip();
            }
        }



    }
}
