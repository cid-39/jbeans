package com.njt.jbeans.repository;

import com.njt.jbeans.model.TipVozila;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author mihdjo
 */

@Repository
public interface TipVozilaRepository extends JpaRepository<TipVozila, Integer> {
    Optional<TipVozila> findByNaziv(String naziv);
}