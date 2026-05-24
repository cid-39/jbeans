package com.njt.jbeans.repository;

import com.njt.jbeans.model.TipVozila;
import com.njt.jbeans.model.Vozilo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 *
 * @author mihdjo
 */

@Repository
public interface VoziloRepository extends JpaRepository<Vozilo, String> {
    
    // Pronađi sva vozila određenog tipa
    List<Vozilo> findByTipVozila(TipVozila tipVozila);
}