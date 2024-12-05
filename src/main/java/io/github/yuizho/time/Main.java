package io.github.yuizho.time;

import java.time.*;
import java.time.temporal.TemporalAdjusters;

public class Main {
    public static void main(String... args) {
        // The LocalDate Class
        System.out.println("--------- The LocalDate Class");
        LocalDate date = LocalDate.of(2000, Month.NOVEMBER, 20);
        // 次の水曜日を取得
        LocalDate nextWed = date.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
        System.out.println(nextWed);

        // The MonthDay Class
        System.out.println("--------- The MonthDay Class");
        MonthDay monthDay = MonthDay.of(Month.FEBRUARY, 29);
        // うるう年化どうかのチェツク
        boolean validLeapYear = monthDay.isValidYear(2010);
        System.out.println(validLeapYear);

        // The Year Class
        System.out.println("--------- The Year Class");
        // 年だけでうるう年かどうかチェック
        boolean validLeapYearByYear = Year.of(2012).isLeap();
        System.out.println(validLeapYearByYear);

        // The ZoneId and ZoneOffset Classes
        System.out.println("--------- The ZoneId and ZoneOffset Classes");
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        System.out.println(tokyo);
        ZonedDateTime zonedDateTime = ZonedDateTime.now(tokyo);
        System.out.println(zonedDateTime);
        ZoneOffset offset = zonedDateTime.getOffset();
        System.out.println(offset);

        // The OffsetDateTime Class
        System.out.println("--------- The OffsetDateTime Class");
        LocalDateTime ld = LocalDateTime.of(2013, Month.JULY, 20, 19, 30);
        ZoneOffset zo = ZoneOffset.of("-08:00");
        // LocalDateにOffsetをつけてOffsetDatetime作れる
        OffsetDateTime offsetDate = OffsetDateTime.of(ld, zo);
        System.out.println(offsetDate);
    }
}
