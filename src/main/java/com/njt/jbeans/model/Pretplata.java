/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.model;

/**
 *
 * @author cid
 */
public class Pretplata {
    private int id;
    private int period;
    private int idNarudzbine;
    private Narudzbina narudzbina;

    public Pretplata() {
    }

    public Pretplata(int id, int period, int idNarudzbine) {
        this.id = id;
        this.period = period;
        this.idNarudzbine = idNarudzbine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getIdNarudzbine() {
        return idNarudzbine;
    }

    public void setIdNarudzbine(int idNarudzbine) {
        this.idNarudzbine = idNarudzbine;
    }

    public Narudzbina getNarudzbina() {
        return narudzbina;
    }

    public void setNarudzbina(Narudzbina narudzbina) {
        this.narudzbina = narudzbina;
    }
    
}
