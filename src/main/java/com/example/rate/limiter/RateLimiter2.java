package com.example.rate.limiter;

public interface RateLimiter2 {
    long acquire(String resourceType, String resource, long requested);
}
