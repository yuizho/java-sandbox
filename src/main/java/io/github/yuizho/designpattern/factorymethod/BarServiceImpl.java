package io.github.yuizho.designpattern.factorymethod;

public class BarServiceImpl implements BarService {
    @Override
    public void execute() {
        System.out.println("BarServiceImpl#execute()");
    }
}
