package com.njt.jbeans.repository;

import com.njt.jbeans.dto.analitika.KlijentStatistika;
import com.njt.jbeans.model.Klijent;
import java.util.List;
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

    // Ovde radimo implicitni JOIN sa Korisnikom da izvučemo email
    @Query("SELECT ko.email AS email, SUM(n.ukupnaCena) AS ukupnaPotrosnja, COUNT(n.id) AS brojNarudzbina "
            + "FROM Narudzbina n "
            + "JOIN n.klijent kl "
            + "JOIN Korisnik ko ON kl.id = ko.id "
            + "GROUP BY kl.id, ko.email "
            + "ORDER BY ukupnaPotrosnja DESC")
    List<KlijentStatistika> dobijTopKlijente();
}
