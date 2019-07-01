package io.github.yuizho.thread.sync.volatiles;

import java.util.concurrent.TimeUnit;

public class Main {
    // volatileをつけると他のスレッドから、変更されたことがすぐ見れるようになる
    // ただし、排他制御をかけるわけでは無いので厳密な整合性が求められる共有オブジェクト
    // の更新を行うときは使うべきではない。
    private static volatile boolean stopRequested;

    public static void main(String... args)
            throws InterruptedException{
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            System.out.println("start");
            while (!stopRequested) {
                i++;
            }
            System.out.printf("stopped: %d\n", i);
        });

        backgroundThread.start();

        TimeUnit.MILLISECONDS.sleep(5);

        stopRequested = true;
    }
}
