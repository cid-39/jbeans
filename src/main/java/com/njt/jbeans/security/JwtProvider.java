/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author cid
 */
@Component
public class JwtProvider {
    
    @Value("${app.jwt.secret}")
    private String SECRET_KEY;
    
    @Value("${app.jwt.expiration}")
    private long EXPIRATION_TIME;

    // generisanje tokena
    public String generateToken(String email, String uloga) {
        return JWT.create()
                .withSubject(email) // Glavni identifikator korisnika
                .withClaim("uloga", uloga) // Dodatni podatak koji pakujemo u token
                .withIssuedAt(new Date()) // Vreme kreiranja
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Vreme isticanja
                .sign(Algorithm.HMAC256(SECRET_KEY)); // Potpisivanje kljucem
    }

    // validacija i citanje tokena
    public DecodedJWT validateToken(String token) {
        try {
            Algorithm algoritam = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifikator = JWT.require(algoritam).build();
            // Ako token nije ispravan ili je istekao, ovde ce baciti izuzetak
            
            return verifikator.verify(token); 
        } catch (Exception e) {
            // nije validan znaci jedno null za sada
            return null;
        }
    }

    public String getEmail(DecodedJWT decodedJwt) {
        return decodedJwt.getSubject();
    }
    
    public String getRole(DecodedJWT decodedJwt) {
        return decodedJwt.getClaim("uloga").asString();
    }
}