/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.service;

import com.njt.jbeans.model.Dostavljanje;
import com.njt.jbeans.repository.DostavljanjeRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author cid
 */
@Service
public class DostavaIAnalitikaService {
    private final DostavljanjeRepository dostavljanjeRepository;

    public DostavaIAnalitikaService(DostavljanjeRepository dostavljanjeRepository) {
        this.dostavljanjeRepository = dostavljanjeRepository;
    }

    public Dostavljanje updateIshodDostave(Integer id, String ishod) {
        Dostavljanje dostavljanje = dostavljanjeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dostava sa ID-jem " + id + " ne postoji!"));
        dostavljanje.setStatus(ishod);
        
        return dostavljanjeRepository.save(dostavljanje);
    }

    public Object getGlobalnaAnalitika() {
        return "mockAnalitika";
    }
}
