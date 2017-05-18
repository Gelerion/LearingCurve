package com.denis.learning.io.chapter_4.serialization.sealed;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SealedPoint {
    public static void main(String[] args) throws Exception {
        byte[] data = writeSealed();

        ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(data));
        SealedObject so = (SealedObject) oin.readObject();

/*      possibilities:
        getObject(Key key)
        getObject(Cipher c)
        getObject(Key key, String provider)*/

        if (so.getAlgorithm().startsWith("DES")) {
            byte[] desKeyData = {(byte) 0x90, (byte) 0x67, (byte) 0x3E, (byte) 0xE6,
                                 (byte) 0x42, (byte) 0x15, (byte) 0x7A, (byte) 0xA3, };

            DESKeySpec desKeySpec = new DESKeySpec(desKeyData);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey desKey = keyFactory.generateSecret(desKeySpec);

            Object o = so.getObject(desKey);
            System.out.println(o);
        }

    }

    private static byte[] writeSealed() throws Exception {
        Point tdp = new Point(32, 45);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(bout);

        // Create a key.
        byte[] desKeyData = {(byte) 0x90, (byte) 0x67, (byte) 0x3E, (byte) 0xE6,
                             (byte) 0x42, (byte) 0x15, (byte) 0x7A, (byte) 0xA3 };

        DESKeySpec desKeySpec = new DESKeySpec(desKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = keyFactory.generateSecret(desKeySpec);

        // Use Data Encryption Standard.
        Cipher des = Cipher.getInstance("DES/ECB/PKCS5Padding");
        des.init(Cipher.ENCRYPT_MODE, desKey);

        SealedObject so = new SealedObject(tdp, des);
        oout.writeObject(so);

        return bout.toByteArray();
    }
}
