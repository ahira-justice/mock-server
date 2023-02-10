package com.ahirajustice.mockserver.common.middleware;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@Component
@Slf4j
public class RequestResponseLoggingFilter extends GenericFilterBean{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Instant startTime = Instant.now();

        logRequest(request);
        chain.doFilter(request, response);

        double processTime = Duration.between(startTime, Instant.now()).getNano() / 1000000.0;
        logResponse(request, response, processTime);
    }

    private void logRequest(HttpServletRequest request) {
        String msg = String.format("Running request '%s > %s'", request.getMethod(), request.getRequestURI());
        log.info(msg);
    }

    private void logResponse(HttpServletRequest request, HttpServletResponse response, double processTime) {
        String msg = "";

        msg = String.format("Finished running request '%s > %s' in %f ms", request.getMethod(), request.getRequestURI(), processTime);
        log.info(msg);

        msg = String.format("Response Status Code: %d", response.getStatus());
        log.info(msg);
}
    
}
