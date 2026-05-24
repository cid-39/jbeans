/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.controller;

import com.njt.jbeans.service.KorisnikService;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author cid
 */
@RestController
@RequestMapping
public class KorisnikController {

    private final KorisnikService korisnikService;

    public KorisnikController(KorisnikService korisnikService) {
        this.korisnikService = korisnikService;
    }

    @PostMapping("/register")
    public String register(@RequestBody Object korisnikData) {
        return korisnikService.registrujKorisnika(korisnikData);
    }

    @PostMapping("/login")
    public String login(@RequestBody Object loginRequest) {
        return korisnikService.login(loginRequest);
    }
}
