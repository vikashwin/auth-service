package com.vikash.auth_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
//    Header.Payload.Signature

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

//    SecretKey object used internally by jjwt library that convert String into SecretKey.
    private SecretKey secretKey;

    @PostConstruct  //This method is automatically executed AFTER Spring creates this bean.
    public void init(){
        secretKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

//    This method is called after successful login.
    public String generateToken(UserDetails userDetails){

        CustomUserDetails user = (CustomUserDetails) userDetails;
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put(
                "roles",
                userDetails.getAuthorities()
                        .stream()
                        .map(authority -> authority.getAuthority())
                        .toList()
        );
        return generateToken(claims, userDetails);
    }

//    Generate JWT with additional claims.
    public String generateToken(Map<String , Object> extraClaims,
                                UserDetails userDetails ){
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

//    Generic claim extractor.
    public <T> T extractClaim(String token,
            Function<Claims, T> resolver) {

        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

//    Extract username/email from JWT.
    public String extractUsername(String token){
        return extractClaim(token , Claims::getSubject);
    }

//    Extract expiration date.
    public Date extractExpiration(String token){
        return extractClaim(token , Claims::getExpiration);
    }

//    Check whether token expired.
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

//    Validate JWT.
    public boolean isTokenValid(String token, UserDetails userDetails){
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public long getExpiration() {
        return jwtExpiration;
    }


}
