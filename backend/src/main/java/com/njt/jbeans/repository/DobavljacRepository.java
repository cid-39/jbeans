package com.njt.jbeans.repository;

import com.njt.jbeans.model.Dobavljac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mihdjo
 */

@Repository
public interface DobavljacRepository extends JpaRepository<Dobavljac, String> {
}