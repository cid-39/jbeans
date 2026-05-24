package com.njt.jbeans.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

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

    @OneToOne
    @JoinColumn(name = "narudzbina_id", nullable = false, unique = true)
    private Narudzbina narudzbina;

    public Pretplata() {
    }

    public Pretplata(Integer id, Integer period, Narudzbina narudzbina) {
        this.id = id;
        this.period = period;
        this.narudzbina = narudzbina;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getPeriod() { return period; }
    public void setPeriod(Integer period) { this.period = period; }

    public Narudzbina getNarudzbina() { return narudzbina; }
    public void setNarudzbina(Narudzbina narudzbina) { this.narudzbina = narudzbina; }
}