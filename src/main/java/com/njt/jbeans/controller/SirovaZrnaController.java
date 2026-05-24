/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.controller;

import com.njt.jbeans.service.SirovaZrnaService;
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
    public List<Object> getAll() {
        return sirovaZrnaService.getAllZrna();
    }

    @GetMapping("/get")
    public Object getById(@RequestParam Long id) {
        return sirovaZrnaService.getZrnaById(id);
    }

    @PostMapping("/create")
    public Object create(@RequestBody Object zrna) {
        return sirovaZrnaService.createZrna(zrna);
    }

    @PostMapping("/update")
    public Object update(@RequestParam Long id, @RequestBody Object zrna) {
        return sirovaZrnaService.updateZrna(id, zrna);
    }

    @PostMapping("/remove")
    public boolean remove(@RequestParam Long id) {
        return sirovaZrnaService.removeZrna(id);
    }
}