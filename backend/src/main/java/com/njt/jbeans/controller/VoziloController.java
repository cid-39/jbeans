/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.controller;

import com.njt.jbeans.model.Narudzbina;
import com.njt.jbeans.model.Vozilo;
import com.njt.jbeans.service.VoziloService;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author cid
 */
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/vozilo")
public class VoziloController {
    private final VoziloService voziloService;

    public VoziloController(VoziloService voziloService) {
        this.voziloService = voziloService;
    }
    
    @GetMapping("/getall")
    public List<Vozilo> getAll() {
        return voziloService.getAllVozilo();
    }

}
