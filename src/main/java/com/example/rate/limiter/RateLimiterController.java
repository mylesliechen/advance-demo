package com.example.rate.limiter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RateLimiterController {

    private final RateLimiter2 rateLimiter;

    public RateLimiterController(RateLimiter2 rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @GetMapping("/test")
    public void test() {
        long result = rateLimiter.acquire("type", "resource", 1024 * 1024L);
        log.info("result : {}", result);
    }
}
