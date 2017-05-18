package com.denis.learning.rpc.invoke.serialization;

import java.io.*;

public class DeserializationClass {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Employee emp = null;

        try (FileInputStream fileIn = new FileInputStream("./employee.txt");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            emp = (Employee) in.readObject();
            emp.validateObject();
        }

//        System.out.println("serialVersionUID = "+ ObjectStreamClass.lookup(emp.getClass()).getSerialVersionUID());
        System.out.println("Deserializing Employee...");
        System.out.println("First Name of Employee: " + emp.firstName);
        System.out.println("Middle Name of Employee: " + emp.middleName);
        System.out.println("Last Name of Employee: " + emp.lastName);
        System.out.println("Nick Name of Employee: " + emp.nickName);
        System.out.println("Age of Employee: " + emp.pd.age);
        System.out.println("Gender of Employee: " + emp.pd.gender);
        System.out.println("Static Age of Employee: " + emp.pdstat.age);
        System.out.println("Static Gender of Employee: " + emp.pdstat.gender);
        System.out.println("Company Name: "+emp.companyName);
        System.out.println("Company CEO: "+emp.companyCEO);
        System.out.println("Manager Name: "+emp.managerName);
    }
}
