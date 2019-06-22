package io.github.yuizho.chrckerframework;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Objects;

/**
 * https://checkerframework.org/
 */
public class Main {
    public static void main(String... args) {
        @NonNull Integer result = getLength("aa");
        // ここで警告が出る
        System.out.println(result.intValue());
    }

    @Nullable
    public static Integer getLength(@Nullable String str) {
        // requreNonNullでも警告は消える
        //Objects.requireNonNull(str);
        // 無駄にボクシングしているが, これはNullにするため
        // strのNullCheckをしてい無いと警告がでる。
        return str.length();
    }
}
