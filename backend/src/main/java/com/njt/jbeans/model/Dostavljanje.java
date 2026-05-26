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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne
    @JoinColumn(name = "registracija_vozila", nullable = false)
    @OnDelete(action = OnDeleteAction.RESTRICT) 
    private Vozilo vozilo;
    
    @Temporal(TemporalType.TIMESTAMP) 
    @Column(name = "datum_dostave")
    private Date datumDostave;
    
    @Column(name = "status", length = 50)
    private String status;

    public Dostavljanje() {
    }

    public Dostavljanje(int id, Vozilo vozilo, Date datumDostave, String status) {
        this.id = id;
        this.vozilo = vozilo;
        this.datumDostave = datumDostave;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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