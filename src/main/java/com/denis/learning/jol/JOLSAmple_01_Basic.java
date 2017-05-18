package com.denis.learning.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.layouters.HotSpotLayouter;
import org.openjdk.jol.vm.VM;

import java.util.*;

import static java.lang.System.out;

public class JOLSAmple_01_Basic {

    /*
     * This sample showcases the basic field layout.
     * You can see a few notable things here:
     *   a) how much the object header consumes;
     *   b) how fields are laid out;
     *   c) how the external alignment beefs up the object size
     */

    public static void main(String[] args) {
        out.println(VM.current().details());
//        out.println(ClassLayout.parseClass(A.class).toPrintable());
        out.println(ClassLayout.parseClass(ArrayList.class).toPrintable());

    }

    public static class A {
        boolean f;
    }


}


