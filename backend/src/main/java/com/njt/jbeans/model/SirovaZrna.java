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
import jakarta.validation.constraints.PositiveOrZero;

/**
 *
 * @author cid
 */
@Entity
@Table(name = "sirova_zrna")
public class SirovaZrna {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "naziv", nullable = false)
    private String naziv;

    @PositiveOrZero(message = "Kolicina na stanju mora biti nula ili veca!")
    @Column(name = "kolicina_na_stanju", nullable = false)
    private Double kolicinaNaStanju = 0.0;

    @Positive(message = "Cena po meri mora biti strogo veca od nule!")
    @Column(name = "cena_po_meri", nullable = false)
    private Double cenaPoMeri;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pib_dobavljaca", nullable = false)
    @org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.RESTRICT)
    private Dobavljac dobavljac;

    public SirovaZrna() {
    }

    public SirovaZrna(Integer id, String naziv, Double kolicinaNaStanju, Double cenaPoMeri, Dobavljac dobavljac) {
        this.id = id;
        this.naziv = naziv;
        this.kolicinaNaStanju = kolicinaNaStanju;
        this.cenaPoMeri = cenaPoMeri;
        this.dobavljac = dobavljac;
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

    public Double getKolicinaNaStanju() {
        return kolicinaNaStanju;
    }

    public void setKolicinaNaStanju(Double kolicinaNaStanju) {
        this.kolicinaNaStanju = kolicinaNaStanju;
    }

    public Double getCenaPoMeri() {
        return cenaPoMeri;
    }

    public void setCenaPoMeri(Double cenaPoMeri) {
        this.cenaPoMeri = cenaPoMeri;
    }

    public Dobavljac getDobavljac() {
        return dobavljac;
    }

    public void setDobavljac(Dobavljac dobavljac) {
        this.dobavljac = dobavljac;
    }
}