/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.model;

/**
 *
 * @author cid
 */
public class SirovaZrna {
    private int id;
    private String naziv;
    private double kolicinaNaStanju;
    private double cenaPoMeri;
    private String pibDobavljaca;

    public SirovaZrna() {
    }

    public SirovaZrna(int id, String naziv, double kolicinaNaStanju, double cenaPoMeri, String pibDobavljaca) {
        this.id = id;
        this.naziv = naziv;
        this.kolicinaNaStanju = kolicinaNaStanju;
        this.cenaPoMeri = cenaPoMeri;
        this.pibDobavljaca = pibDobavljaca;
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

    public double getKolicinaNaStanju() {
        return kolicinaNaStanju;
    }

    public void setKolicinaNaStanju(double kolicinaNaStanju) {
        this.kolicinaNaStanju = kolicinaNaStanju;
    }

    public double getCenaPoMeri() {
        return cenaPoMeri;
    }

    public void setCenaPoMeri(double cenaPoMeri) {
        this.cenaPoMeri = cenaPoMeri;
    }

    public String getPibDobavljaca() {
        return pibDobavljaca;
    }

    public void setPibDobavljaca(String pibDobavljaca) {
        this.pibDobavljaca = pibDobavljaca;
    }   
}
