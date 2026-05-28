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
import com.njt.jbeans.repository.DostavljanjeRepository;
import com.njt.jbeans.repository.KlijentRepository;
import com.njt.jbeans.repository.KorisnikRepository;
import com.njt.jbeans.repository.NarudzbinaRepository;
import com.njt.jbeans.repository.ProizvodRepository;
import com.njt.jbeans.repository.SirovaZrnaRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private final DostavljanjeRepository dostavljanjeRepository;
    private final ProizvodRepository proizvodRepository;
    private final KlijentRepository klijentRepository;

    public NarudzbinaService(NarudzbinaRepository narudzbinaRepository, KorisnikRepository korisnikRepository, SirovaZrnaRepository sirovaZrnaRepository, DostavljanjeRepository dostavljanjeRepository, KlijentRepository klijentRepository, ProizvodRepository proizvodRepository) {
        this.narudzbinaRepository = narudzbinaRepository;
        this.korisnikRepository = korisnikRepository;
        this.sirovaZrnaRepository = sirovaZrnaRepository;
        this.dostavljanjeRepository = dostavljanjeRepository;
        this.proizvodRepository = proizvodRepository;
        this.klijentRepository = klijentRepository;
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
        LocalDateTime pocetakSutrasnjegDana = LocalDate.now().plusDays(1).atStartOfDay();

        if (dto.getDatumDostave().isBefore(pocetakSutrasnjegDana)) {
            throw new RuntimeException("Dostava ne može biti realizovana danas! Izaberite sutrašnji ili neki naredni dan.");
        }

        Klijent klijent = klijentRepository.pronadjiPoEmailu(email)
                .map(postojeciKlijent -> {
                    // Ako klijent postoji, samo mu ažuriramo adresu u memoriji
                    if (dto.getAdresa() != null && !dto.getAdresa().isBlank()) {
                        postojeciKlijent.setAdresa(dto.getAdresa());
                    }
                    return postojeciKlijent;
                })
                .orElseGet(() -> {
                    Integer korisnikId = korisnikRepository.pronadjiIdPoEmailu(email)
                            .orElseThrow(() -> new RuntimeException("Korisnik sa ovim email-om ne postoji!"));

                    klijentRepository.ubaciKlijenta(korisnikId, dto.getAdresa());

                    return klijentRepository.pronadjiPoEmailu(email).get();
                });

        // 2. Kreiramo glavnu narudžbinu
        Narudzbina narudzbina = new Narudzbina();
        narudzbina.setKlijent(klijent);
        narudzbina.setPretplata(false);

        // 3. DOSTAVA: Proveravamo da li vec postoji dostava za taj dan koja čeka
        Dostavljanje dostavljanje = dostavljanjeRepository
                .findByDatumDostaveAndStatus(dto.getDatumDostave().toLocalDate(), "CEKA")
                .orElseGet(() -> {
                    // Ako ne postoji, pravimo potpuno novu dostavu
                    Dostavljanje novaDostava = new Dostavljanje();
                    novaDostava.setDatumDostave(dto.getDatumDostave().toLocalDate());
                    novaDostava.setStatus("CEKA");
                    return dostavljanjeRepository.save(novaDostava);
                });

        // Vezujemo narudžbinu za nađenu ili novu dostavu
        narudzbina.setDostavljanje(dostavljanje);

        // 4. Prolazimo kroz stavke iz DTO-a
        double tekucaUkupnaCena = 0.0;
        for (NarudzbinaRequestDTO.StavkaKorpeDTO stavkaDto : dto.getStavke()) {
            SirovaZrna zrno = sirovaZrnaRepository.findById(stavkaDto.getSirovaZrnaId())
                    .orElseThrow(() -> new RuntimeException("Sirovo zrno sa ID " + stavkaDto.getSirovaZrnaId() + " ne postoji!"));

            // PROIZVOD: Proveravamo da li se ista kafa sa istim prženjem vec przi tog dana
            Proizvod proizvod = proizvodRepository
                    .findByDatumPrzenjaAndZrnaIdAndTipPrzenjaId(dto.getDatumDostave().toLocalDate(), zrno.getId(), stavkaDto.getTipPrzenja().getId())
                    .map(postojeciProizvod -> {
                        // Ako postoji, samo dodajemo novu kolicinu na vec postojecu u bazi
                        postojeciProizvod.setKolicinaPrzena(postojeciProizvod.getKolicinaPrzena() + stavkaDto.getKolicina());
                        return proizvodRepository.save(postojeciProizvod);
                    })
                    .orElseGet(() -> {
                        // Ako ne postoji, pravimo novi proizvod za przenje
                        Proizvod noviProizvod = new Proizvod();
                        noviProizvod.setZrna(zrno);
                        noviProizvod.setTipPrzenja(stavkaDto.getTipPrzenja());
                        noviProizvod.setKolicinaPrzena(stavkaDto.getKolicina());
                        noviProizvod.setDatumPrzenja(dto.getDatumDostave().toLocalDate());
                        noviProizvod.setOpis("CEKA PRZENJE");
                        return proizvodRepository.save(noviProizvod);
                    });

            // 5. Pravimo stavku narudzbine i vezujemo je za (nadjeni ili novi) proizvod
            StavkaNarudzbine stavka = new StavkaNarudzbine();
            stavka.setProizvod(proizvod);
            stavka.setKolicina(stavkaDto.getKolicina());

            // Racunamo cenu stavke
            double cenaStavke = stavkaDto.getKolicina() * zrno.getCenaPoMeri();
            stavka.setCena(cenaStavke);
            tekucaUkupnaCena += cenaStavke;

            stavka.setNarudzbina(narudzbina);
            narudzbina.getStavke().add(stavka);
        }

        // 6. Upisujemo konacnu cenu i cuvamo narudzbinu
        narudzbina.setUkupnaCena(tekucaUkupnaCena);

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
