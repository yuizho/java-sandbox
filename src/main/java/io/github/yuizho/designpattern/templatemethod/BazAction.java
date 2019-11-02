package io.github.yuizho.designpattern.templatemethod;

public class BazAction extends TemplateAction {
    @Override
    protected void doExecute() {
        throw new RuntimeException();
    }
}
