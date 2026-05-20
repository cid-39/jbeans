/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.model;

/**
 *
 * @author cid
 */
public class Proizvod {
    private int id;
    private String naziv;
    private String opis;
    private double kolicinaPrzena;
    private int idZrna;
    private TipPrzenja tipPrzenja;
    private SirovaZrna zrna;

    public SirovaZrna getZrna() {
        return zrna;
    }

    public void setZrna(SirovaZrna zrna) {
        this.zrna = zrna;
    }

    public Proizvod() {
    }

    public Proizvod(int id, String naziv, String opis, double kolicinaPrzena, int idZrna, TipPrzenja tipPrzenja) {
        this.id = id;
        this.naziv = naziv;
        this.opis = opis;
        this.kolicinaPrzena = kolicinaPrzena;
        this.idZrna = idZrna;
        this.tipPrzenja = tipPrzenja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getKolicinaPrzena() {
        return kolicinaPrzena;
    }

    public void setKolicinaPrzena(double kolicinaPrzena) {
        this.kolicinaPrzena = kolicinaPrzena;
    }

    public int getIdZrna() {
        return idZrna;
    }

    public void setIdZrna(int idZrna) {
        this.idZrna = idZrna;
    }

    public TipPrzenja getTipPrzenja() {
        return tipPrzenja;
    }

    public void setTipPrzenja(TipPrzenja tipPrzenja) {
        this.tipPrzenja = tipPrzenja;
    }
    
}
