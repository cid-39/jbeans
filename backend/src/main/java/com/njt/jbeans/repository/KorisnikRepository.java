package com.njt.jbeans.repository;

import com.njt.jbeans.model.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mihdjo
 */

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {
    java.util.Optional<Korisnik> findByEmail(String email);
    boolean existsByEmail(String email);
}