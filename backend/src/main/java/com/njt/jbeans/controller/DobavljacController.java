/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.controller;

import com.njt.jbeans.model.Dobavljac;
import com.njt.jbeans.service.DobavljacService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 *
 * @author cid
 */
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/dobavljac")
public class DobavljacController {

    private final DobavljacService dobavljacService;

    public DobavljacController(DobavljacService dobavljacService) {
        this.dobavljacService = dobavljacService;
    }

    @GetMapping("/getall")
    public List<Dobavljac> getAll() {
        return dobavljacService.getAllDobavljaci();
    }

    @GetMapping("/get")
    public Dobavljac getById(@RequestParam String pib) {
        return dobavljacService.getDobavljacById(pib);
    }

    @PostMapping("/create")
    public Dobavljac create(@Valid @RequestBody Dobavljac dobavljac) {
        return dobavljacService.createDobavljac(dobavljac);
    }

    @PostMapping("/update")
    public Dobavljac update(@RequestParam String pib, @RequestBody Dobavljac dobavljac) {
        if (pib.equals(dobavljac.getPib()))
            return dobavljacService.updateDobavljac(pib, dobavljac);
        else return null;
    }

    @PostMapping("/remove")
    public boolean remove(@RequestParam String pib) {
        return dobavljacService.removeDobavljac(pib);
    }
}