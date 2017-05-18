package com.denis.learning.io.chapter_3;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BufferedStreamCopier {
    public static void main(String[] args) throws IOException {
        File[] roots = File.listRoots();
        Path path = Paths.get(roots[0].getPath(), "home", "denis-shuvalov", "Documents",
                "FileZilla", "Segmentation", "Development", "FileLocations.properties");
        copy(new FileInputStream(path.toFile()), System.out);
    }

    public static void copy(InputStream in, OutputStream out) throws IOException {
        try (BufferedInputStream bin = new BufferedInputStream(in);
             BufferedOutputStream bout = new BufferedOutputStream(out)) {
            while (true) {
                int datum = bin.read();
                if(datum == -1) break;
                bout.write(datum);
            }
            bout.flush();
        }
    }
}
