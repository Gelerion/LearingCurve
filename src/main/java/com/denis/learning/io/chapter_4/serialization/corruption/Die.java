package com.denis.learning.io.chapter_4.serialization.corruption;

import java.io.*;
import java.util.Random;

public class Die implements Serializable {

    /**
     * serialization provides a back door through which the value of face can be changed.
     * Default serialization uses neither constructors nor setter methods; it accesses the private
     * field directly. Thus it’s possible for someone to manually edit the bytes of a serialized
     * Die object so that the value of the face field is greater than 6 or less than 1.
     */
    private int face = 1;
    Random shooter = new Random();

    public Die(int face) {
        if (face < 1 || face > 6) throw new IllegalArgumentException();
        this.face = face;
    }

    public final int getFace() {
        return this.face;
    }

    public void setFace(int face) {
        if (face < 1 || face > 6) throw new IllegalArgumentException();
        this.face = face;
    }

    public int roll() {
        this.face = (Math.abs(shooter.nextInt()) % 6) + 1;
        return this.face;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        if(face < 1 || face > 6) throw new InvalidObjectException("Illegal die value: " + this.face);
    }
}
