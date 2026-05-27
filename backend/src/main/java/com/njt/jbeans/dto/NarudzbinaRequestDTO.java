/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.dto;

import com.njt.jbeans.model.TipPrzenja;
import java.time.LocalDateTime;
import java.util.List;
/**
 *
 * @author cid
 */
public class NarudzbinaRequestDTO {

    private LocalDateTime datumDostave;
    private List<StavkaKorpeDTO> stavke;
    
    private Integer period;

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }
    
    // Geteri i seteri
    public LocalDateTime getDatumDostave() { return datumDostave; }
    public void setDatumDostave(LocalDateTime datumDostave) { this.datumDostave = datumDostave; }

    public List<StavkaKorpeDTO> getStavke() { return stavke; }
    public void setStavke(List<StavkaKorpeDTO> stavke) { this.stavke = stavke; }

    // Unutrašnja pomoćna klasa za uparene parove (id_zrna, kolicina)
    public static class StavkaKorpeDTO {
        private Integer sirovaZrnaId;
        private Double kolicina;
        private TipPrzenja tipPrzenja;

        public TipPrzenja getTipPrzenja() {
            return tipPrzenja;
        }

        public void setTipPrzenja(TipPrzenja tipPrzenja) {
            this.tipPrzenja = tipPrzenja;
        }
        
        public Integer getSirovaZrnaId() { return sirovaZrnaId; }
        public void setSirovaZrnaId(Integer sirovaZrnaId) { this.sirovaZrnaId = sirovaZrnaId; }

        public Double getKolicina() { return kolicina; }
        public void setKolicina(Double kolicina) { this.kolicina = kolicina; }
    }
}