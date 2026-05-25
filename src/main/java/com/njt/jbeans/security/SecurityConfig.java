/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author cid
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Gasimo CSRF jer ne koristimo cookies nego JWT
            .csrf(csrf -> csrf.disable())
            
            // 2. Prebacujemo aplikaciju u STATELESS mod (nema cuvanja sesija na serveru)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // 3. Konfiguracija autorizacije ruta
            .authorizeHttpRequests(auth -> auth
                // Rute za registraciju i login su potpuno javne
                .requestMatchers("/register", "/login").permitAll()
                
                // Primer zastite po ulogama: Samo ADMIN može da vidi analitiku
                .requestMatchers("/analitika").hasRole("ADMIN")
                
                // Sve ostale rute (dobavljaci, zrna, narudzbine...) zahtevaju samo da je korisnik ulogovan
                .anyRequest().authenticated()
            )
            
            // 4. Ubacujemo nas JwtFilter u lanac provere pre nego sto Spring Security odradi svoju osnovnu proveru
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    @Bean
    public org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }
}