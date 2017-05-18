package com.denis.learning.io.chapter_5.channles;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.zip.GZIPInputStream;

public class NioSpecificChannel {
    public static void main(String[] args) throws IOException {
        FileInputStream fin = new FileInputStream(args[0]);
        GZIPInputStream gzin = new GZIPInputStream(fin);

        ReadableByteChannel in = Channels.newChannel(gzin);
        WritableByteChannel out = Channels.newChannel(System.out);

        ByteBuffer buffer = ByteBuffer.allocate(65536);
        while (in.read(buffer) != -1) {
            buffer.flip( );
            out.write(buffer);
            buffer.clear( );
        }
    }
}
