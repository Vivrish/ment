package com.example.authenticationservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {
    private final String algorithm = "HmacSHA256";
    private final KeyGenerator keyGenerator;

    private final SecretKey secretKey;

    private final UserDetailsService userDetailsService;

    public JwtService(UserDetailsService userDetailsService) throws NoSuchAlgorithmException {
        this.userDetailsService = userDetailsService;
        this.keyGenerator = KeyGenerator.getInstance(algorithm);
        this.secretKey = keyGenerator.generateKey();
    }


    public String generateToken (String username)  {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .addClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(secretKey)
                .compact();
    }



    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean validateHeader(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            return false;
        }
        String jwtToken = header.substring(7);
        String username = extractUsername(jwtToken);
        if (username == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            return false;
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return validateToken(jwtToken, userDetails);
    }

    public boolean validateToken(String jwtToken, UserDetails userDetails) {
        String username = extractUsername(jwtToken);
        return Objects.equals(username, userDetails.getUsername()) && !isTokenExpired(jwtToken);
    }

    public boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    private Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public UserDetails getUserDetails(String authHeader) {
        return userDetailsService.loadUserByUsername(extractUsername(authHeader.substring(7)));
    }
}
