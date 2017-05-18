package com.denis.learning.nio.async.file;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.READ;

public class AsyncFileOperations {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Path sitesFile = Paths.get("/home/denis-shuvalov/Documents", "sitesFile");
        //future(sitesFile);
        try(AsynchronousFileChannel channel = AsynchronousFileChannel.open(sitesFile, READ)) {
            Thread current = Thread.currentThread();

            ByteBuffer buffer = ByteBuffer.allocate(100); //100 bytes

            channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    System.out.print("Read bytes: " + result);
                    attachment.flip();
                    System.out.print(UTF_8.decode(attachment));
                    attachment.clear();
                    current.interrupt();
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    System.out.println(attachment);
                    System.out.println("Error:" + exc);
                    current.interrupt();
                }
            });

/*
            channel.read(buffer, 0, "Read operation status ...", new CompletionHandler<Integer, String>() {
                @Override
                public void completed(Integer result, String attachment) {
                    System.out.println(attachment);
                    System.out.print("Read bytes: " + result);
                    current.interrupt();
                }

                @Override
                public void failed(Throwable exc, String attachment) {
                    System.out.println(attachment);
                    System.out.println("Error:" + exc);
                    current.interrupt();
                }
            });
*/


            System.out.println("\nWaiting for reading operation to end ...\n");
            try {
                current.join();
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }

/*            buffer.flip();
            System.out.print(UTF_8.decode(buffer));
            buffer.clear();*/

            System.out.println("\n\nClose everything and leave! Bye, bye ...");
        }


    }

    private static void future(Path sitesFile) throws IOException, InterruptedException, ExecutionException {
        try(AsynchronousFileChannel channel = AsynchronousFileChannel.open(sitesFile, READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(100); //100 bytes
            Future<Integer> result = channel.read(buffer, 0);

            while (result.isDone()) {
                System.out.println("Do something else whi;e reading...");
            }

            System.out.println("Bytes read: " + result.get());

            buffer.flip();
            System.out.print(UTF_8.decode(buffer));
            buffer.clear();
        }
    }
}
