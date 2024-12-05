package io.github.yuizho.optional;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;
import java.util.stream.Stream;

class Main {
    public static void main(String[] args) {
        // --------- from Java9
        // or(Supplier<Optional> supplier
        var r = Optional.empty()
                // if the value is empty, then call getValueFromSomewhere
                .or(Main::getValueFromSomewhere)
                .orElse("default");
        System.out.println(r);

        // stream()
        Stream.of(
                Optional.of("aaa"),
                Optional.of("bbb"),
                Optional.of("ccc"),
                Optional.empty()
        )
                // Optional#streamは空なら空のStreamを返すので
                // emptyの要素は弾かれる
                .flatMap(Optional::stream)
                .forEach(System.out::println);

    }

    static Optional<String> getValueFromSomewhere() {
        return Optional.empty();
    }
}