package com.njt.jbeans.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cid
 */
@Entity
@Table(name = "pretplata")
public class Pretplata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "period")
    private Integer period;

    @Column(name = "aktivna")
    private Boolean aktivna;

    @OneToMany(mappedBy = "pretplataObjekat", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Narudzbina> narudzbine = new ArrayList<>();

    public void dodajNarudzbinu(Narudzbina narudzbina) {
        this.narudzbine.add(narudzbina);
        narudzbina.setPretplataObjekat(this);
    }

    public Pretplata() {
    }

    public Pretplata(Integer id, Integer period, Boolean aktivna) {
        this.id = id;
        this.period = period;
        this.aktivna = aktivna;
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPeriod() {
        return period;
    }

    public List<Narudzbina> getNarudzbine() {
        return narudzbine;
    }

    public void setNarudzbine(List<Narudzbina> narudzbine) {
        this.narudzbine = narudzbine;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Boolean getAktivna() {
        return aktivna;
    }

    public void setAktivna(Boolean aktivna) {
        this.aktivna = aktivna;
    }
}