package com.reuniao.backend.security;


import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final String SECRET = "minha_chave_super_secreta_para_jwt_2026_backend_seguro";

    private Key getKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String gerarToken(String email){

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getKey())
                .compact();
    }

    public String getEmail(String token){

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}