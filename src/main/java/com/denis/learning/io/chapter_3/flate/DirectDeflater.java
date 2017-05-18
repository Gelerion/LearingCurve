package com.denis.learning.io.chapter_3.flate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;

public class DirectDeflater {
    public final static String DEFLATE_SUFFIX = ".dfl";

    public static void main(String[] args) throws IOException {
        Deflater def = new Deflater();
        byte[] input = new byte[1024];
        byte[] output = new byte[1024];

        for (int i = 0; i < args.length; i++) {
            FileInputStream fin = new FileInputStream(args[i]);
            FileOutputStream fout = new FileOutputStream(args[i] + DEFLATE_SUFFIX);

            while (true) { //read and deflate the data

                //fill input array
                int bytesRead = fin.read(input);

                if (bytesRead == -1) { //end
                    def.finish();  // deflate any data that remains in the input buffer.
                    while (!def.finished()) {
                        int numCompressedBytes = def.deflate(output, 0, output.length);
                        if (numCompressedBytes > 0) {
                            fout.write(output, 0, numCompressedBytes);
                        }
                    }
                    break;
                }


                def.setInput(input, 0, bytesRead);

                while (!def.needsInput()) {
                    int numCompressedBytes = def.deflate(output, 0, output.length);
                    while (numCompressedBytes > 0) {
                        fout.write(output, 0, numCompressedBytes);
                    }
                }
            }

            System.out.println((1.0 - def.getTotalOut() / def.getTotalIn()) * 100.0 + "% saved");
        }
    }
}
