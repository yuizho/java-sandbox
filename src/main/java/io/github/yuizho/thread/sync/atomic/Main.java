package io.github.yuizho.thread.sync.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    // スレッドセーフなboolean
    // https://docs.oracle.com/javase/jp/8/docs/api/java/util/concurrent/atomic/package-summary.html
    // 中身を見たらvolatileで制御している感じだった
    private static AtomicBoolean stopRequested = new AtomicBoolean(false);

    public static void main(String... args)
            throws InterruptedException{
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            System.out.println("start");
            while (!stopRequested.get()) {
                i++;
            }
            System.out.printf("stopped: %d\n", i);
        });

        backgroundThread.start();

        TimeUnit.MILLISECONDS.sleep(5);

        stopRequested.set(true);
    }
}
