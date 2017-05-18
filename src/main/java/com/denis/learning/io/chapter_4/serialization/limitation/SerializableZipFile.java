package com.denis.learning.io.chapter_4.serialization.limitation;

import java.io.*;
import java.util.zip.ZipFile;

public class SerializableZipFile implements Serializable {
    private ZipFile zf;

    public SerializableZipFile(String name) throws IOException {
        this.zf = new ZipFile(name);
    }

    public SerializableZipFile(File file) throws IOException {
        this.zf = new ZipFile(file);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(zf.getName());
    }

    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        String filename = (String) in.readObject( );
        zf = new ZipFile(filename);
    }

    public static void main(String[] args) {
        try {
            SerializableZipFile szf = new SerializableZipFile(
                    "/home/denis-shuvalov/Documents/S3Local/Development/UDB/user-db-builder.jar");
            ByteArrayOutputStream bout = new ByteArrayOutputStream( );
            ObjectOutputStream oout = new ObjectOutputStream(bout);
            oout.writeObject(szf);

            oout.close( );
            System.out.println("Wrote object!");

            ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray( ));
            ObjectInputStream oin = new ObjectInputStream(bin);
            Object o = oin.readObject( );
            System.out.println("Read object!");
        }
        catch (Exception ex) {ex.printStackTrace( );}
    }
}
