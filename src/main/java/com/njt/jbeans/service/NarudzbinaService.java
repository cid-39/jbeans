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
public class NarudzbinaService {
    public List<Object> getAllNarudzbine() {
        return new ArrayList<>();
    }

    public List<Object> getMojeNarudzbine(Long korisnikId) {
        return new ArrayList<>();
    }

    public Object getNarudzbinaById(Long id) {
        return "Detalji narudžbine " + id;
    }

    public Object createNarudzbina(Object narudzbina) {
        return "Narudžbina kreirana";
    }

    public Object updateNarudzbina(Long id, Object narudzbina) {
        return "Narudžbina " + id + " izmenjena (provera datuma prošla)";
    }
}
