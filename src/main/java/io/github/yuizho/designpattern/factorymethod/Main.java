package io.github.yuizho.designpattern.factorymethod;

public class Main {
    public static void main(String... args) throws Exception {
        Action action = FooActionFactory.newFooAction();
        action.execute();
    }
}
