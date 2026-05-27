package com.njt.jbeans.repository;

import com.njt.jbeans.dto.analitika.DobavljacStatistika;
import com.njt.jbeans.model.Dobavljac;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mihdjo
 */
@Repository
public interface DobavljacRepository extends JpaRepository<Dobavljac, String> {
    @Query("SELECT d.naziv AS naziv, SUM(sn.cena) AS ukupanPrihod "
            + "FROM StavkaNarudzbine sn "
            + "JOIN sn.proizvod p "
            + "JOIN p.zrna z "
            + "JOIN z.dobavljac d "
            + "GROUP BY d.pib, d.naziv "
            + "ORDER BY ukupanPrihod DESC")
    List<DobavljacStatistika> dobijTopDobavljace();
}
