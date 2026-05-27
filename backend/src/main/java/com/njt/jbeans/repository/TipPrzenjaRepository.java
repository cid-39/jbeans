package com.njt.jbeans.repository;

import com.njt.jbeans.dto.analitika.PrzenjeStatistika;
import com.njt.jbeans.model.TipPrzenja;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author mihdjo
 */
@Repository
public interface TipPrzenjaRepository extends JpaRepository<TipPrzenja, Integer> {

    // Spring Data automatski generiše SQL: SELECT * FROM tip_przenja WHERE naziv = ?
    Optional<TipPrzenja> findByNaziv(String naziv);

    @Query("SELECT tp.naziv AS naziv, SUM(sn.kolicina) AS ukupnaKolicina "
            + "FROM StavkaNarudzbine sn "
            + "JOIN sn.proizvod p "
            + "JOIN p.tipPrzenja tp "
            + "GROUP BY tp.id, tp.naziv "
            + "ORDER BY ukupnaKolicina DESC")
    List<PrzenjeStatistika> dobijTrendovePrzenja();
}
