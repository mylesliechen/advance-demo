package com.cx.reactor.test;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class reactorTest {
    @Test
    public void createAFlux_just() {
        Flux<String> fruitFlux = Flux
                .just("Apple", "Orange", "Grape", "Banana", "Strawberry");

        fruitFlux.subscribe(f -> System.out.println("Here's some fruit: " + f));


        Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1)).take(5);

        intervalFlux.subscribe(System.out::println);

    }

}
