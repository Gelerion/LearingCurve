package com.denis.learning.io.chapter_4.serialization.validation;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Person implements Serializable, ObjectInputValidation {
    static Map<String, String> thePeople = new HashMap<>();

    private String name;
    private String ss;

    public Person(String name, String ss) {
        this.name = name;
        this.ss = ss;
        thePeople.put(ss, name);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.registerValidation(this, 5);
        in.defaultReadObject();
    }

    @Override
    public void validateObject() throws InvalidObjectException {
        if (thePeople.containsKey(this.ss)) {
            throw new InvalidObjectException(this.name + " already exists");
        }
        else {
            thePeople.put(this.ss, this.name);
        }
    }
}
