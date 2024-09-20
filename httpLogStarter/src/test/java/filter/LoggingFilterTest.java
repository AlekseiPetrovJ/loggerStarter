package filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import ru.petrov.loggerstarter.filter.LoggingFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LoggingFilterTest {

    @InjectMocks
    private LoggingFilter loggingFilter;
    private List<LogEvent> logEvents;

    @Mock
    private FilterChain filterChain;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    private Appender appender;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        loggingFilter.setLogUrl("/log-url");
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        logEvents = new ArrayList<>();

        appender = new AbstractAppender("TestAppender", null, PatternLayout.createDefaultLayout(), true) {
            @Override
            public void append(LogEvent event) {
                logEvents.add(event);
            }
        };
        appender.start();

        Configurator.setRootLevel(org.apache.logging.log4j.Level.INFO);
        Logger logger = LogManager.getLogger(LoggingFilter.class);
        ((org.apache.logging.log4j.core.Logger) logger).addAppender(appender);
    }

    @AfterEach
    public void tearDown() {
        if (appender != null) {
            appender.stop();
            Logger logger = LogManager.getLogger(LoggingFilter.class);
            ((org.apache.logging.log4j.core.Logger) logger).removeAppender(appender);
        }
    }

    @Test
    @DisplayName("ѕроверка, что логи пишутс€ если URL совпадает с настроенным LogUrl")
    public void testDoFilterInternal_LogsRequestAndResponse() throws IOException, ServletException {
        request.setRequestURI("/log-url");
        request.setMethod("POST");
        request.setContent("Request body".getBytes(StandardCharsets.UTF_8));
        request.addHeader("Custom-Header", "HeaderValue");

        doAnswer(invocation -> {
            response.setStatus(200);
            response.getWriter().write("Response body");
            return null;
        }).when(filterChain).doFilter(any(), any());

        loggingFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());

        assertEquals(2, logEvents.size(), "There should be two log events (request and response)");

        assertTrue(logEvents.get(0).getMessage().getFormattedMessage().contains("Incoming request"));
        assertTrue(logEvents.get(1).getMessage().getFormattedMessage().contains("Status"));
    }

    @Test
    @DisplayName("ѕроверка, что логи не пишутс€ если url не совпадает с настроенным на сбор")
    public void testDoFilterInternal_SkipsLoggingForDifferentUrl() throws IOException, ServletException {
        request.setRequestURI("/other-url");

        loggingFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
        assertTrue(logEvents.isEmpty(), "No logs should be captured for a different URL");
    }
}