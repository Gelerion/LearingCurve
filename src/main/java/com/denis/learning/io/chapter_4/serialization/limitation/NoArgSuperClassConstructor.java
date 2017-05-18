package com.denis.learning.io.chapter_4.serialization.limitation;

import java.io.*;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class NoArgSuperClassConstructor {


/*    Wrote object!
    java.io.InvalidClassException: java.util.zip.ZipFile; <init>
        at java.io.ObjectInputStream.inputObject(Compiled Code)
        at java.io.ObjectInputStream.readObject(ObjectInputStream.java:363)
        at java.io.ObjectInputStream.readObject(ObjectInputStream.java:226)
        at SerializableZipFileNot.main(SerializableZipFileNot.java:28)*/
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        SerializableZipFileNot szf = new SerializableZipFileNot("someZip");
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(bout);
        oout.writeObject(szf);
        oout.close();

        System.out.println("Wrote object!");

        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        ObjectInputStream oin = new ObjectInputStream(bin);
        SerializableZipFileNot o = (SerializableZipFileNot) oin.readObject();

        System.out.println("Read object!");


    }

    public static class SerializableZipFileNot extends ZipFile implements Serializable {
        public SerializableZipFileNot(String name) throws IOException {
            super(name);
        }

        public SerializableZipFileNot(File file) throws ZipException, IOException {
            super(file);
        }
    }

}
