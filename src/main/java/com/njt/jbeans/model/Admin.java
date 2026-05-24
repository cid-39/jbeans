package com.njt.jbeans.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 *
 * @author cid
 */
@Entity
@Table(name = "admin") 
public class Admin extends Korisnik {
    
    @Column(name = "sluzbeni_telefon")
    private String sluzbeniTelefon;
    
    @Column(name = "is_menadzer")
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
