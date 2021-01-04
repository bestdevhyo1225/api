package com.hyoseok.dynamicdatasource.web.http.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String strStartTime = "StartTime";

    public LoggingInterceptor() {
        this.objectMapper.registerModule(new AfterburnerModule());
    }

    // preHandle() -> Controller 실행 전
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute(strStartTime, System.currentTimeMillis());
        return true;
    }

    // afterCompletion() -> View Rendering 이후
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
        final ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;

        String elapsed = (System.currentTimeMillis() - (long) request.getAttribute(strStartTime)) + "ms";

        Map<String, String> headerMap = new HashMap<>();

        request.getHeaderNames().asIterator().forEachRemaining(name -> headerMap.put(name, request.getHeader(name)));

        Map<String, Object> requestMap = new HashMap<>() {
            {
                put("protocol", request.getProtocol());
                put("method", request.getMethod());
                put("path", request.getRequestURI());
                put("queryString", request.getQueryString());
                put("body", objectMapper.readTree(cachingRequest.getContentAsByteArray()));
            }
        };

        Map<String, String> responseMap = new HashMap<>() {
            {
                put("status", String.valueOf(response.getStatus()));
                put("elapsed", elapsed);
            }
        };

        Map<String, Object> logMap = new HashMap<>() {
            {
                put("header", headerMap);
                put("request", requestMap);
                put("response", responseMap);
            }
        };

        boolean isError = 400 <= response.getStatus() && response.getStatus() <= 504;

        if (isError) {
            String errorMessage = objectMapper.readTree(cachingResponse.getContentAsByteArray())
                    .get("message").toString()
                    .replace("\"", "");

            logMap.put("error", new HashMap<>() { { put("message", errorMessage); } });

            log.error(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(logMap), "{}");

        } else log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(logMap), "{}");
    }
}
