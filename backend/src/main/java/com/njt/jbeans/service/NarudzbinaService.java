/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.service;

import com.njt.jbeans.dto.NarudzbinaRequestDTO;
import com.njt.jbeans.model.Dostavljanje;
import com.njt.jbeans.model.Klijent;
import com.njt.jbeans.model.Korisnik;
import com.njt.jbeans.model.Narudzbina;
import com.njt.jbeans.model.Proizvod;
import com.njt.jbeans.model.SirovaZrna;
import com.njt.jbeans.model.StavkaNarudzbine;
import com.njt.jbeans.repository.KorisnikRepository;
import com.njt.jbeans.repository.NarudzbinaRepository;
import com.njt.jbeans.repository.SirovaZrnaRepository;
import jakarta.transaction.Transactional;
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
    private final SirovaZrnaRepository sirovaZrnaRepository;
    
    public NarudzbinaService(NarudzbinaRepository narudzbinaRepository, KorisnikRepository korisnikRepository, SirovaZrnaRepository sirovaZrnaRepository) {
        this.narudzbinaRepository = narudzbinaRepository;
        this.korisnikRepository = korisnikRepository;
        this.sirovaZrnaRepository = sirovaZrnaRepository;
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
    
    @Transactional
    public Narudzbina createNarudzbina(NarudzbinaRequestDTO dto, String email) {
        // 1. Pronalazimo korisnika i vezujemo ga za prazan objekat Klijenta
        Korisnik korisnik = korisnikRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Korisnik sa ovim email-om ne postoji!"));

        Klijent klijent = new Klijent();
        klijent.setId(korisnik.getId());

        // 2. Kreiramo glavnu narudžbinu
        Narudzbina narudzbina = new Narudzbina();
        narudzbina.setKlijent(klijent);
        narudzbina.setPretplata(false);

        // 3. Prolazimo kroz stavke iz DTO-a i punimo listu
        double tekucaUkupnaCena = 0.0;
        for (NarudzbinaRequestDTO.StavkaKorpeDTO stavkaDto : dto.getStavke()) {
            SirovaZrna zrno = sirovaZrnaRepository.findById(stavkaDto.getSirovaZrnaId())
                    .orElseThrow(() -> new RuntimeException("Sirovo zrno sa ID " + stavkaDto.getSirovaZrnaId() + " ne postoji!"));
            
//            IF postoji 
                    
            Proizvod proizvod = new Proizvod();
            proizvod.setKolicinaPrzena(stavkaDto.getKolicina());
            proizvod.setTipPrzenja(stavkaDto.getTipPrzenja());
            proizvod.setZrna(zrno);
            proizvod.setDatumPrzenja(dto.getDatumDostave());
            proizvod.setOpis("CEKA PRZENJE");
            // save prozivod?!
            
            
            
            // Pravimo novu stavku i vezujemo podatke PROIZVOOOD
            StavkaNarudzbine stavka = new StavkaNarudzbine();
            stavka.setProizvod(proizvod);
            stavka.setKolicina(stavkaDto.getKolicina());

            // KLJUČNO ZA CASCADE: Dvosmerno povezivanje stavke i narudžbine
            stavka.setNarudzbina(narudzbina); 
            narudzbina.getStavke().add(stavka);

            // Računamo cenu na osnovu cene zrna iz baze
            stavka.setCena(stavkaDto.getKolicina() * zrno.getCenaPoMeri());
            tekucaUkupnaCena += stavkaDto.getKolicina() * zrno.getCenaPoMeri();
        }

        // 4. Upisujemo konačnu sračunatu cenu u narudžbinu
        narudzbina.setUkupnaCena(tekucaUkupnaCena);
        
        Dostavljanje dostavljanje = new Dostavljanje(null, null, dto.getDatumDostave(), "CEKA");
        
        narudzbina.setDostavljanje(dostavljanje);
        
        // 5. Čuvamo samo narudžbinu – Hibernate zbog CascadeType.ALL sam čuva i sve stavke iz liste!
        return narudzbinaRepository.save(narudzbina);
    }
    
    
    
    // sto je naruzivao narucivao je ovo ce da NEMA
    public Narudzbina updateNarudzbina(Integer id, Narudzbina narudzbina) {
        if (narudzbinaRepository.existsById(id)) {
            narudzbina.setId(id); 
            return narudzbinaRepository.save(narudzbina);
        }
        return null;
    }
}


// pravljenje objekta Narudzbina sa listom stavki

        // POCETAK TRANSAKCIJE
        
        // da li vec postoji dostava i proizvod za taj dan?
        // da> promeni kolicinu u proizvodu
        // ne > ubaci nove u bazu
        
        // ubacivanje narudzbine (uz stavke)
        
        // KRAJ TRANSAKCIJE