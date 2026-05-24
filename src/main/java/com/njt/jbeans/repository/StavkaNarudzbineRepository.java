package com.njt.jbeans.repository;

import com.njt.jbeans.model.Narudzbina;
import com.njt.jbeans.model.StavkaNarudzbine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 *
 * @author mihdjo
 */

@Repository
public interface StavkaNarudzbineRepository extends JpaRepository<StavkaNarudzbine, Integer> {
    
    // Pronađi sve stavke za određenu narudžbinu
    List<StavkaNarudzbine> findByNarudzbina(Narudzbina narudzbina);
}