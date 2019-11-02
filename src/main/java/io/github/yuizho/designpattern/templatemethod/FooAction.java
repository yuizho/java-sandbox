package io.github.yuizho.designpattern.templatemethod;

public class FooAction extends TemplateAction {
    @Override
    protected void doExecute() {
        System.out.println("FooAction");
    }
}
