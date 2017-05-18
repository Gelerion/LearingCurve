package com.denis.learning.oop.lsp;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public void checkBehaviourForRegularList()
    {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        assertEquals(1, list.size());
    }

    public void checkBehaviourForDoubleList()
    {
        List<Integer> list = new DoubleList<Integer>();
        list.add(1);
        assertEquals(1, list.size()); // fail
    }






    private void assertEquals(int expected, int size) {
        if(expected != size) throw new RuntimeException("Not equals");
    }
}
