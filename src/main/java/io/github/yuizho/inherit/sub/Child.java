package io.github.yuizho.inherit.sub;

import io.github.yuizho.inherit.Parent;

public class Child extends Parent {
    @Override
    public void greeting() {
        System.out.println("hello this is child");
        super.greeting(); // hello this is parent
    }

    @Override
    protected Bean getBean() {
        return new Bean("this is the bean created by child");
    }
}
