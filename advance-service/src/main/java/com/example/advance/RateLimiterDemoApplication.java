package com.example.advance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class RateLimiterDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RateLimiterDemoApplication.class, args);
    }

}
