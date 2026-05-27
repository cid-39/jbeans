package com.njt.jbeans.repository;

import com.njt.jbeans.model.Klijent;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mihdjo
 */
@Repository
public interface KlijentRepository extends JpaRepository<Klijent, Integer> {
    @Query("SELECT k FROM Klijent k WHERE k.email = :email")
    Optional<Klijent> pronadjiPoEmailu(@Param("email") String email);

    // Nova metoda za direktan upis u bazu koji zaobilazi SMRDLJIVI HIBERNATE KES 
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "INSERT INTO klijent (id, adresa) VALUES (:id, :adresa)", nativeQuery = true)
    void ubaciKlijenta(@Param("id") Integer id, @Param("adresa") String adresa);
}
