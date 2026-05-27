/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.service;

import com.njt.jbeans.dto.AnalitikaResponseDTO;
import com.njt.jbeans.model.Dostavljanje;
import com.njt.jbeans.model.Narudzbina;
import com.njt.jbeans.repository.DobavljacRepository;
import com.njt.jbeans.repository.DostavljanjeRepository;
import com.njt.jbeans.repository.KlijentRepository;
import com.njt.jbeans.repository.NarudzbinaRepository;
import com.njt.jbeans.repository.SirovaZrnaRepository;
import com.njt.jbeans.repository.TipPrzenjaRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author cid
 */
@Service
public class DostavaIAnalitikaService {

    private final SirovaZrnaRepository sirovaZrnaRepository;
    private final TipPrzenjaRepository tipPrzenjaRepository;
    private final DobavljacRepository dobavljacRepository;
    private final NarudzbinaRepository narudzbinaRepository;
    private final KlijentRepository klijentRepository;
    private final DostavljanjeRepository dostavljanjeRepository;
    private final PretplataService pretplataService;

    public DostavaIAnalitikaService(SirovaZrnaRepository sirovaZrnaRepository, TipPrzenjaRepository tipPrzenjaRepository, DobavljacRepository dobavljacRepository, NarudzbinaRepository narudzbinaRepository, KlijentRepository klijentRepository, DostavljanjeRepository dostavljanjeRepository, PretplataService pretplataService) {
        this.sirovaZrnaRepository = sirovaZrnaRepository;
        this.tipPrzenjaRepository = tipPrzenjaRepository;
        this.dobavljacRepository = dobavljacRepository;
        this.narudzbinaRepository = narudzbinaRepository;
        this.klijentRepository = klijentRepository;
        this.dostavljanjeRepository = dostavljanjeRepository;
        this.pretplataService = pretplataService;
    }

    

    @Transactional
    public Dostavljanje updateIshodDostave(Integer id, String ishod) {
        Dostavljanje dostavljanje = dostavljanjeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dostava sa ID-jem " + id + " ne postoji!"));

        dostavljanje.setStatus(ishod);
        Dostavljanje sacuvanoDostavljanje = dostavljanjeRepository.save(dostavljanje);

        if ("ISPORUCENO".equalsIgnoreCase(ishod)) {

            List<Narudzbina> narudzbineNaOvojDostavi = narudzbinaRepository.findByDostavljanje(sacuvanoDostavljanje);

            for (Narudzbina narudzbina : narudzbineNaOvojDostavi) {
                pretplataService.proveriIKreirajSledeciKrug(narudzbina);
            }
        }

        return sacuvanoDostavljanje;
    }

    @Transactional
    public AnalitikaResponseDTO getGlobalnaAnalitika() {
        AnalitikaResponseDTO dto = new AnalitikaResponseDTO();

        dto.setPopularnijaZrna(sirovaZrnaRepository.dobijNajpopularnijaZrna());
        dto.setTrendoviPrzenja(tipPrzenjaRepository.dobijTrendovePrzenja());
        dto.setTopDobavljaci(dobavljacRepository.dobijTopDobavljace());
        dto.setTopKlijenti(klijentRepository.dobijTopKlijente());
        dto.setOpterecenostDostave(dostavljanjeRepository.dobijOpterecenostDostave());

        // Zastita od null vrednosti ako je baza potpuno prazna
        Double prosecna = narudzbinaRepository.dobijProsecnuVrednostKorpe();
        dto.setProsecnaVrednostKorpe(prosecna != null ? prosecna : 0.0);

        Double ukupna = narudzbinaRepository.dobijUkupanPrihod();
        dto.setUkupanPrihod(ukupna != null ? ukupna : 0.0);

        return dto;
    }
}
