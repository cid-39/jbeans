/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.service;

import com.njt.jbeans.model.Proizvod;
import com.njt.jbeans.repository.ProizvodRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 *
 * @author cid
 */
@Service
public class ProizvodService {
    private final ProizvodRepository proizvodRepository;

    public ProizvodService(ProizvodRepository proizvodRepository) {
        this.proizvodRepository = proizvodRepository;
    }

    public List<Proizvod> getAllProizvod() {
        return proizvodRepository.findAll();
    }

    public Proizvod getProizvodById(Integer id) {
        return proizvodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proizvod sa ID-jem " + id + " ne postoji!"));
    }

    public Proizvod updateStatusProizvod(Integer id, String noviStatus) {
        Proizvod proizvod = proizvodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proizvod sa ID-jem " + id + " ne postoji!"));
        proizvod.setOpis(noviStatus);
        return proizvodRepository.save(proizvod);
    }
    
    public List<Proizvod> generisiDnevniSpisakPrzenja() {
        // tek treba uraditi ovo....
        return proizvodRepository.findAll();
    }
    
}
