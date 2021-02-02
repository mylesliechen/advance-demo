package cx.commom.sample.concurrent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadGroupAd {
    public static void main(String[] args) {
        ThreadGroup threadGroup = new ThreadGroup("group1");

        log.info("parentName:{}", threadGroup.getParent().getName());
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
        log.info("group.isDistroyd:{}", threadGroup.isDestroyed());
        mainGroup.list();
        threadGroup.destroy();
        log.info("group.isDistroyd:{}", threadGroup.isDestroyed());
        mainGroup.list();
    }
}
