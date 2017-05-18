package com.denis.learning.io.chapter_4.serialization.externalizable;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SerializableList extends ArrayList implements Externalizable {
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(size());
        for (int i = 0; i < size(); i++) {
            if (get(i) instanceof Serializable) {
                out.writeObject(get(i));
            }
            else {
                out.writeObject(null);
            }
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int size = in.readInt();
        this.ensureCapacity(size);
        for (int i = 0; i < size; i++) {
            this.add(in.readObject());
        }
    }

    public static void main(String[] args) throws Exception {
        SerializableList list = new SerializableList();
        list.add("Element 1");
        list.add(new Integer(9));
        list.add(new URL("http://www.oreilly.com/"));
        // not Serializable
        list.add(new Socket("www.oreilly.com", 80));
        list.add("Element 5");
        list.add(new Integer(9));
        list.add(new URL("http://www.oreilly.com/"));
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream temp = new ObjectOutputStream(bout);
        temp.writeObject(list);
        temp.close();
        System.out.println("Wrote");


        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        ObjectInputStream oin = new ObjectInputStream(bin);
        List out = (List) oin.readObject();
        Iterator iterator = out.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
