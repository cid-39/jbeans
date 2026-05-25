/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.service;

import com.njt.jbeans.model.Dobavljac;
import com.njt.jbeans.model.SirovaZrna;
import com.njt.jbeans.repository.DobavljacRepository;
import com.njt.jbeans.repository.SirovaZrnaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 *
 * @author cid
 */
@Service
public class SirovaZrnaService {
    private final SirovaZrnaRepository sirovaZrnaRepository;
    private final DobavljacRepository dobavljacRepository;

    public SirovaZrnaService(SirovaZrnaRepository sirovaZrnaRepository, DobavljacRepository dobavljacRepository) {
        this.sirovaZrnaRepository = sirovaZrnaRepository;
        this.dobavljacRepository = dobavljacRepository;
    }

    public List<SirovaZrna> getAllZrna() {
        return sirovaZrnaRepository.findAll();
    }

    public SirovaZrna getZrnaById(Integer id) {
        return sirovaZrnaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sirova zrna sa ID-jem " + id + " ne postoje!"));
    }

    public SirovaZrna createZrna(SirovaZrna zrna) {
        String pib = zrna.getDobavljac().getPib();

        Dobavljac praviDobavljac = dobavljacRepository.findById(pib)
                .orElseThrow(() -> new RuntimeException("Dobavljač sa ovim PIB-om ne postoji u bazi!"));

        zrna.setDobavljac(praviDobavljac);

        return sirovaZrnaRepository.save(zrna);
    }

    public SirovaZrna updateZrna(Integer id, SirovaZrna zrna) {
        if (sirovaZrnaRepository.existsById(id)) {
            zrna.setId(id);
            return sirovaZrnaRepository.save(zrna);
        }
        throw new RuntimeException("Ažuriranje nemoguće: Zrno sa ID-jem " + id + " ne postoji!");
    }
    
    public boolean removeZrna(Integer id) {
        if (sirovaZrnaRepository.existsById(id)) {
            sirovaZrnaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}