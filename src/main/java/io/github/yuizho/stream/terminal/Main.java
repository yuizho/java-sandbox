package io.github.yuizho.stream.terminal;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String... args) {
        // mapping
        var strings = List.of(
                "one", "two", "three", "four", "five",
                "six", "seven", "eight", "nine", "ten"
        );
        var intermediateCollectorResult = strings.stream()
                .collect(
                        Collectors.mapping(String::toUpperCase, Collectors.toList())
                );
        System.out.println(intermediateCollectorResult);

        // collectingAndThen
        var max = strings.stream()
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.groupingBy(String::length, Collectors.counting()),
                                map -> map.values()
                                        .stream()
                                        .mapToLong(aLong -> aLong)
                                        .max()
                        )
                )
                .orElse(0);
        System.out.println(max);
    }
}
