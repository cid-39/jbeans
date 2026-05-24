package com.njt.jbeans.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

/**
 *
 * @author cid
 */
@Entity
@Table(name = "narudzbina")
public class Narudzbina {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; 

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datum_porucivanja")
    private Date datumPorucivanja;
    
    @Column(name = "ukupna_cena")
    private Double ukupnaCena; 

    @ManyToOne
    @JoinColumn(name = "klijent_id", nullable = false) 
    private Klijent klijent;

    @ManyToOne
    @JoinColumn(name = "dostavljanje_id") 
    private Dostavljanje dostavljanje;

    @Column(name = "pretplata")
    private boolean pretplata;

    public Narudzbina() {
    }

    public Narudzbina(Integer id, Date datumPorucivanja, Double ukupnaCena, Klijent klijent, Dostavljanje dostavljanje, boolean pretplata) {
        this.id = id;
        this.datumPorucivanja = datumPorucivanja;
        this.ukupnaCena = ukupnaCena;
        this.klijent = klijent;
        this.dostavljanje = dostavljanje;
        this.pretplata = pretplata;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDatumPorucivanja() {
        return datumPorucivanja;
    }

    public void setDatumPorucivanja(Date datumPorucivanja) {
        this.datumPorucivanja = datumPorucivanja;
    }

    public Double getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(Double ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    public Klijent getKlijent() {
        return klijent;
    }

    public void setKlijent(Klijent klijent) {
        this.klijent = klijent;
    }

    public Dostavljanje getDostavljanje() {
        return dostavljanje;
    }

    public void setDostavljanje(Dostavljanje dostavljanje) {
        this.dostavljanje = dostavljanje;
    }

    public boolean isPretplata() {
        return pretplata;
    }

    public void setPretplata(boolean pretplata) {
        this.pretplata = pretplata;
    }
}