package com.denis.learning.rpc.invoke.serialization;

import java.io.Serializable;

class PersonalDetails implements Serializable {
    transient int age = 40;
    char gender = 'F';

    public PersonalDetails(int age, char gender) {
        this.age = age;
        this.gender = gender;
    }
}