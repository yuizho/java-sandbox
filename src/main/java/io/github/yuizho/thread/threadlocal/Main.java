package io.github.yuizho.thread.threadlocal;

import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final ThreadLocal<Map<String, String>> TEMP_VAL = ThreadLocal.withInitial(() -> new HashMap<>());
    public static void main(String... args) throws Exception {
        Map<String, String> map = TEMP_VAL.get();
        print();

        map.put("foo", "foo");
        print();

        new Thread(() -> {
            // 空
            print();
            TEMP_VAL.get().put("bar", "bar");
            // {bar=bar}
            print();
        }).start();

        Thread.sleep(100);

        // {foo=foo}
        print();
    }

    private static void print() {
        Map<String, String> map = TEMP_VAL.get();
        System.out.println(Thread.currentThread().getName() + ": " + map);
    }
}
