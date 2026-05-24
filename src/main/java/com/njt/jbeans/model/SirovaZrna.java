package com.njt.jbeans.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "sirova_zrna")
public class SirovaZrna {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "naziv", nullable = false)
    private String naziv;

    @Column(name = "kolicina_na_stanju")
    private Double kolicinaNaStanju;

    @Column(name = "cena_po_meri")
    private Double cenaPoMeri;

    @ManyToOne
    @JoinColumn(name = "pib_dobavljaca", nullable = false)
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