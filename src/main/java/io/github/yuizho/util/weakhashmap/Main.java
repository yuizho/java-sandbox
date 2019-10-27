package io.github.yuizho.util.weakhashmap;

import java.util.Map;
import java.util.WeakHashMap;

public class Main {
    private static final Map<Main, Main> CACHE = new WeakHashMap<>();
    public static void main(String... args) {
        Main key = new Main();
        Main value = new Main();
        CACHE.put(key, value);
        System.out.println(CACHE);

        System.gc();
        System.out.println(CACHE);

        key = null;
        System.out.println(CACHE);

        System.gc();
        System.out.println(CACHE);
    }

    @Override
    public String toString() {
        return Long.toString(hashCode());
    }
}
