package io.github.yuizho.time.format;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String... args) {
        // Parsing
        System.out.println("--------- Parsing");
        LocalDate date = LocalDate.parse(
                "2020/01/01",
                DateTimeFormatter.ofPattern("yyyy/MM/dd")
        );
        System.out.println(date);

        // formattting
        System.out.println("--------- Formatting");
        String localDateTime = LocalDateTime.of(2024, 10, 07, 12, 30, 11)
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd_HH:mm:ss.SSS"));
        System.out.println(localDateTime);
    }
}
