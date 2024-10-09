package io.github.yuizho.time.temporal;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.*;

public class Main {
    public static void main(String... args) {
        // ChronoField and IsoFields
        System.out.println("--------- ChronoField and IsoFields");
        // LocalDateは一日の中の時間をサポートしないのでfalseになる
        boolean isSupported = LocalDate.now().isSupported(ChronoField.CLOCK_HOUR_OF_DAY);
        System.out.println(isSupported);

        // Temporal Adjuster
        System.out.println("--------- Temporal Adjuster");
        LocalDate date = LocalDate.of(2000, Month.OCTOBER, 15);
        DayOfWeek dotw = date.getDayOfWeek();
        System.out.printf("%s is on a %s%n", date, dotw);

        System.out.printf("first day of Month: %s%n",
                date.with(TemporalAdjusters.firstDayOfMonth()));

        // Temporal Query
        System.out.println("--------- Temporal Query");
        TemporalQuery<TemporalUnit> query = TemporalQueries.precision();
        System.out.printf("LocalDate precision is %s%n",
                LocalDate.now().query(query));

        // Custom Temporal Query
        System.out.printf("Is my birthday? %s%n", LocalDate.now().query(new MyBirthdayQuery()));
        LocalDate myBirthday = LocalDate.of(1988, 11, 2);
        System.out.printf("Is my birthday? %s%n", myBirthday.query(new MyBirthdayQuery()));

        TemporalQuery<Boolean> isAutumn = (temporal) -> {
            int month = temporal.get(ChronoField.MONTH_OF_YEAR);
            return month >= 9 && month <= 11;
        };
        System.out.printf("Is autumn? %s%n", LocalDate.now().query(isAutumn));

        // Duration
        System.out.println("--------- Duration");
        LocalDate start = LocalDate.of(2019, Month.JANUARY, 1);
        LocalDate end = LocalDate.of(2019, Month.FEBRUARY, 4);

        Duration.between(start.atStartOfDay(), end.atStartOfDay()).toDays();

        // ChronoUnit
        System.out.println("--------- ChronoUnit");
        long days = ChronoUnit.DAYS.between(start, end);
        long weeks = ChronoUnit.WEEKS.between(start, end);
        System.out.printf("days: %d, weeks: %d%n", days, weeks);
    }

    public static class MyBirthdayQuery implements TemporalQuery<Boolean> {
        @Override
        public Boolean queryFrom(TemporalAccessor temporal) {
            int year = temporal.get(ChronoField.YEAR);
            int month = temporal.get(ChronoField.MONTH_OF_YEAR);
            int day = temporal.get(ChronoField.DAY_OF_MONTH);
            return year == 1988 && month == 11 && day == 2;
        }
    }
}
