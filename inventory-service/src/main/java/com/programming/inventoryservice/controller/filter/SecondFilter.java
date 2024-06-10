package com.programming.inventoryservice.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@WebFilter(urlPatterns = "/*")
public class SecondFilter extends OncePerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(FirstFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        LOG.info(String.valueOf(request));
        LOG.info("secondFilter");
        filterChain.doFilter(request, response);
        LOG.info("secondFilter ended ");
    }
}
