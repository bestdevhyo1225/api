package com.hyoseok.dynamicdatasource.web.http.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ServletWrappingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper wrappingRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappingRespone = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(wrappingRequest, wrappingRespone);

        // 실제 Response Body에 값을 넣어야 한다. 이렇게 해주지 않으면, 클라이언트가 아무 응답도 받지 못하게 된다.
        wrappingRespone.copyBodyToResponse();
    }
}
