/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.service;

import com.njt.jbeans.model.Klijent;
import com.njt.jbeans.model.Korisnik;
import com.njt.jbeans.model.Narudzbina;
import com.njt.jbeans.repository.KorisnikRepository;
import com.njt.jbeans.repository.NarudzbinaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 *
 * @author cid
 */
@Service
public class NarudzbinaService {
    private final KorisnikRepository korisnikRepository;
    private final NarudzbinaRepository narudzbinaRepository;

    public NarudzbinaService(NarudzbinaRepository narudzbinaRepository, KorisnikRepository korisnikRepository) {
        this.narudzbinaRepository = narudzbinaRepository;
        this.korisnikRepository = korisnikRepository;
    }
    
    public List<Narudzbina> getAllNarudzbine() {
        return narudzbinaRepository.findAll();
    }

    public List<Narudzbina> getMojeNarudzbine(String email) {
        Korisnik korisnik = korisnikRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Korisnik sa ovim email-om ne postoji!"));

        Klijent klijent = new com.njt.jbeans.model.Klijent();
        klijent.setId(korisnik.getId()); 

        return narudzbinaRepository.findByKlijent(klijent);
    }

    public Narudzbina getNarudzbinaById(Integer id) {
        return narudzbinaRepository.findById(id).orElse(null);
    }

    public Narudzbina createNarudzbina(Narudzbina narudzbina) {
        return narudzbinaRepository.save(narudzbina);
    }
    
    public Narudzbina updateNarudzbina(Integer id, Narudzbina narudzbina) {
        if (narudzbinaRepository.existsById(id)) {
            narudzbina.setId(id); 
            return narudzbinaRepository.save(narudzbina);
        }
        return null;
    }
}
