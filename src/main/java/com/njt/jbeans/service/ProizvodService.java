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
public class ProizvodService {
    public List<Object> getAllPrzenja() {
        return new ArrayList<>();
    }

    public Object getPrzenjeById(Long id) {
        return "Detalji o prženju ID: " + id;
    }

    public Object updateStatusPrzenja(Long id, String noviStatus) {
        return "Status prženja " + id + " promenjen u: " + noviStatus;
    }

    public List<Object> generisiDnevniSpisakPrzenja() {
        // Specijalna logika za dnevni plan prženja
        return new ArrayList<>();
    }
}
