package cx.commom.sample.concurrent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UncaughtException {
    public static void main(String[] args) {
        //Thread.setDefaultUncaughtExceptionHandler((thread, e) -> {
        //    log.error("{} error", thread.getName(), e);
        //});
        new Thread(() -> {
            int a = 1 / 0;
        }).start();
    }
}
