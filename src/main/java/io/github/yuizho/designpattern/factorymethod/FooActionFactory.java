package io.github.yuizho.designpattern.factorymethod;

import java.lang.reflect.InvocationTargetException;

public class FooActionFactory {
    public static FooAction newFooAction() {
        try {
            // VMオプションやら環境変数やらで切り替える
            return (FooAction) Class.forName("io.github.yuizho.designpattern.factorymethod.FooActionForTest")
                    .getDeclaredConstructor()
                    .newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
