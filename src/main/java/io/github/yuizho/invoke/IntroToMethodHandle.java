package io.github.yuizho.invoke;

import java.lang.invoke.*;

public class IntroToMethodHandle {
    public static void main(String... args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        MethodType replaceMethodType = MethodType.methodType(String.class, char.class, char.class);
        MethodHandle replaceMethodHandle = lookup.findVirtual(String.class, "replace", replaceMethodType);
        String result = (String) replaceMethodHandle.invokeExact("dummy", 'd', 'm');
        System.out.println(result);

        // TutorialのこのコードだとIllegalAccessExceptionになる
        // https://stackoverflow.com/questions/34069386/methodhandle-to-a-getter-setter-from-another-class-gives-a-nosuchfielderror
        // findGetter, findSettterはfieldをlookupしていそう
        //MethodHandle getterHandle = lookup.findGetter(Example.class, "field", String.class);
        //MethodHandle setterHandle = lookup.findSetter(Example.class, "field", String.class);

        MethodHandle getterHandle = lookup.findVirtual(Example.class, "getField", MethodType.methodType(String.class));
        MethodHandle setterHandle = lookup.findVirtual(Example.class, "setField", MethodType.methodType(void.class, String.class));

        Example example = new Example("initial value");
        System.out.println(getterHandle.invoke(example));
        setterHandle.invoke(example, "new value");
        System.out.println(getterHandle.invoke(example));
    }
}

class Example {
    private String field;

    public Example(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
