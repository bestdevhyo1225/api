package com.hyoseok.dynamicdatasource.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JwtUtils {

    private final SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;

    private final Key secretKeySpec;

    public JwtUtils(String secretKey) {
        secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), algorithm.getJcaName());
    }

    public String generateToken(Integer adminId, String hasRole) {
        return Jwts.builder()
                .setClaims(createClaims(adminId, hasRole))
                .setExpiration(createExpiration())
                .signWith(algorithm, secretKeySpec)
                .compact();
    }

    private Map<String, Object> createClaims(Integer adminId, String hasRole) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("adminId", adminId);
        claims.put("hasRole", hasRole);

        return claims;
    }

    private Date createExpiration() {
        long EXPIRATION_MS = 1000 * 60 * 60 * 24;
        LocalDateTime expiredAt = LocalDateTime.now().plus(EXPIRATION_MS, ChronoUnit.MILLIS);
        return Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKeySpec)
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean validateToken(String token) {
        return new Date().before(getClaims(token).getExpiration());
    }

    public Integer getMemberId(String token) {
        Integer memberId = getClaims(token).get("memberId", Integer.class);
        return memberId == null ? 0 : memberId;
    }

    public Integer getAdminId(String token) {
        Integer adminId = getClaims(token).get("adminId", Integer.class);
        return adminId == null ? 0 : adminId;
    }

    public String getHasRole(String token) {
        return Optional.ofNullable(getClaims(token).get("hasRole", String.class)).orElse("USER");
    }
}
