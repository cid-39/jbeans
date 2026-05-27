package com.njt.jbeans.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

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
    
    @Column(name = "datum_przenja", nullable = false, updatable=false)
    private LocalDate datumPrzenja;

    @Column(name = "opis")
    private String opis;

    @Positive(message = "Kolicina przena mora strogo veca od nule!")
    @Column(name = "kolicina_przena", nullable = false)
    private Double kolicinaPrzena = 0.0; 


    @ManyToOne
    @JoinColumn(name = "tip_przenja_id")
    @org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.RESTRICT)
    private TipPrzenja tipPrzenja;
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "zrna_id", nullable = false)
    @org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.RESTRICT)
    private SirovaZrna zrna;

    public Proizvod() {
    }

    public Proizvod(Integer id, LocalDate datumPrzenja, String opis, Double kolicinaPrzena, TipPrzenja tipPrzenja, SirovaZrna zrna) {
        this.id = id;
        this.datumPrzenja = datumPrzenja;
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

    public LocalDate getDatumPrzenja() {
        return datumPrzenja;
    }

    public void setDatumPrzenja(LocalDate datumPrzenja) {
        this.datumPrzenja = datumPrzenja;
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