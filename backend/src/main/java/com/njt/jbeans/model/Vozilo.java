package com.njt.jbeans.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 *
 * @author cid
 */
@Entity
@Table(name = "vozilo")
public class Vozilo {
    
    @Id
    @Size(min = 9, max = 10, message= "Registracija mora imati izmedju 9 i 10 karaktera!")
//    @Pattern(
//        regexp = "^[A-ZŠĐČĆŽWXY]{2}-[0-9]{3,4}-[A-ZŠĐČĆŽWXY]{2}$", 
//        message = "Registracija mora biti u ispravnom formatu sa crticama i velikim slovima (npr. BG-045-AB)!"
//    )
    @Column(name = "registracija", length = 10)
    private String registracija;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "tip_vozila_id")
    @org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.RESTRICT)
    private TipVozila tipVozila;

    public Vozilo() {
    }

    public Vozilo(String registracija, TipVozila tipVozila) {
        this.registracija = registracija;
        this.tipVozila = tipVozila;
    }

    public String getRegistracija() {
        return registracija;
    }

    public void setRegistracija(String registracija) {
        this.registracija = registracija;
    }

    public TipVozila getTipVozila() {
        return tipVozila;
    }

    public void setTipVozila(TipVozila tipVozila) {
        this.tipVozila = tipVozila;
    }
}