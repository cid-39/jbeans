package com.njt.jbeans.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @org.hibernate.annotations.CreationTimestamp
    @Column(name = "datum_porucivanja", nullable = false, updatable = false)
    private LocalDateTime datumPorucivanja;
    
    @Positive(message = "Ukupna cena narudžbine mora biti veća od nule!")
    @Column(name = "ukupna_cena", nullable = false)
    private Double ukupnaCena = 0.0;

    @ManyToOne
    @JoinColumn(name = "klijent_id", nullable = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    private Klijent klijent;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "dostavljanje_id")
    private Dostavljanje dostavljanje;

    @OneToMany(mappedBy = "narudzbina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StavkaNarudzbine> stavke = new ArrayList<>();

    @Column(name = "pretplata", nullable = false)
    private boolean pretplata = false;

    @ManyToOne
    @JoinColumn(name = "pretplata_id")
    @JsonIgnore
    private Pretplata pretplataObjekat;

    public Pretplata getPretplataObjekat() {
        return pretplataObjekat;
    }

    public void setPretplataObjekat(Pretplata pretplataObjekat) {
        this.pretplataObjekat = pretplataObjekat;
    }

    public Narudzbina() {
    }

    public Narudzbina(Integer id, LocalDateTime datumPorucivanja, Double ukupnaCena, Klijent klijent, Dostavljanje dostavljanje, boolean pretplata) {
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

    public LocalDateTime getDatumPorucivanja() {
        return datumPorucivanja;
    }

    public void setDatumPorucivanja(LocalDateTime datumPorucivanja) {
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

    public List<StavkaNarudzbine> getStavke() {
        return stavke;
    }

    public void setStavke(List<StavkaNarudzbine> stavke) {
        this.stavke = stavke;
    }

    public void dodajStavku(StavkaNarudzbine stavka) {
        this.stavke.add(stavka);
        stavka.setNarudzbina(this);
    }
}