/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.service;

import com.njt.jbeans.dto.DnevnoPrzenjeDTO;
import com.njt.jbeans.model.Proizvod;
import com.njt.jbeans.model.SirovaZrna;
import com.njt.jbeans.repository.ProizvodRepository;
import com.njt.jbeans.repository.SirovaZrnaRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 *
 * @author cid
 */
@Service
public class ProizvodService {

    private final ProizvodRepository proizvodRepository;
    private final SirovaZrnaRepository sirovaZrnaRepository;

    public ProizvodService(ProizvodRepository proizvodRepository, SirovaZrnaRepository sirovaZrnaRepository) {
        this.proizvodRepository = proizvodRepository;
        this.sirovaZrnaRepository = sirovaZrnaRepository;
    }

    public List<Proizvod> getAllProizvod() {
        return proizvodRepository.findAll();
    }

    public Proizvod getProizvodById(Integer id) {
        return proizvodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proizvod sa ID-jem " + id + " ne postoji!"));
    }

    @Transactional
    public Proizvod updateStatusProizvod(Integer id, String noviStatus) {
        // 1. Pronalazimo proizvod u bazi
        Proizvod proizvod = proizvodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proizvod sa ID-jem " + id + " ne postoji!"));

        // Sigurnosna provera: Ako je vec bio isprzen, ne zelimo ponovo da umanjujemo zalihe
        if ("ISPRZENO".equals(proizvod.getOpis())) {
            throw new RuntimeException("Ovaj proizvod je vec isprzen! Status se ne moze ponovo azurirati.");
        }

        // 2. Azuriramo status (polje opis)
        proizvod.setOpis(noviStatus);

        // 3. Ako je status postavljen na ISPRZENO, umanjujemo stanje sirovih zrna
        if ("ISPRZENO".equalsIgnoreCase(noviStatus)) {
            SirovaZrna zrna = proizvod.getZrna(); // Izvlacimo sirova zrna vezana za ovaj proizvod

            double trenutnaKolicina = zrna.getKolicinaNaStanju();
            double kolicinaZaUmanjenje = proizvod.getKolicinaPrzena();

            // Provera da li uopste imamo dovoljno kafe na stanju za przenje
            if (trenutnaKolicina < kolicinaZaUmanjenje) {
                throw new RuntimeException("Nemate dovoljno sirove kafe '" + zrna.getNaziv() + "' na stanju! "
                        + "Preostalo: " + trenutnaKolicina + "kg, a potrebno je: " + kolicinaZaUmanjenje + "kg.");
            }

            // Umanjujemo kolicinu u memoriji
            zrna.setKolicinaNaStanju(trenutnaKolicina - kolicinaZaUmanjenje);

            // cuvamo novo stanje sirovih zrna u bazu
            sirovaZrnaRepository.save(zrna);
        }

        return proizvodRepository.save(proizvod);
    }

    public List<DnevnoPrzenjeDTO> generisiDnevniSpisakPrzenja() {
        LocalDate danas = LocalDate.now();
        return proizvodRepository.pronadjiSveZaPrzenjeNaDan(danas);
    }

}
