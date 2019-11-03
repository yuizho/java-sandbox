package io.github.yuizho.designpattern.factorymethod;

public class FooAction implements Action {
    @Override
    public void execute() {
        BarService barService = createBarService();
        barService.execute();
    }

    protected BarService createBarService() {
        return new BarServiceImpl();
    }
}
