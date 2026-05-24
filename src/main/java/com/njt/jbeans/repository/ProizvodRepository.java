package com.njt.jbeans.repository;

import com.njt.jbeans.model.Proizvod;
import com.njt.jbeans.model.SirovaZrna;
import com.njt.jbeans.model.TipPrzenja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 *
 * @author mihdjo
 */

@Repository
public interface ProizvodRepository extends JpaRepository<Proizvod, Integer> {
    
    // Pronađi sve proizvode od određenog tipa sirovih zrna
    List<Proizvod> findByZrna(SirovaZrna zrna);
    
    // Pronađi sve proizvode po tipu prženja
    List<Proizvod> findByTipPrzenja(TipPrzenja tipPrzenja);
}