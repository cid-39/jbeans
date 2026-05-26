package com.njt.jbeans.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 
 * 
 *
 * @author cid
 */
@Entity
@Table(name = "tip_przenja")
public class TipPrzenja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "naziv", nullable = false, unique = true)
    private String naziv;

    public TipPrzenja() {
    }
    
    public TipPrzenja(String naziv) {
        this.naziv = naziv;
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
}