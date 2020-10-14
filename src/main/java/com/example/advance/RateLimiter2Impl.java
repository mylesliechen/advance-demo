package com.example.advance;

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

    private static final String DEFAULT_RATE = 200 * 1024 + "";// 200KB
    private static final String DEFAULT_CAPACITY = 200 * 1024 * 2 + ""; //400KB
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
        System.out.println(realResource);
        String ret = stringRedisTemplate.execute(redisScript, Collections.singletonList(realResource), DEFAULT_RATE, DEFAULT_CAPACITY, requested + "");
        double needSleep = Double.parseDouble(ret);
        return (long) (needSleep * 1000);
    }

    @Override
    public long acquire(String resourceType, String resource, long requested, String rate, String capacity) {
        String realResource = OSS + SEPARATOR + resourceType + SEPARATOR + resource;
        //System.out.println(realResource);
        String ret = stringRedisTemplate.execute(redisScript, Collections.singletonList(realResource), rate, capacity, requested + "");
        double needSleep = Double.parseDouble(ret);
        return (long) (needSleep * 1000);
    }
}
