package com.njt.jbeans.repository;

import com.njt.jbeans.model.TipPrzenja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author mihdjo
 */

@Repository
public interface TipPrzenjaRepository extends JpaRepository<TipPrzenja, Integer> {
    // Spring Data automatski generiše SQL: SELECT * FROM tip_przenja WHERE naziv = ?
    Optional<TipPrzenja> findByNaziv(String naziv);
}