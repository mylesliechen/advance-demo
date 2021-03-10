package cx.commom.sample.concurrent;

import java.util.concurrent.TimeUnit;

public class ThreadGroupDaemon {
    //线程组设置为守护线程组，并不会影响其线程是否为守护线程，仅仅表示当它内部没有active的线程的时候，会自动destroy
    public static void main(String[] args) throws InterruptedException {
        ThreadGroup group1 = new ThreadGroup("group1");
        new Thread(group1, () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "group1-thread1").start();
        ThreadGroup group2 = new ThreadGroup("group2");
        new Thread(group2, () -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "group1-thread2").start();
        group2.setDaemon(true);
        TimeUnit.SECONDS.sleep(2);
        System.out.println(group1.isDestroyed());//
        System.out.println(group2.isDestroyed());//虽然2被设置为daemon，但是当前2中有active（2中的线程还在执行） 线程，所以false

        TimeUnit.SECONDS.sleep(2);
        System.out.println(group1.isDestroyed());//
        System.out.println(group2.isDestroyed());//2设置为daemon，当前没有active线程了，true
    }
}
