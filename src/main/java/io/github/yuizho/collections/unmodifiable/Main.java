package io.github.yuizho.collections.unmodifiable;

import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String... args) {
        StringBuilder str1 = new StringBuilder("a");
        StringBuilder str2 = new StringBuilder("b");
        StringBuilder str3 = new StringBuilder("b");
        List<StringBuilder> strings
                = Collections.unmodifiableList(List.of(str1, str2, str3));
        System.out.println(strings);
        str1.append("-modified");
        // unmodifiale系のCollectionはaddなどは出来ないけど、
        // 中身がミュータブルの場合に書き換えられてしまう場合がある
        System.out.println(strings);
    }
}
