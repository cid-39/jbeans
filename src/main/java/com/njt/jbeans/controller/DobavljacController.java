/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.controller;

import com.njt.jbeans.service.DobavljacService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 *
 * @author cid
 */
@RestController
@RequestMapping("/dobavljac")
public class DobavljacController {

    private final DobavljacService dobavljacService;

    public DobavljacController(DobavljacService dobavljacService) {
        this.dobavljacService = dobavljacService;
    }

    @GetMapping("/getall")
    public List<Object> getAll() {
        return dobavljacService.getAllDobavljaci();
    }

    @GetMapping("/get")
    public Object getById(@RequestParam Long id) {
        //primer: /dobavljac/get?id=3
        return dobavljacService.getDobavljacById(id);
    }

    @PostMapping("/create")
    public Object create(@RequestBody Object dobavljac) {
        return dobavljacService.createDobavljac(dobavljac);
    }

    @PostMapping("/update")
    public Object update(@RequestParam Long id, @RequestBody Object dobavljac) {
        return dobavljacService.updateDobavljac(id, dobavljac);
    }

    @PostMapping("/remove")
    public boolean remove(@RequestParam Long id) {
        return dobavljacService.removeDobavljac(id);
    }
}