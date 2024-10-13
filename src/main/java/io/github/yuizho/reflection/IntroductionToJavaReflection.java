package io.github.yuizho.reflection;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class IntroductionToJavaReflection {
    public static void main(String... args) throws ReflectiveOperationException {
        System.out.println(String.class.getClassLoader());
        System.out.println(IntroductionToJavaReflection.class.getClassLoader());

        var pi = "3141592653589793".chars()
                .map(i -> i - '0')
                .boxed()
                // mutableじゃないとだめ
                .collect(Collectors.toList());
        System.out.println(pi);
        for (var method : Collections.class.getMethods()) {
            if (method.getReturnType() == void.class
                    && method.getParameterCount() == 1
                    && method.getParameterTypes()[0] == List.class) {
                System.out.println("Calling " + method.getName() + "()");
                method.invoke(null, pi);
                System.out.println(pi);
            }
        }
    }
}