package com.njt.jbeans.repository;

import com.njt.jbeans.model.Dostavljanje;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mihdjo
 */

@Repository
public interface DostavljanjeRepository extends JpaRepository<Dostavljanje, Integer> {
    Optional<Dostavljanje> findByDatumDostaveAndStatus(LocalDate datum, String status);
}