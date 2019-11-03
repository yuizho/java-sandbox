package io.github.yuizho.designpattern.factorymethod;

public class FooActionForTest extends FooAction {
    protected BarService createBarService() {
        return new MockBarService();
    }
}
