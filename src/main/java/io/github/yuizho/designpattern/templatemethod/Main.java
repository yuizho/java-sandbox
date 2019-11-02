package io.github.yuizho.designpattern.templatemethod;

public class Main {
    public static void main(String... args) {
        new FooAction().execute();
        new BarAction().execute();
        new BazAction().execute();
    }
}
