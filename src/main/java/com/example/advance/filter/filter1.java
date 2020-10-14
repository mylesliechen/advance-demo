package com.example.advance.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(FilterRegistrationBean.REQUEST_WRAPPER_FILTER_MAX_ORDER - 105)
@WebFilter(filterName = "filter1", urlPatterns = "/*")
public class filter1 extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("1 filter");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
