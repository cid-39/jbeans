package com.njt.jbeans.repository;

import com.njt.jbeans.dto.analitika.DostavaStatistika;
import com.njt.jbeans.model.Dostavljanje;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mihdjo
 */
@Repository
public interface DostavljanjeRepository extends JpaRepository<Dostavljanje, Integer> {
    Optional<Dostavljanje> findByDatumDostaveAndStatus(LocalDate datum, String status);

    @Query("SELECT d.datumDostave AS datum, COUNT(n.id) AS brojDostava "
            + "FROM Narudzbina n "
            + "JOIN n.dostavljanje d "
            + "GROUP BY d.datumDostave "
            + "ORDER BY d.datumDostave ASC")
    List<DostavaStatistika> dobijOpterecenostDostave();
}
