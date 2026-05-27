/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.controller;

import com.njt.jbeans.dto.NarudzbinaRequestDTO;
import com.njt.jbeans.model.Pretplata;
import com.njt.jbeans.service.PretplataService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.core.Authentication;

/**
 *
 * @author cid
 */
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/pretplata")
public class PretplataController {

    private final PretplataService pretplataService;

    public PretplataController(PretplataService pretplataService) {
        this.pretplataService = pretplataService;
    }

    @GetMapping("/getall")
    public List<Pretplata> getAll() {
        return pretplataService.getAllPretplata();
    }

    @GetMapping("/moja")
    public List<Pretplata> getMoja(Authentication authentication) {
        String email = authentication.getName();
        return pretplataService.getMojePretplate(email);
    }

    @PostMapping("/create")
    public Pretplata create(@RequestBody NarudzbinaRequestDTO dto, Authentication authentication) {
        String email = authentication.getName();
        return pretplataService.createPretplata(dto, email);
    }

    @PostMapping("/update")
    public Pretplata update(@RequestParam int id, @RequestBody Pretplata pretplata) {
        return pretplataService.updatePretplata(id, pretplata);
    }

    @PostMapping("/delete")
    public Pretplata delete(@RequestParam int id) {
        return pretplataService.disablePretplata(id);
    }
}