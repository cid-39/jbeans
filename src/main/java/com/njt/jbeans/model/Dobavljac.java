/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.model;

/**
 *
 * @author cid
 */
public class Dobavljac {
    private String pib;
    private String naziv;
    private String brojTelefona;

    public Dobavljac() {
    }

    public Dobavljac(String pib, String naziv, String brojTelefona) {
        this.pib = pib;
        this.naziv = naziv;
        this.brojTelefona = brojTelefona;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }
}
