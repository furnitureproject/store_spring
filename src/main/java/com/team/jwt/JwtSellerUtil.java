package com.team.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtSellerUtil {
    
    private final String SECRETKEY = "uuuejsidkrjjsndfjei564sd78f#!@$";

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody();

        return claimsResolver.apply(claims);
    }

    public String generateToken(String username){
        
        long tokenValidTime = 1000 * 60 * 60 * 4;

        Map<String, Object> claims = new HashMap<>();
        String token = Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + tokenValidTime))
            .signWith(SignatureAlgorithm.HS256, SECRETKEY)
            .compact();

        return token;
    }

    public Boolean validateToken(String token, String sellerId){
        
        final String username = this.extractUsername(token);

        if(username.equals(sellerId) && !isTokenExpired(token)){
            return true;
        }
        return false;
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    
    public Boolean isTokenExpired(String token){
        return this.extractExpiration(token).before(new Date());
    }
}
