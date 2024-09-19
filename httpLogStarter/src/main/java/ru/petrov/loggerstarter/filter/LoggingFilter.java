package ru.petrov.loggerstarter.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;

@Slf4j
public class LoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        log.info("""
                        
                        Incoming request: {} {}\

                        Headers: {}\

                        Body: {}\
                        
                        Remote Addr: {}""",
                request.getMethod(),
                request.getRequestURI(),
                getHeader(request),
                getBody(request),
                request.getRemoteAddr());

        filterChain.doFilter(request, response);

        long duration = System.currentTimeMillis() - startTime;

        log.info("""
                        
                        Outgoing response: {}\

                        Status: {}\

                        Duration: {} ms""",
                request.getRequestURI(),
                response.getStatus(),
                duration);
    }

    private static StringBuilder getBody(HttpServletRequest request) throws IOException {
        StringBuilder body = new StringBuilder();
        String line;

        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            body.append(line).append("\n");
        }
        return body;
    }

    private static StringBuilder getHeader(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder header = new StringBuilder();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                header.append(headerName).append(": ").append(headerValue).append("\n");
            }
        }
        return header;
    }
}