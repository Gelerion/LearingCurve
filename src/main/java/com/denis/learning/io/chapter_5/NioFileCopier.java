package com.denis.learning.io.chapter_5;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileCopier {
    public static void main(String[] args) throws IOException {
        FileInputStream fin = new FileInputStream("file1");
        FileOutputStream fout = new FileOutputStream("file2");

        FileChannel inChannel = fin.getChannel();
        FileChannel outChannel = fout.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);//1mb
        while (inChannel.read(buffer) > 0) {
            buffer.flip();
            while (buffer.hasRemaining()) outChannel.write(buffer);
            buffer.clear();
        }
    }
}
