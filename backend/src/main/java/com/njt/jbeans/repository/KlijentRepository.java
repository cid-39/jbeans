package com.njt.jbeans.repository;

import com.njt.jbeans.model.Klijent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mihdjo
 */

@Repository
public interface KlijentRepository extends JpaRepository<Klijent, Integer> {
}