package com.njt.jbeans.repository;

import com.njt.jbeans.model.Narudzbina;
import com.njt.jbeans.model.Pretplata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mihdjo
 */

@Repository
public interface PretplataRepository extends JpaRepository<Pretplata, Integer> {

    Pretplata findByNarudzbina(Narudzbina narudzbina);
}