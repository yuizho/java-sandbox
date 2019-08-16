package io.github.yuizho.inherit;

import io.github.yuizho.inherit.sub.Child;

public class Main {
    public static void main(String... args) {
        Parent p = new Child();
        p.greeting();
        System.out.println(p.getBean());
    }
}
