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
public class PretplataService {
    public List<Object> getAllPretplata() {
        return new ArrayList<>();
    }

    public List<Object> getMojePretplate(Long korisnikId) {
        return new ArrayList<>();
    }

    public Object createPretplata(Object pretplata) {
        return "Pretplata formirana";
    }

    public Object updatePretplata(Long id, Object pretplata) {
        return "Pretplata izmenjena";
    }

    public Object disablePretplata(Long id) {
        // Ne brišemo iz baze, samo menjamo status zbog analitike
        return "Pretplata " + id + " je deaktivirana (status: DISABLED)";
    }
    
}
