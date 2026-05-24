/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cid
 */
@Service
public class DobavljacService {
    public List<Object> getAllDobavljaci() {
        return new ArrayList<>(); // Vraća praznu listu za sada
    }

    public Object getDobavljacById(Long id) {
        return "Detalji o dobavljaču sa ID: " + id;
    }

    public Object createDobavljac(Object dobavljac) {
        return "Dobavljač kreiran";
    }

    public Object updateDobavljac(Long id, Object dobavljac) {
        return "Dobavljač sa ID: " + id + " izmenjen";
    }

    public boolean removeDobavljac(Long id) {
        return true; // Uspešno obrisano
    }
}
