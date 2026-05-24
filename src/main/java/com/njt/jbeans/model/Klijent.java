package com.njt.jbeans.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 *
 * @author cid
 */
@Entity
@Table(name = "klijent")
public class Klijent extends Korisnik {
    
    @Column(name = "adresa")
    private String adresa;

    public Klijent() {
    }

    public Klijent(String adresa, int id, String email, String password, String username) {
        super(id, email, password, username);
        this.adresa = adresa;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
}