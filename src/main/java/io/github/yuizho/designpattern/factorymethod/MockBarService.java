package io.github.yuizho.designpattern.factorymethod;

public class MockBarService implements BarService {
    @Override
    public void execute() {
        System.out.println("Mock!");
    }
}
