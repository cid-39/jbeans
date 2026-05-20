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
public class Narudzbina {
    private int id;
    private Date datumPorucivanja;
    private double ukupnaCena;
    private int idKlijenta;
    private int idDostavljanje;
    private boolean pretplata;

    public Narudzbina() {
    }

    public Narudzbina(int id, Date datumPorucivanja, double ukupnaCena, int idKlijenta, int idDostavljanje, boolean pretplata) {
        this.id = id;
        this.datumPorucivanja = datumPorucivanja;
        this.ukupnaCena = ukupnaCena;
        this.idKlijenta = idKlijenta;
        this.idDostavljanje = idDostavljanje;
        this.pretplata = pretplata;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatumPorucivanja() {
        return datumPorucivanja;
    }

    public void setDatumPorucivanja(Date datumPorucivanja) {
        this.datumPorucivanja = datumPorucivanja;
    }

    public double getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(double ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    public int getIdKlijenta() {
        return idKlijenta;
    }

    public void setIdKlijenta(int idKlijenta) {
        this.idKlijenta = idKlijenta;
    }

    public int getIdDostavljanje() {
        return idDostavljanje;
    }

    public void setIdDostavljanje(int idDostavljanje) {
        this.idDostavljanje = idDostavljanje;
    }

    public boolean isPretplata() {
        return pretplata;
    }

    public void setPretplata(boolean pretplata) {
        this.pretplata = pretplata;
    }
    
}
