package com.example.advance;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ThreadTest {
    @Test
    public void threadTead_1() {

        new Thread(this::sing).start();
    }

    private void sing() {
        System.out.println("sing");
    }

    private void dance() {
        System.out.println("dance");
    }
}
