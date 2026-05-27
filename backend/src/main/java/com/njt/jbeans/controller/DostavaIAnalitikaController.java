/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.controller;

import com.njt.jbeans.model.Dostavljanje;
import com.njt.jbeans.model.Vozilo;
import com.njt.jbeans.service.DostavaIAnalitikaService;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author cid
 */
@RestController
public class DostavaIAnalitikaController {

    private final DostavaIAnalitikaService dostavaIAnalitikaService;

    public DostavaIAnalitikaController(DostavaIAnalitikaService dostavaIAnalitikaService) {
        this.dostavaIAnalitikaService = dostavaIAnalitikaService;
    }

    @PostMapping("/dostava/update")
    public Dostavljanje updateDostava(@RequestParam int id, @RequestParam String ishod) {
        return dostavaIAnalitikaService.updateIshodDostave(id, ishod);
    }
    
    @PostMapping("/dostava/vozilo")
    public Dostavljanje updateDostavaVozilo(@RequestParam int id, @RequestBody Vozilo vozilo) {
        return dostavaIAnalitikaService.updateDostavaVozilo(id, vozilo);
    }

    @GetMapping("/analitika")
    public Object getAnalitika() {
        return dostavaIAnalitikaService.getGlobalnaAnalitika();
    }
}
