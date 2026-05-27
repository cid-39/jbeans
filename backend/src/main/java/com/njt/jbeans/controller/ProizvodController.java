/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.controller;

import com.njt.jbeans.dto.DnevnoPrzenjeDTO;
import com.njt.jbeans.model.Proizvod;
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
    public List<Proizvod> getAllProizvod() {
        return proizvodService.getAllProizvod();
    }

    @GetMapping("/proizvod/get")
    public Proizvod getPrzenjeById(@RequestParam int id) {
        return proizvodService.getProizvodById(id);
    }

    @PostMapping("/proizvod/update")
    public Proizvod updateStatus(@RequestParam int id, @RequestParam String status) {
        return proizvodService.updateStatusProizvod(id, status);
    }
    
    @GetMapping("/dnevni_spisak")
    public List<DnevnoPrzenjeDTO> getDnevniSpisak() {
        return proizvodService.generisiDnevniSpisakPrzenja();
    }
}