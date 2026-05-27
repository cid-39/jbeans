/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.dto;

import com.njt.jbeans.dto.analitika.DobavljacStatistika;
import com.njt.jbeans.dto.analitika.DostavaStatistika;
import com.njt.jbeans.dto.analitika.KlijentStatistika;
import com.njt.jbeans.dto.analitika.PrzenjeStatistika;
import com.njt.jbeans.dto.analitika.ZrnaStatistika;
import java.util.List;

/**
 *
 * @author cid
 */
public class AnalitikaResponseDTO {
    private List<ZrnaStatistika> popularnijaZrna;
    private List<PrzenjeStatistika> trendoviPrzenja;
    private List<DobavljacStatistika> topDobavljaci;
    private List<KlijentStatistika> topKlijenti;
    private List<DostavaStatistika> opterecenostDostave;
    private Double prosecnaVrednostKorpe;
    private Double ukupanPrihod;

    public List<ZrnaStatistika> getPopularnijaZrna() {
        return popularnijaZrna;
    }

    public void setPopularnijaZrna(List<ZrnaStatistika> popularnijaZrna) {
        this.popularnijaZrna = popularnijaZrna;
    }

    public List<PrzenjeStatistika> getTrendoviPrzenja() {
        return trendoviPrzenja;
    }

    public void setTrendoviPrzenja(List<PrzenjeStatistika> trendoviPrzenja) {
        this.trendoviPrzenja = trendoviPrzenja;
    }

    public List<DobavljacStatistika> getTopDobavljaci() {
        return topDobavljaci;
    }

    public void setTopDobavljaci(List<DobavljacStatistika> topDobavljaci) {
        this.topDobavljaci = topDobavljaci;
    }

    public List<KlijentStatistika> getTopKlijenti() {
        return topKlijenti;
    }

    public void setTopKlijenti(List<KlijentStatistika> topKlijenti) {
        this.topKlijenti = topKlijenti;
    }

    public List<DostavaStatistika> getOpterecenostDostave() {
        return opterecenostDostave;
    }

    public void setOpterecenostDostave(List<DostavaStatistika> opterecenostDostave) {
        this.opterecenostDostave = opterecenostDostave;
    }

    public Double getProsecnaVrednostKorpe() {
        return prosecnaVrednostKorpe;
    }

    public void setProsecnaVrednostKorpe(Double prosecnaVrednostKorpe) {
        this.prosecnaVrednostKorpe = prosecnaVrednostKorpe;
    }

    public Double getUkupanPrihod() {
        return ukupanPrihod;
    }

    public void setUkupanPrihod(Double ukupanPrihod) {
        this.ukupanPrihod = ukupanPrihod;
    }

    
}
