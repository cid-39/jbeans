/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.controller;

import com.njt.jbeans.model.SirovaZrna;
import com.njt.jbeans.service.SirovaZrnaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 *
 * @author cid
 */
@RestController
@RequestMapping("/zrna")
public class SirovaZrnaController {

    private final SirovaZrnaService sirovaZrnaService;

    public SirovaZrnaController(SirovaZrnaService sirovaZrnaService) {
        this.sirovaZrnaService = sirovaZrnaService;
    }

    @GetMapping("/getall")
    public List<SirovaZrna> getAll() {
        return sirovaZrnaService.getAllZrna();
    }

    @GetMapping("/get")
    public SirovaZrna getById(@RequestParam int id) {
        return sirovaZrnaService.getZrnaById(id);
    }

    @PostMapping("/create")
    public SirovaZrna create(@Valid @RequestBody SirovaZrna zrna) {
        return sirovaZrnaService.createZrna(zrna);
    }

    @PostMapping("/update")
    public SirovaZrna update(@RequestParam int id, @RequestBody SirovaZrna zrna) {
        return sirovaZrnaService.updateZrna(id, zrna);
    }

    @PostMapping("/remove")
    public boolean remove(@RequestParam int id) {
        return sirovaZrnaService.removeZrna(id);
    }
}