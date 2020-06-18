package com.example.rate.limiter;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RateLimiter2Impl implements RateLimiter2 {

    private DefaultRedisScript<String> redisScript;
    private StringRedisTemplate stringRedisTemplate;

    private static final String DEFAULT_RATE = 1024*1024*1024L + "";
    private static final String DEFAULT_CAPACITY = 1024*1024*1024L * 2 + "";
    private static final String OSS = "OSS";
    private static final String SEPARATOR = "::";
    private static final String DEFAULT_RESOURCE = "{DEFAULT}";

    public RateLimiter2Impl(StringRedisTemplate stringRedisTemplate) {
        redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("bandwidth_rate_limiter.lua")));
        redisScript.setResultType(String.class);

        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public long acquire(String resourceType, String resource, long requested) {
        String realResource = OSS + SEPARATOR + resourceType + SEPARATOR + resource;
        String ret = stringRedisTemplate.execute(redisScript, Collections.singletonList(realResource), DEFAULT_RATE, DEFAULT_CAPACITY, requested + "");
        double needSleep = Double.parseDouble(ret);
        return (long)(needSleep * 1000);
    }
}
