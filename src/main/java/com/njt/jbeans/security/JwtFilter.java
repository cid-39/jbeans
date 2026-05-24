/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author cid
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    
    public JwtFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        // 1. Izvlacimo "Authorization" header iz HTTP zahteva
        String authHeader = request.getHeader("Authorization");

        // 2. Proveravamo da li header postoji i da li pocinje sa "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Preskacemo prvih 7 karaktera ("Bearer ") da uzmemo cist token

            // 3. Validacija tokena
            DecodedJWT decodedJwt = jwtProvider.validateToken(token);

            if (decodedJwt != null) {
                String email = jwtProvider.getEmail(decodedJwt);
                String role = jwtProvider.getRole(decodedJwt);

                // Spring Security zahteva uloge u formatu "ROLE_NAZIV" (npr. ROLE_ADMIN, ROLE_KLIJENT)
                List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())
                );

                // 4. Kreiramo auth objekat
                UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(email, null, authorities);

                // 5. Ubacujemo korisnika u trenutni "bezbednosni kontekst" aplikacije
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // 6. Pustamo zahtev da nastavi svoj put
        filterChain.doFilter(request, response);
    }
}