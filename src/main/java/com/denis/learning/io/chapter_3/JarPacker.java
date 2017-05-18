package com.denis.learning.io.chapter_3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarInputStream;
import java.util.jar.Pack200;

public class JarPacker {
    public static void main(String[] args) throws IOException {
        Pack200.Packer packer = Pack200.newPacker();
        packer.properties().put(Pack200.Packer.EFFORT, "9");
        packer.pack(
                new JarInputStream(new FileInputStream("/home/denis-shuvalov/Development/" +
                        "Segmentation_Workspace/jinni-targeted-ads-segmentation/build/libs/segmentationProcess.jar")),
                new FileOutputStream("segmentationProcessPacked.pack")
        );
    }
}
