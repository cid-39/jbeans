/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.controller;

import com.njt.jbeans.service.ProizvodService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 *
 * @author cid
 */
@RestController
public class ProizvodController {

    private final ProizvodService proizvodService;

    public ProizvodController(ProizvodService proizvodService) {
        this.proizvodService = proizvodService;
    }

    @GetMapping("/proizvod/getall")
    public List<Object> getAllPrzenja() {
        return proizvodService.getAllPrzenja();
    }

    @GetMapping("/proizvod/get")
    public Object getPrzenjeById(@RequestParam Long id) {
        return proizvodService.getPrzenjeById(id);
    }

    @PostMapping("/proizvod/update")
    public Object updateStatus(@RequestParam Long id, @RequestParam String status) {
        return proizvodService.updateStatusPrzenja(id, status);
    }
    
    @GetMapping("/dnevni_spisak")
    public List<Object> getDnevniSpisak() {
        return proizvodService.generisiDnevniSpisakPrzenja();
    }
}