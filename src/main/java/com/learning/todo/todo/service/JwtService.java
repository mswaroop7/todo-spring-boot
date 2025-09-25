package com.learning.todo.todo.service;

import com.learning.todo.todo.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Getter
    @Value("${security.jwt.exp}")
    private long exp;

    private Key key;


    @PostConstruct
    public void constructKey() {
        byte[] encodedKey = Decoders.BASE64.decode(this.jwtSecret);
        this.key = Keys.hmacShaKeyFor(encodedKey);
    }

    private String buildToken(
            Map<String, Object> extras,
            UserDetails userDetails,
            long exp
    ) {
        return Jwts
                .builder()
                .setClaims(extras)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + exp))
                .signWith(this.key, SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(this.key)
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    private boolean isExpired(String token) {
        Claims claims = this.extractClaims(token);
        return claims.getExpiration().before(new Date());
    }

    public boolean isValid(String token, User userDetails) {
        Claims claims = this.extractClaims(token);
        String username = claims.getSubject();
        return username.equals(userDetails.getUsername()) && !this.isExpired(token);
    }

    public String generateToken(HashMap<String, Object> extras, UserDetails userDetails) {
        return this.buildToken(extras, userDetails, this.exp);
    }

    public String extractUsername(String token) {
        Claims claims = this.extractClaims(token);
        return claims.getSubject();
    }

}
