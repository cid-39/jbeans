/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.dto;

/**
 *
 * @author cid
 */
public class DnevnoPrzenjeDTO {
    private String nazivZrna;
    private String tipPrzenja;
    private double kolicina;
    private String opis;

    public DnevnoPrzenjeDTO(String nazivZrna, String tipPrzenja, double kolicina, String opis) {
        this.nazivZrna = nazivZrna;
        this.tipPrzenja = tipPrzenja;
        this.kolicina = kolicina;
        this.opis = opis;
    }

    public DnevnoPrzenjeDTO() {
    }

    public String getNazivZrna() {
        return nazivZrna;
    }

    public void setNazivZrna(String nazivZrna) {
        this.nazivZrna = nazivZrna;
    }

    public String getTipPrzenja() {
        return tipPrzenja;
    }

    public void setTipPrzenja(String tipPrzenja) {
        this.tipPrzenja = tipPrzenja;
    }

    public double getKolicina() {
        return kolicina;
    }

    public void setKolicina(double kolicina) {
        this.kolicina = kolicina;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    
    
}