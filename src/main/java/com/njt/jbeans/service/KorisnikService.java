/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.service;

import org.springframework.stereotype.Service;

/**
 *
 * @author cid
 */
@Service
public class KorisnikService {
    public String registrujKorisnika(Object korisnikData) {
        // TODO: Logika za hešovanje lozinke i čuvanje u bazu
        return "Korisnik uspešno registrovan (MOCK)";
    }

    public String login(Object loginRequest) {
        // TODO: Logika za proveru lozinke i generisanje JWT tokena/sesije
        return "Uspšan login. Token: mock-jwt-token-123";
    }
}
