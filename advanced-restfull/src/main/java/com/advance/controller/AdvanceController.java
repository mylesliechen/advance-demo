package com.advance.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AdvanceController {
    @Value("${advance.name}")
    private String name;

    @Value("#{advance.address}")
    private String address;

    @GetMapping("/test")
    public void test() {
        log.info("name:{}", name);
    }
}
