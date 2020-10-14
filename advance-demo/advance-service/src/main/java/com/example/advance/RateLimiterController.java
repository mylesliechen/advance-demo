package com.example.advance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class RateLimiterController {

    private final RateLimiter2 rateLimiter;

    public RateLimiterController(RateLimiter2 rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @GetMapping("/test")
    public void test() {

        System.out.println("test");
        //long result = rateLimiter.acquire("type", "resource", 1024 * 1024L);
        //log.info("result : {}", result);
    }

    @GetMapping("/traffic/{rate}/{capacity}")
    public void testTrafficLimit(@PathVariable String rate, @PathVariable String capacity) throws InterruptedException {

        //789KB 限速100KB
        ExecutorService executorService = Executors.newFixedThreadPool(50);

        int actionCount = 1000;
        long start = System.currentTimeMillis();
        for (int i = 0; i < actionCount; ++i) {
            executorService.execute(() -> {
                try {
                    long result = rateLimiter.acquire("PUT", "OBJECT", 789 * 1024L, rate + "", capacity + "");
                    log.info("result : {}", result * 1.0 / 1000);
                } catch (Exception ex) {
//                    log.error("{}", ex.getMessage());
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1000, TimeUnit.HOURS);
        long duration = (System.currentTimeMillis() - start) / 1000L;
        log.info("qps :{}", actionCount / duration);
    }
}
