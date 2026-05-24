package com.njt.jbeans.repository;

import com.njt.jbeans.model.Dobavljac;
import com.njt.jbeans.model.SirovaZrna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 *
 * @author mihdjo
 */

@Repository
public interface SirovaZrnaRepository extends JpaRepository<SirovaZrna, Integer> {
    
    // Pronađi sva zrna koje isporučuje određeni dobavljač
    List<SirovaZrna> findByDobavljac(Dobavljac dobavljac);
    
    // Pronađi zrna po nazivu (korisno za pretragu na lageru)
    List<SirovaZrna> findByNazivContainingIgnoreCase(String naziv);
}