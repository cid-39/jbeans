/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.controller;

import com.njt.jbeans.model.Narudzbina;
import com.njt.jbeans.service.NarudzbinaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.core.Authentication;

/**
 *
 * @author cid
 */
@RestController
@RequestMapping("/narudzbina")
public class NarudzbinaController {

    private final NarudzbinaService narudzbinaService;

    public NarudzbinaController(NarudzbinaService narudzbinaService) {
        this.narudzbinaService = narudzbinaService;
    }

    @GetMapping("/getall")
    public List<Narudzbina> getAll() {
        return narudzbinaService.getAllNarudzbine();
    }

    @GetMapping("/moja")
    public List<Narudzbina> getMoja(Authentication authentication) {
        String email = authentication.getName();
        return narudzbinaService.getMojeNarudzbine(email);
    }

    @GetMapping("/get")
    public Narudzbina getById(@RequestParam int id) {
        return narudzbinaService.getNarudzbinaById(id);
    }

    @PostMapping("/create")
    public Narudzbina create(@RequestBody Narudzbina narudzbina) {
        return narudzbinaService.createNarudzbina(narudzbina);
    }

    @PostMapping("/update")
    public Narudzbina update(@RequestParam int id, @RequestBody Narudzbina narudzbina) {
        return narudzbinaService.updateNarudzbina(id, narudzbina);
    }
}