package com.programming.inventoryservice.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// to log before the request sent and after response returns
@Component
@WebFilter(urlPatterns = "/*")
public class FirstFilter extends OncePerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(FirstFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        LOG.info("FirstFilter started " + startTime);
        filterChain.doFilter(request, response);
        long endTime = System.currentTimeMillis();
        LOG.info("FirstFilter ended the status code is " + response.getStatus());
        LOG.info("time spent: " + (endTime - startTime) + " ms");
    }
}
