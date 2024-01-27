package org.example.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Component
public class JwtTokenService{
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String username, String password){
        Map<String, Object> claims = getClaims(username,password);


        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+18000000))
                .signWith(key)
                .compact();
    }

    public Map<String,Object> getClaims(String username, String password){
        Map<String, Object> claims = new HashMap<>();
        claims.put("username",username);
        claims.put("password",password);
        return claims;
    }

    public Boolean isValidate(String token){

        try {
            System.out.println("in req " + token);
            String tokenR = token.split("\\s+")[1];
            System.out.println(tokenR);
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(tokenR).getBody();
            System.out.println(claims.get("username"));
//        String expectedUsername = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
//        System.out.println("expected "+expectedUsername);
            return true;
        }
        catch (Exception e){
            return false;
        }

    }

    public Claims extractUserNamePasswordFromJwtToken(String token){
        String tokenR = token.split("\\s+")[1];
        System.out.println(tokenR);
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(tokenR).getBody();
    }



}
