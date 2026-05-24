/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.controller;

import com.njt.jbeans.service.PretplataService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 *
 * @author cid
 */
@RestController
@RequestMapping("/pretplata")
public class PretplataController {

    private final PretplataService pretplataService;

    public PretplataController(PretplataService pretplataService) {
        this.pretplataService = pretplataService;
    }

    @GetMapping("/getall")
    public List<Object> getAll() {
        return pretplataService.getAllPretplata();
    }

    @GetMapping("/moja")
    public List<Object> getMoja(@RequestParam Long korisnikId) {
        return pretplataService.getMojePretplate(korisnikId);
    }

    @PostMapping("/create")
    public Object create(@RequestBody Object pretplata) {
        return pretplataService.createPretplata(pretplata);
    }

    @PostMapping("/update")
    public Object update(@RequestParam Long id, @RequestBody Object pretplata) {
        return pretplataService.updatePretplata(id, pretplata);
    }

    @PostMapping("/delete")
    public Object delete(@RequestParam Long id) {
        return pretplataService.disablePretplata(id);
    }
}