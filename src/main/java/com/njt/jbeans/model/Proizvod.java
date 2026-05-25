package com.njt.jbeans.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author cid
 */
@Entity
@Table(name = "proizvod")
public class Proizvod {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; 

    @Column(name = "naziv", nullable = false)
    private String naziv;

    @Column(name = "opis")
    private String opis;

    @Column(name = "kolicina_przena", nullable = false)
    private Double kolicinaPrzena = 0.0; 


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "tip_przenja_id")
    @org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.RESTRICT)
    private TipPrzenja tipPrzenja;
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "zrna_id", nullable = false)
    @org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.RESTRICT)
    private SirovaZrna zrna;

    public Proizvod() {
    }

    public Proizvod(Integer id, String naziv, String opis, Double kolicinaPrzena, TipPrzenja tipPrzenja, SirovaZrna zrna) {
        this.id = id;
        this.naziv = naziv;
        this.opis = opis;
        this.kolicinaPrzena = kolicinaPrzena;
        this.tipPrzenja = tipPrzenja;
        this.zrna = zrna;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Double getKolicinaPrzena() {
        return kolicinaPrzena;
    }

    public void setKolicinaPrzena(Double kolicinaPrzena) {
        this.kolicinaPrzena = kolicinaPrzena;
    }

    public TipPrzenja getTipPrzenja() {
        return tipPrzenja;
    }

    public void setTipPrzenja(TipPrzenja tipPrzenja) {
        this.tipPrzenja = tipPrzenja;
    }

    public SirovaZrna getZrna() {
        return zrna;
    }

    public void setZrna(SirovaZrna zrna) {
        this.zrna = zrna;
    }
}