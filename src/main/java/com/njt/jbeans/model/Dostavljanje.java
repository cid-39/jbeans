package com.njt.jbeans.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

/**
 *
 * @author cid
 */
@Entity
@Table(name = "dostavljanje")
public class Dostavljanje {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "registracija_vozila", length = 20)
    private String registracijaVozila;
    
    @Temporal(TemporalType.TIMESTAMP) 
    @Column(name = "datum_dostave")
    private Date datumDostave;
    
    @Column(name = "status", length = 50)
    private String status;

    public Dostavljanje() {
    }

    public Dostavljanje(int id, String registracijaVozila, Date datumDostave, String status) {
        this.id = id;
        this.registracijaVozila = registracijaVozila;
        this.datumDostave = datumDostave;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegistracijaVozila() {
        return registracijaVozila;
    }

    public void setRegistracijaVozila(String registracijaVozila) {
        this.registracijaVozila = registracijaVozila;
    }

    public Date getDatumDostave() {
        return datumDostave;
    }

    public void setDatumDostave(Date datumDostave) {
        this.datumDostave = datumDostave;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}