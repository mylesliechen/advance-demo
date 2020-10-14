package com.example.advance.configuration;

import com.example.advance.filter.Filter3;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.DispatcherType;

@Configuration
public class filterConfig {

    @Bean
    public FilterRegistrationBean<Filter3> webMvcMetricsFilter() {
        Filter3 filter = new Filter3();
        FilterRegistrationBean<Filter3> registration = new FilterRegistrationBean<>(
                filter);
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        registration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC);
        return registration;
    }

}
