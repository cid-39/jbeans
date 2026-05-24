/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.service;

import com.njt.jbeans.security.JwtProvider;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 *
 * @author cid
 */
@Service
public class KorisnikService {
    private final JwtProvider jwtProvider;

    public KorisnikService(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    public String registrujKorisnika(Object korisnikData) {
        return "Korisnik uspešno registrovan (MOCK)";
    }
    
    // bare minimum rad, treba popraviti kad bude radio repo i da se naprave dto...
    public String login(Object loginRequest) {
        // Privremeno kastujemo u Map da bismo izvukli podatke bez dto klasa
        Map<String, String> podaci = (Map<String, String>) loginRequest;
        String email = podaci.get("email");
        String lozinka = podaci.get("password");

        // Privremena provera
        if ("cid@example.com".equals(email) && "prolaz123".equals(lozinka)) {
            
            String praviToken = jwtProvider.generateToken(email, "ADMIN");
            
            return "Uspešan login. Token: " + praviToken;
        } else {
            // Ako podaci nisu tačni, bacamo grešku
            throw new RuntimeException("Pogrešan email ili lozinka!");
        }
    }
}
