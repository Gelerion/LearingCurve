package com.denis.learning.io.chapter_5;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class LockingCopier {
    public static void main(String[] args) throws IOException {
        FileInputStream inFile = new FileInputStream(args[0]);
        FileOutputStream outFile = new FileOutputStream(args[1]);
        FileChannel inChannel = inFile.getChannel( );
        FileChannel outChannel = outFile.getChannel( );

        //waits for lock on the file
        FileLock outLock = outChannel.lock();
        FileLock inLock = inChannel.lock(0, inChannel.size(), true);

        inChannel.transferTo(0, inChannel.size(), outChannel);

        //outChannel.force( ); like flush
        outLock.release();
        //outChannel.close();
        inLock.release();
    }
}
