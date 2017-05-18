package com.denis.learning.io.chapter_2;

import com.denis.learning.io.chapter_2.naked.streams.StreamCopier;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileDumper {
    public static final int ASC = 0;
    public static final int DEC = 1;
    public static final int HEX = 2;

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java FileDumper [-ahd] file1 file2...");
            return;
        }

        int firstArg = 0;
        int mode = ASC;

        if (args[0].startsWith("-")) {
            firstArg = 1;
            if (args[0].equals("-h")) mode = HEX;
            else if (args[0].equals("-d")) mode = DEC;
        }

        for (int i = firstArg; i < args.length; i++) {
            try {
                if (mode == ASC) dumpAscii(args[i]);
                else if (mode == DEC) dumpDecimal(args[i]);
                else if (mode == HEX) dumpHex(args[i]);

            }
            catch (IOException ex) {
                System.err.println("Error reading from " + args[i] + ": " + ex.getMessage());
            }
        }
    }

    private static void dumpHex(String fileName) throws IOException {
        try (FileInputStream fin = new FileInputStream(fileName)) {
            byte[] buffer = new byte[24];
            boolean end = false;

            while (!end) {
                int bytesRead = 0;
                while (bytesRead < buffer.length) {
                    int read = fin.read(buffer, bytesRead, buffer.length - bytesRead);
                    if(read == -1) {
                        end = true;
                        break;
                    }

                    bytesRead += read;
                }

                for (int i = 0; i < bytesRead; i++) {
                    int hex = buffer[i];
                    if(hex < 0) hex = 256 + hex;

                    if (hex >= 16) System.out.print(Integer.toHexString(hex) + " ");
                    else System.out.print("0" + Integer.toHexString(hex) + " ");
                }
                System.out.println( );
            }
        }
    }

    private static void dumpDecimal(String fileName) throws IOException {
        try (FileInputStream fin = new FileInputStream(new File(fileName))) {
            byte[] buffer = new byte[16];
            boolean end = false;

            while (!end) {
                int bytesRead = 0;
                int offset = 0;
                while (offset < buffer.length) {
                    bytesRead = fin.read(buffer, offset, buffer.length - offset);
                    if(bytesRead == -1) {
                        end = true;
                        break;
                    }
                    offset += bytesRead;
                }

                for (int i = 0; i < offset; i++) {
                    int dec = buffer[i];
                    if(dec < 0) dec = 256 + dec;

                    if(dec < 10) System.out.println("00" + dec + " ");
                    if(dec < 100) System.out.println("0" + dec + " ");
                    System.out.println(dec + " ");
                }
                System.out.println( );
            }
        }
    }

    private static void dumpAscii(String fileName) throws IOException {
        try (FileInputStream fin = new FileInputStream(new File(fileName))) {
            StreamCopier.copy(fin, System.out);
        }
    }
}
