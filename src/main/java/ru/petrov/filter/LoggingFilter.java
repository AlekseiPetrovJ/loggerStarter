package ru.petrov.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class LoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        long startTime = System.currentTimeMillis();

        log.info("Incoming request: {} {}" +
                        "\nHeaders: {}" +
                        "\nRemote Addr: {}",
                request.getMethod(),
                request.getRequestURI(),
                request.getHeaderNames(),
                request.getRemoteAddr());

        filterChain.doFilter(request, response);

        long duration = System.currentTimeMillis() - startTime;

        log.info("Outgoing response: {}" +
                        "\nStatus: {}" +
                        "\nDuration: {} ms",
                request.getRequestURI(),
                response.getStatus(),
                duration);
    }
}