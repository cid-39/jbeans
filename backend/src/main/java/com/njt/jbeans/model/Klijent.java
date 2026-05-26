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
@Table(name = "klijent")
@OnDelete(action = OnDeleteAction.CASCADE)
public class Klijent extends Korisnik {
    
    @Column(name = "adresa", nullable = false)
    private String adresa;

    public Klijent() {
    }

    public Klijent(String adresa, Integer id, String email, String password, String username) {
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