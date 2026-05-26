package com.njt.jbeans.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 *
 * @author cid
 */
@Entity
@Table(name = "admin") 
@OnDelete(action = OnDeleteAction.CASCADE)
public class Admin extends Korisnik {
    
    @Column(name = "sluzbeni_telefon", nullable = false)
    private String sluzbeniTelefon;
    
    @Column(name = "is_menadzer", nullable = false)
    private boolean isMenadzer = false;

    public Admin() {
    }

    public Admin(String sluzbeniTelefon, boolean isMenadzer, Integer id, String email, String password, String username) {
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
