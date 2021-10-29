package com.team.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// jjwt 라이브러리 설치
@Service
// 토큰 발행 & 토큰의 유효성 검사, 토큰 관련된 메소드
// jwtUtil은 간단하게 token을 발행하고 인증하고 유지하는것만 있고 security와 관련된건 다 뺌
public class JwtUtil {

    // 토큰 발행용 보안키
    private final String SECRETKEY = "jsdkfjkasdfasdfasdf";
    
    // 토큰에서 정보 추출할수 있게 해주는 메소드(아래에 있는 메소드들의 return값에 있는 get을 쓸수있게 해줌)
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody();

        return claimsResolver.apply(claims);
    }

    // 토큰 생성(아이디 정보를 이용한 토큰 생성)
    public String generateToken(String username){

        // 토큰 만료 시간(지금은 4시간으로 설정)
        long tokenValidTime = 1000 * 60 * 60 * 4; // 1000(1초) * 60(1분) * 60(1시간) * 4(4시간) ==> 4시간 

        Map<String, Object> claims = new HashMap<>();
        String token = Jwts.builder()
            .setClaims(claims)
            .setSubject(username)//id값
            .setIssuedAt(new Date(System.currentTimeMillis()))//발행시간
            .setExpiration(new Date(System.currentTimeMillis() + tokenValidTime))//만료시간
            .signWith(SignatureAlgorithm.HS256, SECRETKEY)//토큰 발행 알고리즘
            .compact();

        return token;
    }

    // 토큰 유효성 확인
    // vue에서 오는 토큰에서 꺼낸 아이디 정보(string token)
    // 시큐리티 세션에 보관되어 있던 아이디 정보(string userid)
    public Boolean validateToken(String token, String userId){

        // 토큰에서 아이디 정보 추출
        final String username = this.extractUsername(token);

        // 유효성 검사
        // 프론트에서 온 토큰에서 꺼낸 아이디 정보와 세큐리티 세션에 보관되어 있던 아이디정보가 같은지 비교
        // && 만료시간이 유지되고 있는가
        if(username.equals(userId) && !isTokenExpired(token)){
            return true;
        }
        return false;
    }

    // 토큰에서 아이디 정보 가져오기
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    // 토큰에서 만료 시간 추출하기
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    // 토큰의 만료시간이 유효한지 확인
    public Boolean isTokenExpired(String token){
        // 만료시간 가져와서 현재시간보다 이전인지 확인
        return this.extractExpiration(token).before(new Date());
    }



}
