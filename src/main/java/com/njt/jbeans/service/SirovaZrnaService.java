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
public class SirovaZrnaService {
    public List<Object> getAllZrna() {
        return new ArrayList<>();
    }

    public Object getZrnaById(Long id) {
        return "Detalji o zrnu sa ID: " + id;
    }

    public Object createZrna(Object zrna) {
        return "Novo zrno ubačeno u sistem";
    }

    public Object updateZrna(Long id, Object zrna) {
        return "Zrno sa ID: " + id + " ažurirano";
    }

    public boolean removeZrna(Long id) {
        return true;
    }
}
