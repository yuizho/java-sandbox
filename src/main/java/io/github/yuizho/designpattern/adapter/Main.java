package io.github.yuizho.designpattern.adapter;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

public class Main {
    public static void main(String... args) {
        Vector<String> vector = new Vector<>();
        vector.add("foo");
        vector.add("bar");
        vector.add("baz");

        Enumeration<String> enumeration = vector.elements();

        List<String> results1 = Collections.list(enumeration);
        System.out.println(results1);

        List<String> list = vector;
        enumeration = new IteratorAdapter<>(list.iterator());

        List<String> results2 = Collections.list(enumeration);
        System.out.println(results2);
    }
}
