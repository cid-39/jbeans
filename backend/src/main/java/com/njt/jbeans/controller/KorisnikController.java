/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.controller;

import com.njt.jbeans.model.Korisnik;
import com.njt.jbeans.service.KorisnikService;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author cid
 */
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping
public class KorisnikController {

    private final KorisnikService korisnikService;

    public KorisnikController(KorisnikService korisnikService) {
        this.korisnikService = korisnikService;
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Korisnik korisnik) {
        try {
            korisnikService.registrujKorisnika(korisnik);
            return Map.of(
                    "status", "success",
                    "message", "Korisnik uspešno registrovan"
            );
        } catch (Exception e) {
            return Map.of(
                    "status", "failed",
                    "message", e.getMessage()
            );
        }
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Object loginRequest) {
        try {
            return korisnikService.login(loginRequest);
        } catch (Exception e) {
            return Map.of(
                    "message", e.getMessage()
            );
        }
    }
}
