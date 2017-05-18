package com.denis.learning.io.chapter_3.flate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class DirectInflater {

    public static void main(String[] args) throws IOException, DataFormatException {

        Inflater inf = new Inflater();

        byte[] input = new byte[1024];
        byte[] output = new byte[1024];

        for (int i = 0; i < args.length; i++) {
            if (!args[i].endsWith(DirectDeflater.DEFLATE_SUFFIX)) {
                System.err.println(args[i] + " does not look like a deflated file");
                continue;
            }

            FileInputStream fin = new FileInputStream(args[i]);
            FileOutputStream fout = new FileOutputStream(args[i].substring(0,
                    args[i].length() - DirectDeflater.DEFLATE_SUFFIX.length()));

            while (true) { //read and inflate the data
                int numRead = fin.read(input);

                if (numRead != -1) { // End of stream, finish inflating.
                    inf.setInput(input, 0, numRead);
                }

                int numDecompressed = inf.inflate(output, 0, output.length);
                while (numDecompressed > 0) {
                    fout.write(output, 0, numDecompressed);
                }

                // At this point inflate( ) has returned 0. Let's find out why.
                if (inf.finished()) { // all done
                    break;
                }
                else if (inf.needsDictionary()) { // We don't handle dictionaries.
                    System.err.println("Dictionary required! bailing...");
                    break;
                }
                else if (inf.needsInput()) {
                    continue;
                }
            }

            fin.close();
            fout.flush();
            fout.close();
            inf.reset();
        }
    }
}