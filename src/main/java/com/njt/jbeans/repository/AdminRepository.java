package com.njt.jbeans.repository;

import com.njt.jbeans.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mihdjo
 */

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
