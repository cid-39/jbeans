package com.njt.jbeans.repository;

import com.njt.jbeans.model.Korisnik;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mihdjo
 */
@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {

    java.util.Optional<Korisnik> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT k.id FROM Korisnik k WHERE k.email = :email")
    Optional<Integer> pronadjiIdPoEmailu(@Param("email") String email);
}
