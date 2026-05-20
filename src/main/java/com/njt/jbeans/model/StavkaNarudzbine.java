/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.model;

/**
 *
 * @author cid
 */
public class StavkaNarudzbine {
    private int idNarudzbine;
    private int id;
    private double cena;
    private double kolicina;
    private int idProizvoda;

    public StavkaNarudzbine() {
    }

    public StavkaNarudzbine(int idNarudzbine, int id, double cena, double kolicina, int idProizvoda) {
        this.idNarudzbine = idNarudzbine;
        this.id = id;
        this.cena = cena;
        this.kolicina = kolicina;
        this.idProizvoda = idProizvoda;
    }

    public int getIdNarudzbine() {
        return idNarudzbine;
    }

    public void setIdNarudzbine(int idNarudzbine) {
        this.idNarudzbine = idNarudzbine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public double getKolicina() {
        return kolicina;
    }

    public void setKolicina(double kolicina) {
        this.kolicina = kolicina;
    }

    public int getIdProizvoda() {
        return idProizvoda;
    }

    public void setIdProizvoda(int idProizvoda) {
        this.idProizvoda = idProizvoda;
    }
    
}
