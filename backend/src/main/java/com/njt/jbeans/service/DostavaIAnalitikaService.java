/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.service;

import com.njt.jbeans.model.Dostavljanje;
import com.njt.jbeans.model.Narudzbina;
import com.njt.jbeans.repository.DostavljanjeRepository;
import com.njt.jbeans.repository.NarudzbinaRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author cid
 */
@Service
public class DostavaIAnalitikaService {

    private final DostavljanjeRepository dostavljanjeRepository;
    private final NarudzbinaRepository narudzbinaRepository;
    private final PretplataService pretplataService;

    public DostavaIAnalitikaService(DostavljanjeRepository dostavljanjeRepository, NarudzbinaRepository narudzbinaRepository, PretplataService pretplataService) {
        this.dostavljanjeRepository = dostavljanjeRepository;
        this.narudzbinaRepository = narudzbinaRepository;
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

    public Object getGlobalnaAnalitika() {
        return "mockAnalitika";
    }
}
