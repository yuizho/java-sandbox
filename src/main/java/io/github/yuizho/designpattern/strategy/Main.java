package io.github.yuizho.designpattern.strategy;

public class Main {
    public static void main(String... args) {
        new Processor(() -> System.out.println("FooAction")).execute();
    }
}
