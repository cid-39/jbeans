package com.njt.jbeans.repository;

import com.njt.jbeans.dto.analitika.ZrnaStatistika;
import com.njt.jbeans.model.Dobavljac;
import com.njt.jbeans.model.SirovaZrna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

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

    @Query("SELECT z.naziv AS naziv, SUM(sn.kolicina) AS ukupnaKolicina "
            + "FROM StavkaNarudzbine sn "
            + "JOIN sn.proizvod p "
            + "JOIN p.zrna z "
            + "GROUP BY z.id, z.naziv "
            + "ORDER BY ukupnaKolicina DESC")
    List<ZrnaStatistika> dobijNajpopularnijaZrna();
}
