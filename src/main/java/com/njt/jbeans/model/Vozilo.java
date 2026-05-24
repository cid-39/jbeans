package com.njt.jbeans.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author cid
 */
@Entity
@Table(name = "vozilo")
public class Vozilo {
    
    @Id
    @Column(name = "registracija", length = 20)
    private String registracija;

    @ManyToOne
    @JoinColumn(name = "tip_vozila_id")
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