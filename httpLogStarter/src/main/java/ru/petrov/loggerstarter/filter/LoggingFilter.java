package ru.petrov.loggerstarter.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.UUID;

@Slf4j
public class LoggingFilter extends OncePerRequestFilter {
    @Value("${http-logger.log-url}")
    private String logUrl;

    /**
     * Логируется запросы и ответы по URL указанному в настройке application.yml http-logger: log-url:
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!logUrl.equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        UUID logUuid = UUID.randomUUID();
        long startTime = System.currentTimeMillis();
        ContentCachingRequestWrapper cRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper cResponse = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(cRequest, cResponse);
        logRequest(logUuid, cRequest);

        long duration = System.currentTimeMillis() - startTime;

        logResponse(logUuid, cResponse, duration);
        cResponse.copyBodyToResponse();
    }

    private static void logResponse(UUID logUuid, ContentCachingResponseWrapper cResponse, long duration) {
        log.info("""
                        
                        Log UUID {}\

                        Status: {}\
                                                
                        Body: {}

                        Duration: {} ms""",
                logUuid,
                cResponse.getStatus(),
                new String(cResponse.getContentAsByteArray(), StandardCharsets.UTF_8),
                duration);
    }

    private static void logRequest(UUID logUuid, ContentCachingRequestWrapper cRequest) {
        log.info("""
                        
                        Log UUID: {}\
                                                
                        Incoming request: {} {}\

                        Headers: {}\

                        Body: {}\
                        
                        Remote Addr: {}""",
                logUuid,
                cRequest.getMethod(),
                cRequest.getRequestURI(),
                getHeader(cRequest),
                new String(cRequest.getContentAsByteArray(), StandardCharsets.UTF_8),
                cRequest.getRemoteAddr());
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