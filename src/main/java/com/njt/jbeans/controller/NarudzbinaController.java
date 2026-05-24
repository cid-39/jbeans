/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.controller;

import com.njt.jbeans.service.NarudzbinaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public List<Object> getAll() {
        return narudzbinaService.getAllNarudzbine();
    }

    @GetMapping("/moja")
    public List<Object> getMoja(@RequestParam Long korisnikId) {
        // Privremeno prosleđujemo korisnikId dok ne bude bezbednosti/autentifikacije
        return narudzbinaService.getMojeNarudzbine(korisnikId);
    }

    @GetMapping("/get")
    public Object getById(@RequestParam Long id) {
        return narudzbinaService.getNarudzbinaById(id);
    }

    @PostMapping("/create")
    public Object create(@RequestBody Object narudzbina) {
        return narudzbinaService.createNarudzbina(narudzbina);
    }

    @PostMapping("/update")
    public Object update(@RequestParam Long id, @RequestBody Object narudzbina) {
        return narudzbinaService.updateNarudzbina(id, narudzbina);
    }
}