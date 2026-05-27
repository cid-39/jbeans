package com.njt.jbeans.repository;

import com.njt.jbeans.model.Klijent;
import com.njt.jbeans.model.Narudzbina;
import com.njt.jbeans.model.Pretplata;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mihdjo
 */

@Repository
public interface PretplataRepository extends JpaRepository<Pretplata, Integer> {
    // JPQL upit koji prolazi kroz listu narudzbina i trazi poklapanje za klijenta
    @Query("SELECT DISTINCT p FROM Pretplata p JOIN p.narudzbine n WHERE n.klijent = :klijent")
    List<Pretplata> findAllByKlijent(@Param("klijent") Klijent klijent);
}