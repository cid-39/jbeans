/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.dto;

import com.njt.jbeans.model.TipPrzenja;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
/**
 *
 * @author cid
 */
public class NarudzbinaRequestDTO {

    @NotNull(message = "Datum dostave je obavezan!")
    @Future(message = "Datum dostave mora biti isključivo u buducnosti (od sutra pa nadalje)!")
    private LocalDateTime datumDostave;
    
    @Valid //
    @NotEmpty(message = "Korpa ne sme biti prazna!")
    private List<StavkaKorpeDTO> stavke;
    
    private Integer period;
    
    @NotBlank(message = "Adresa ne sme biti prazna!")
    private String adresa;

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

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
        @NotNull(message = "ID sirovog zrna je obavezan!")
        private Integer sirovaZrnaId;
        
        @NotNull(message = "Kolicina je obavezna!")
        @DecimalMin(value = "0.2", inclusive = true, message = "Kolicina mora biti najmanje 0.2 kg!")
        private Double kolicina;
        
        @NotNull(message = "Tip przenja je obavezan!")
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