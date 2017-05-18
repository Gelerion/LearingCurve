package com.denis.learning.deathmatch.print;

public class RunPrint {
    public static void main(String[] args) {
        Person p1 = new Person();
        Person p2 = new Student();

        p1.name = "Ricki";
        p2.name = "Martin"; //public call to Person's name, and runtime call for Student's overridden toString

        System.out.println(p1.toString() + p2);
    }
}
