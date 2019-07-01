package io.github.yuizho.thread.sync.syncronized;

import java.util.concurrent.TimeUnit;

public class Main {
    private static boolean stopRequested;

    private static synchronized void stop() {
        stopRequested = true;
    }

    private static synchronized boolean isStopped() {
        return stopRequested;
    }

    public static void main(String... args)
            throws InterruptedException{
        Thread backgroundThread = new Thread(() -> {
            int i = 0;
            System.out.println("start");
            while (!isStopped()) {
                i++;
            }
            System.out.printf("stopped: %d\n", i);
        });

        backgroundThread.start();

        TimeUnit.MILLISECONDS.sleep(5);

        stop();
    }
}
