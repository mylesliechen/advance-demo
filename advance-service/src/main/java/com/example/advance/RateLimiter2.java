package com.example.advance;

public interface RateLimiter2 {
    long acquire(String resourceType, String resource, long requested);

    long acquire(String resourceType, String resource, long requested, String rate, String capacity);

}
