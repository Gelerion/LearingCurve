package com.denis.learning.io.chapter_4;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class URLDigest {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        URL url = new URL("http://www.rambler.ru");
        InputStream in = url.openStream();

        byte[] buffer = new byte[128];
        MessageDigest sha = MessageDigest.getInstance("SHA");
        while (true) {
            int bytesRead = in.read(buffer);
            if(bytesRead > 0) {
                sha.update(buffer, 0, bytesRead);
            }
            else {
                break;
            }
        }

        byte[] result = sha.digest();

        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + " ");
        }

        System.out.println( );
        System.out.println(new BigInteger(result));

    }
}
