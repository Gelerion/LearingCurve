package com.denis.learning;

import java.io.File;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TEst {
    public static void main(String[] args) {

        File abc = new File("/home/denis-shuvalov/Development/Learning Projects/RpcInvoke/src/main/resources/services.xml");
        long totalSpace = abc.getFreeSpace();
        System.out.println("totalSpace = " + totalSpace / 1024 / 1024);

        System.out.println(System.getProperty("user.dir"));

        CompletableFuture.supplyAsync(() -> "abc")
                .thenAccept(System.out::println);

//        System.out.println(Integer.MAX_VALUE);
/*        System.out.println(Integer.toBinaryString(356));
        int cast = -234678;
        System.out.println("To binary 234678 = " + Integer.toBinaryString(cast));
        System.out.println("(234678 >>> 8) & 0xFF = " + ((cast >>> 8)  & 0xFF));
        System.out.println("(234678 >>> 0) & 0xFF = " + ((cast >>> 0)  & 0xFF));
        System.out.println("(234678 >>> 8) & 0xFF = " + Integer.toBinaryString(((cast >>> 8)  & 0xFF)));
        System.out.println("(234678 >>> 0) & 0xFF = " + Integer.toBinaryString(((cast >>> 0)  & 0xFF)));
        System.out.println("356 &= 0xFF: " + (356 & 0xFF));
        byte i = (byte) (254);
        System.out.println("i = " + i);

        System.out.println(Integer.toHexString(255));

        System.out.println("~101100100 " + (~356) + " : " + Integer.toBinaryString(~356));

        short numS = (short) 0b01101001_10011101; //27037
        System.out.println("numS = " + numS);
        int byte1 = 0b10011101;
        int byte2 = 0b01101001;
        short fromLitleEnd = (short) ((byte2 << 8) | byte1);
        System.out.println("fromLitleEnd = " + fromLitleEnd);*/
//        System.out.println(-4 >> 1);

//        String a = "15935,6223,15940,336,306,354,348,63431,346,309,6227,308,5198,307";
//
//        String id = "348";
//        int commaIndex;
//
//        String entertainmentSegmentIds = "15935,6223,15940,336,306,354,348,63431,346,309,6227,308,5198,307";
//        //entertainmentIds.stream().flatMap(ids -> Arrays.stream(ids.split(","))).anyMatch(entertainmentSegmentIds::contains);
//
//
//        List<String> entertainmentIds = new ArrayList<>();
//        entertainmentIds.addTokenInfo("2");
//        entertainmentIds.addTokenInfo("12");
//        entertainmentIds.addTokenInfo("15940");
//
//        boolean res = false;
//        for (String entId : entertainmentSegmentIds.split(",")) {
//            res = entertainmentIds.stream().anyMatch(entId::equals);
//            if(res) break;
//        }
//
//        System.out.println("res = " + res);
    }
}
