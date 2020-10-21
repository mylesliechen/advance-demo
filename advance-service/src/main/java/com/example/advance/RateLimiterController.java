package com.example.advance;

import com.example.advance.model.CostDetail;
import com.example.advance.model.CostResult;
import com.example.advance.model.CostStatus;
import com.example.advance.model.CostSubUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

    @GetMapping(value = "/cost")
    public CostResult cost() throws Exception {

        CostDetail.CostDetailBuilder costDetailBuilder = CostDetail.builder();
        Map<String, Map<String, String>> resource = new HashMap<>();
        Map<String, String> map = new HashMap<>();
        map.put("jd_oss", "1");
        resource.put("storage", map);
        costDetailBuilder.jdOss(map);
        CostSubUser costSubUser = new CostSubUser();
        costSubUser.setPin("testsubPin");
        costDetailBuilder.costSubUser(costSubUser);
        CostDetail costDetail = costDetailBuilder.build();
        CostStatus costStatus = CostStatus.builder().code("ok").msg("ok").build();

        return CostResult.builder().data(costDetail).updatetime(System.currentTimeMillis()
                / 1000).status(costStatus).build();
        //return getCost();
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
