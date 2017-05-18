package com.denis.learning.io.chapter_2.file.streams;

import com.denis.learning.io.chapter_2.naked.streams.StreamCopier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class FileRoot {
    public static void main(String[] args) throws IOException {
        File[] roots = File.listRoots();
//        System.out.println("roots = " + Arrays.toString(roots));

        //independent path creation
        File dir = new File(roots[0], "etc");
        File child = new File(dir, "hosts");
//        FileInputStream fis = new FileInputStream(child);

        Path path = Paths.get(roots[0].getPath(), "home", "denis-shuvalov", "Documents",
                "FileZilla", "Segmentation", "Development", "FileLocations.properties");

//        StreamCopier.copy(new FileInputStream(path.toFile()), System.out);
        StreamCopier.copy(Files.newInputStream(path, StandardOpenOption.READ), System.out);

    }
}
