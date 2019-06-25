package io.github.yuizho.time;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;

import java.time.temporal.ChronoUnit;

public class Main {
    public static void main(String... args) {
        // ローカル時間
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        // タイムゾーン付き
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Europe/Rome"));
        System.out.println(zonedDateTime);

        // 計算系
        // 3日後
        System.out.println(localDateTime.plusDays(3));
        // 破壊的ではないので、元の時間はかわらない
        System.out.println(localDateTime);
        // 3日前
        System.out.println(localDateTime.minusDays(3));

        // 月の最終日
        System.out.println(localDateTime.with(TemporalAdjusters.lastDayOfMonth()));

        // 比較
        System.out.println(localDateTime.isAfter(localDateTime.plusSeconds(-1)));
        System.out.println(localDateTime.isEqual(localDateTime));

        // 時間の一部を表す、time.temporal.Temporalの実装たち
        System.out.println(YearMonth.from(localDateTime));
        // サポートしている単位の確認
        System.out.println(localDateTime.isSupported(ChronoUnit.SECONDS));
        // YearMonthは秒はサポートしてないのでfalseになる
        System.out.println(YearMonth.from(localDateTime)
                .isSupported(ChronoUnit.SECONDS)
        );
    }
}
