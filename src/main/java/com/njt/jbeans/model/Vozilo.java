/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.model;

/**
 *
 * @author cid
 */
public class Vozilo {
    private String registracija;
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
