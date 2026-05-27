package com.njt.jbeans.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 *
 * @author cid
 */
@Entity
@Table(name = "dobavljac")
public class Dobavljac {
    
    @Id
    @Size(min = 9, max = 9, message= "PIB mora imati tacno 9 cifara!")
    @Pattern(regexp = "^[0-9]+$", message = "PIB sme sadrzati samo brojeve!")
    @Column(name = "pib", length = 9) 
    private String pib;
    
    @Column(name = "naziv", nullable = false) 
    private String naziv;
    
    @Pattern(regexp = "^[0-9]+$", message = "Telefon sme sadrzati samo brojeve!")
    @Column(name = "broj_telefona", nullable = false)
    private String brojTelefona;

    public Dobavljac() {
    }

    public Dobavljac(String pib, String naziv, String brojTelefona) {
        this.pib = pib;
        this.naziv = naziv;
        this.brojTelefona = brojTelefona;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }
}