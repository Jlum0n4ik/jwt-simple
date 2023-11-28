package com.example.jwtrepeat2.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    @Value("${jwt.expiration}")
    private long expiration;
    public String createToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        String commaSeparatedAuthorities = userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        claims.put("authorities", commaSeparatedAuthorities);
        return generateToken(claims, userDetails.getUsername());
    }
    public String extractAuthorities(String token) {
        Function<Claims, String> extractAuth = claims ->  {
            return (String) claims.get("authorities");
        };
        return extractClaim(token, extractAuth);
    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    private <T> T extractClaim(String token, Function<Claims, T> extractFunc) {
        Claims claims = extractAllClaims(token);
        return extractFunc.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
    private String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDateFromNow())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
    private Date expirationDateFromNow() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }
}
