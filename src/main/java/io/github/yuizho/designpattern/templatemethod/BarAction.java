package io.github.yuizho.designpattern.templatemethod;

public class BarAction extends TemplateAction {
    @Override
    protected void doExecute() {
        System.out.println("BarAction");
    }
}
