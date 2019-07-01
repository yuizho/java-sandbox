package io.github.yuizho.stream.match;

import java.util.List;

public class Main {
    public static void main(String... args) {
        List<String> strs = List.of("a", "b", "c", "d");

        // 一つでもマッチするものがあればtrue
        boolean anyMatch =
                strs.stream()
                        .anyMatch(elm -> elm.equals("a"));
        System.out.println(anyMatch);

        boolean allMatch =
                strs.stream()
                        // 空リストに対して使うとtrueになる
                        // あんまり使わないほうがよいかも
                        .allMatch(elm -> elm.length() == 1);
        System.out.println(allMatch);

        // 1個も一致しなかったらtrueになる
        boolean noneMatch =
                strs.stream()
                .noneMatch(elm -> elm.equals("e"));
        System.out.println(noneMatch);
    }
}
