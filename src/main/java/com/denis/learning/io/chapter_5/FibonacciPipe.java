package com.denis.learning.io.chapter_5;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class FibonacciPipe {
    public static void main(String[] args) throws IOException {
        Pipe pipe = Pipe.open();

        WritableByteChannel out = pipe.sink();
        ReadableByteChannel in  = pipe.source();

        FibonacciProducer producer = new FibonacciProducer(out, 30);
        FibonacciConsumer consumer = new FibonacciConsumer(in);

        producer.start();
        consumer.start();
    }

    private static class FibonacciProducer extends Thread {
        private WritableByteChannel out;
        private int howMany;

        public FibonacciProducer(WritableByteChannel out, int howMany) {
            this.out = out;
            this.howMany = howMany;
        }

        @Override
        public void run() {
            BigInteger low = BigInteger.ONE;
            BigInteger high = BigInteger.ONE;

            try {
                ByteBuffer buffer = ByteBuffer.allocate(4);
                buffer.putInt(this.howMany);
                buffer.flip();
                while (buffer.hasRemaining()) out.write(buffer);

                for (int i = 0; i < howMany; i++) {
                    byte[] data = low.toByteArray();
                    // These numbers can become arbitrarily large, and they grow
                    // exponentially so no fixed size buffer will suffice.
                    buffer = ByteBuffer.allocate(4 + data.length);
                    buffer.putInt(data.length);
                    buffer.put(data);
                    buffer.flip();
                    while (buffer.hasRemaining()) out.write(buffer);
                    BigInteger temp = high;
                    high = high.add(low);
                    low = temp;
                }

                out.close();
                System.err.println("Closed");

            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    out.close();
                }
                catch (IOException e) {
                    /*NOP*/
                }
            }
        }
    }

    private static class FibonacciConsumer extends Thread {
        private ReadableByteChannel in;

        public FibonacciConsumer(ReadableByteChannel in) {
            this.in = in;
        }

        @Override
        public void run() {
            ByteBuffer sizeBuffer = ByteBuffer.allocate(4);
            try {
                while (sizeBuffer.hasRemaining()) in.read(sizeBuffer);
                sizeBuffer.flip();

                int howMany = sizeBuffer.getInt();
                for (int i = 0; i < howMany; i++) {
                    sizeBuffer.clear();
                    while (sizeBuffer.hasRemaining()) in.read(sizeBuffer);
                    sizeBuffer.flip();
                    int size = sizeBuffer.getInt();
                    ByteBuffer data = ByteBuffer.allocate(size);
                    while (data.hasRemaining()) in.read(data);
                    data.flip();
                    System.out.println("Got: " + new BigInteger(data.array()));
                }

            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    in.close();
                }
                catch (IOException e) {
                    /*NOP*/
                }
            }
        }
    }
}
