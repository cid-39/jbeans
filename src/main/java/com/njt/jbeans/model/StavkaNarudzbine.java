package com.njt.jbeans.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 *
 * @author cid
 */
@Entity
@Table(name = "stavka_narudzbine")
public class StavkaNarudzbine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "narudzbina_id", nullable = false)
    private Narudzbina narudzbina;

    @ManyToOne
    @JoinColumn(name = "proizvod_id", nullable = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    private Proizvod proizvod;

    @Column(name = "cena", nullable = false)
    private Double cena;

    @Column(name = "kolicina", nullable = false)
    private Double kolicina;

    public StavkaNarudzbine() {
    }

    public StavkaNarudzbine(Integer id, Narudzbina narudzbina, Proizvod proizvod, Double cena, Double kolicina) {
        this.id = id;
        this.narudzbina = narudzbina;
        this.proizvod = proizvod;
        this.cena = cena;
        this.kolicina = kolicina;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Narudzbina getNarudzbina() { return narudzbina; }
    public void setNarudzbina(Narudzbina narudzbina) { this.narudzbina = narudzbina; }
    
    public Proizvod getProizvod() { return proizvod; }
    public void setProizvod(Proizvod proizvod) { this.proizvod = proizvod; }
    
    public Double getCena() { return cena; }
    public void setCena(Double cena) { this.cena = cena; }
    
    public Double getKolicina() { return kolicina; }
    public void setKolicina(Double kolicina) { this.kolicina = kolicina; }
    
    
}