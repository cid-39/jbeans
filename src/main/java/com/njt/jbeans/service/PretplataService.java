/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.service;

import com.njt.jbeans.model.Klijent;
import com.njt.jbeans.model.Korisnik;
import com.njt.jbeans.model.Pretplata;
import com.njt.jbeans.repository.KorisnikRepository;
import com.njt.jbeans.repository.PretplataRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 *
 * @author cid
 */
@Service
public class PretplataService {
    private final PretplataRepository pretplataRepository;
    private final KorisnikRepository korisnikRepository;

    public PretplataService(PretplataRepository pretplataRepository, KorisnikRepository korisnikRepository) {
        this.pretplataRepository = pretplataRepository;
        this.korisnikRepository = korisnikRepository;
    }

    public List<Pretplata> getAllPretplata() {
        return pretplataRepository.findAll();
    }

    public List<Pretplata> getMojePretplate(String email) {
        Korisnik korisnik = korisnikRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Korisnik sa ovim email-om ne postoji!"));

        Klijent klijent = new Klijent();
        klijent.setId(korisnik.getId()); 

        return pretplataRepository.findByNarudzbinaKlijent(klijent);
    }

    public Pretplata createPretplata(Pretplata pretplata) {
        pretplata.setAktivna(true); 
        return pretplataRepository.save(pretplata);
    }

    public Pretplata updatePretplata(Integer id, Pretplata pretplata) {
        if (pretplataRepository.existsById(id)) {
            pretplata.setId(id);
            return pretplataRepository.save(pretplata);
        }
        return null;
    }

    public Pretplata disablePretplata(Integer id) {
        Pretplata pretplata = pretplataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pretplata sa ID-jem " + id + " ne postoji!"));
        
        pretplata.setAktivna(false); 
        return pretplataRepository.save(pretplata);
    }
}
