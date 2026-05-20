/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.model;

/**
 *
 * @author cid
 */
public class Admin extends Korisnik {
    private String sluzbeniTelefon;
    private boolean isMenadzer;

    public Admin() {
    }

    public Admin(String sluzbeniTelefon, boolean isMenadzer, int id, String email, String password, String username) {
        super(id, email, password, username);
        this.sluzbeniTelefon = sluzbeniTelefon;
        this.isMenadzer = isMenadzer;
    }

    public String getSluzbeniTelefon() {
        return sluzbeniTelefon;
    }

    public void setSluzbeniTelefon(String sluzbeniTelefon) {
        this.sluzbeniTelefon = sluzbeniTelefon;
    }

    public boolean isIsMenadzer() {
        return isMenadzer;
    }

    public void setIsMenadzer(boolean isMenadzer) {
        this.isMenadzer = isMenadzer;
    }
    
}
