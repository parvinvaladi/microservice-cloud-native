package com.programming.orderservice.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


@Component
public class RateLimitingFilter implements Filter {
    private final int MAX_REQUEST = 2;
    private final long TIME = TimeUnit.HOURS.toMillis(1);
    private ConcurrentHashMap<String,UserRequestInfo> requestCounts = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();

        if (!requestURI.startsWith("/practice")) {
            filterChain.doFilter(request, response);
            return;
        }

        String clientIp = request.getRemoteAddr();
        long currentTime = System.currentTimeMillis();
        UserRequestInfo userRequestInfo = requestCounts.get(clientIp);
        if (userRequestInfo == null){
            userRequestInfo = new UserRequestInfo(1,currentTime);
            requestCounts.put(clientIp,userRequestInfo);
        } else {
            if (currentTime - userRequestInfo.startTime > TIME){
                userRequestInfo.countRequest = 1;
                userRequestInfo.startTime = currentTime;
            }else {
                userRequestInfo.countRequest++;
            }

            if (userRequestInfo.countRequest > MAX_REQUEST){
                response.setStatus(HttpStatus.SC_TOO_MANY_REQUESTS);
                response.getWriter().write("Too many requests. Please try again later.");
                return;
            }
        }
        filterChain.doFilter(request,response);
    }


    private static class UserRequestInfo {
        int countRequest;
        long startTime;

        public UserRequestInfo(int countRequest, long startTime) {
            this.countRequest = countRequest;
            this.startTime = startTime;
        }
    }
}
