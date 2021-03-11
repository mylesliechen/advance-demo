package cx.commom.sample.concurrent.thread;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ThreadApiTest {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("thread1");
            try {
                TimeUnit.SECONDS.sleep(6);
                System.out.println("thread1 sleep over");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread1 = new Thread(() -> {
            System.out.println("thread2");
        });

        List<Thread> threads = Arrays.asList(thread, thread1);
        threads.forEach(Thread::start);
        thread.interrupt();

        threads.forEach(th -> {
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("main over");
    }
}
