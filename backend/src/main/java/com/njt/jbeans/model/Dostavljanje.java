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
import java.time.LocalDateTime;
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
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "registracija_vozila")
    @OnDelete(action = OnDeleteAction.RESTRICT) 
    private Vozilo vozilo;
    
    @Column(name = "datum_dostave", nullable = false, updatable = false)
    private LocalDateTime datumDostave;
    
    @Column(name = "status", length = 50)
    private String status;

    public Dostavljanje() {
    }

    public Dostavljanje(Integer id, Vozilo vozilo, LocalDateTime datumDostave, String status) {
        this.id = id;
        this.vozilo = vozilo;
        this.datumDostave = datumDostave;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDatumDostave() {
        return datumDostave;
    }

    public void setDatumDostave(LocalDateTime datumDostave) {
        this.datumDostave = datumDostave;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}