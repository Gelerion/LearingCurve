package com.denis.learning.rpc.invoke.serialization;

import java.io.InvalidObjectException;
import java.io.ObjectInputValidation;
import java.io.Serializable;

public class Employee extends Manager implements Company, Serializable, ObjectInputValidation {

    public String firstName = "First Name";
    public transient static String middleName = "Middle Name";
    public transient final String lastName;
    public transient final String nickName = "Nick Name";
    private int explicitAge = 45;//Will not be read
    //If we make it transient then null pointer exception
    PersonalDetails pd = new PersonalDetails(26,'F');
    transient static PersonalDetails pdstat = new PersonalDetails(30,'M');

    public Employee(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public void validateObject() throws InvalidObjectException {
        System.err.println("Object Validation In Progress");
        if(this.explicitAge == 45 || this.nickName.equals("Nick Name")) {
            System.err.println("Object Validation Passed");
        }else{
            throw new InvalidObjectException("Object Validation Failed");
        }
    }
}
