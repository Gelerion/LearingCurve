package com.denis.learning.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import static java.lang.System.out;

public class JOLSample_04_Inheritance {

    /*
     * This is the example how VM lays out the fields in the hierarchy.
     *
     * The important invariant for JVM to maintain is laying out the
     * accessible fields at the same offsets regardless of the class
     * the field is being accessed through. That is, for classes B and C
     * below the field A.a should reside on the same offset. This prompts
     * VM to lay out the superclass fields first.
     */

    public static void main(String[] args) throws Exception {
        out.println(VM.current().details());
        out.println(ClassLayout.parseClass(C.class).toPrintable());
    }

    public static class A {
        int a;
    }

    public static class B extends A {
        int b;
    }

    public static class C extends B {
        int c;
    }
}
