/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.service;

import com.njt.jbeans.dto.NarudzbinaRequestDTO;
import com.njt.jbeans.model.Klijent;
import com.njt.jbeans.model.Korisnik;
import com.njt.jbeans.model.Narudzbina;
import com.njt.jbeans.model.Pretplata;
import com.njt.jbeans.model.StavkaNarudzbine;
import com.njt.jbeans.repository.KorisnikRepository;
import com.njt.jbeans.repository.NarudzbinaRepository;
import com.njt.jbeans.repository.PretplataRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final NarudzbinaRepository narudzbinaRepository;
    private final NarudzbinaService narudzbinaService;

    public PretplataService(PretplataRepository pretplataRepository, KorisnikRepository korisnikRepository, NarudzbinaRepository narudzbinaRepository, NarudzbinaService narudzbinaService) {
        this.pretplataRepository = pretplataRepository;
        this.korisnikRepository = korisnikRepository;
        this.narudzbinaService = narudzbinaService;
        this.narudzbinaRepository = narudzbinaRepository;
    }

    public List<Pretplata> getAllPretplata() {
        return pretplataRepository.findAll();
    }

    public List<Pretplata> getMojePretplate(String email) {
        Korisnik korisnik = korisnikRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Korisnik sa ovim email-om ne postoji!"));

        Klijent klijent = new Klijent();
        klijent.setId(korisnik.getId());

        return pretplataRepository.findAllByKlijent(klijent);
    }

    @Transactional
    public Pretplata createPretplata(NarudzbinaRequestDTO dto, String email) {
        if (dto.getPeriod() == null || dto.getPeriod() <= 0) {
            throw new RuntimeException("Morate uneti validan period u danima za pretplatu!");
        }

        Narudzbina baznaNarudzbina = narudzbinaService.createNarudzbina(dto, email);

        baznaNarudzbina.setPretplata(true);

        Pretplata novaPretplata = new Pretplata();
        novaPretplata.setPeriod(dto.getPeriod());
        novaPretplata.setAktivna(true);

        novaPretplata.dodajNarudzbinu(baznaNarudzbina);

        return pretplataRepository.save(novaPretplata);
    }

    @Transactional
    public Pretplata updatePretplata(Integer id, Pretplata izmene) {
        if (izmene.getPeriod() == null || izmene.getPeriod() <= 0) {
            throw new RuntimeException("Period mora biti veći od 0 dana!");
        }

        Pretplata postojecaPretplata = pretplataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pretplata sa ID-jem " + id + " ne postoji!"));

        postojecaPretplata.setPeriod(izmene.getPeriod());

        return pretplataRepository.save(postojecaPretplata);
    }

    public Pretplata disablePretplata(Integer id) {
        Pretplata pretplata = pretplataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pretplata sa ID-jem " + id + " ne postoji!"));

        pretplata.setAktivna(false);
        return pretplataRepository.save(pretplata);
    }

    @Transactional
    public void proveriIKreirajSledeciKrug(Narudzbina staraNarudzbina) {
        Pretplata pretplata = staraNarudzbina.getPretplataObjekat();

        if (pretplata != null && Boolean.TRUE.equals(pretplata.getAktivna())) {

            LocalDateTime sledeciDatum = staraNarudzbina.getDostavljanje().getDatumDostave().plusDays(pretplata.getPeriod()).atStartOfDay();

            NarudzbinaRequestDTO noviDto = new NarudzbinaRequestDTO();
            noviDto.setDatumDostave(sledeciDatum);
            noviDto.setPeriod(pretplata.getPeriod());

            List<NarudzbinaRequestDTO.StavkaKorpeDTO> noveStavkeDto = new ArrayList<>();
            for (StavkaNarudzbine staraStavka : staraNarudzbina.getStavke()) {
                NarudzbinaRequestDTO.StavkaKorpeDTO stavkaDto = new NarudzbinaRequestDTO.StavkaKorpeDTO();
                stavkaDto.setSirovaZrnaId(staraStavka.getProizvod().getZrna().getId());
                stavkaDto.setKolicina(staraStavka.getKolicina());
                stavkaDto.setTipPrzenja(staraStavka.getProizvod().getTipPrzenja());
                noveStavkeDto.add(stavkaDto);
            }
            noviDto.setStavke(noveStavkeDto);
            
            noviDto.setAdresa(staraNarudzbina.getKlijent().getAdresa());
            
            String emailKlijenta = staraNarudzbina.getKlijent().getEmail();
            Narudzbina novaNarudzbina = narudzbinaService.createNarudzbina(noviDto, emailKlijenta);

            novaNarudzbina.setPretplata(true);
            pretplata.dodajNarudzbinu(novaNarudzbina);

            pretplataRepository.save(pretplata);
        }
    }
}
