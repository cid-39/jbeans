package com.njt.jbeans.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tip_vozila")
public class TipVozila {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String naziv;

    public TipVozila() {}

    public TipVozila(String naziv) {
        this.naziv = naziv;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }
}