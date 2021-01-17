package com.hyoseok.dynamicdatasource.web.http.filter;

import com.hyoseok.dynamicdatasource.config.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (isBearerToken(authHeader)) {
            setSecurityContextHolder(request, authHeader.substring(7));
        }

        filterChain.doFilter(request, response);
    }

    private Boolean isBearerToken(String authHeader) {
        return StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ");
    }

    private void setSecurityContextHolder(HttpServletRequest request, String token) {
        Boolean isValidToken = jwtUtils.validateToken(token);

        if (isValidToken) {
            Integer memberId = jwtUtils.getMemberId(token);
            Integer principal = memberId > 0 ? memberId : jwtUtils.getAdminId(token);

            List<GrantedAuthority> authorities =
                    Collections.singletonList(new SimpleGrantedAuthority(jwtUtils.getHasRole(token)));

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(principal, token, authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

}
