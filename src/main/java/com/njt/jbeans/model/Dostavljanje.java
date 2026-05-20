/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.model;

import java.util.Date;

/**
 *
 * @author cid
 */
public class Dostavljanje {
    private String registracijaVozila;
    private Date datumDostave;
    private String status;

    public Dostavljanje() {
    }

    public Dostavljanje(String registracijaVozila, Date datumDostave, String status) {
        this.registracijaVozila = registracijaVozila;
        this.datumDostave = datumDostave;
        this.status = status;
    }

    public String getRegistracijaVozila() {
        return registracijaVozila;
    }

    public void setRegistracijaVozila(String registracijaVozila) {
        this.registracijaVozila = registracijaVozila;
    }

    public Date getDatumDostave() {
        return datumDostave;
    }

    public void setDatumDostave(Date datumDostave) {
        this.datumDostave = datumDostave;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
