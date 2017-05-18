package com.denis.learning.io.chapter_5.channles;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class GatheringChannels {
    public static void main(String[] args) throws IOException {
        ByteBuffer[] buffers = new ByteBuffer[3];
        for (int i = 0; i < 3; i++) {
            //some diff files
            RandomAccessFile raf = new RandomAccessFile("file" + i, "r");
            FileChannel channel = raf.getChannel();
            buffers[i] = channel.map(FileChannel.MapMode.READ_ONLY, 0, raf.length());
        }

        FileOutputStream outFile = new FileOutputStream(args[args.length-1]);
        FileChannel out = outFile.getChannel( );

        //makes one dangerous assumption, though: it only works if the write( )
        //method writes every byte from every buffer.
        out.write(buffers);

//        outer: while (true) {
//            out.write(buffers);
//            for (ByteBuffer buffer : buffers) {
//                if (buffer.hasRemaining()) continue outer;
//            }
//
//            break;
//        }

        out.close( );
    }
}
