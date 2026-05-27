package com.njt.jbeans.repository;

import com.njt.jbeans.dto.DnevnoPrzenjeDTO;
import com.njt.jbeans.model.Proizvod;
import com.njt.jbeans.model.SirovaZrna;
import com.njt.jbeans.model.TipPrzenja;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author mihdjo
 */
@Repository
public interface ProizvodRepository extends JpaRepository<Proizvod, Integer> {

    // Pronađi sve proizvode od određenog tipa sirovih zrna
    List<Proizvod> findByZrna(SirovaZrna zrna);

    // Pronađi sve proizvode po tipu prženja
    List<Proizvod> findByTipPrzenja(TipPrzenja tipPrzenja);

    Optional<Proizvod> findByDatumPrzenjaAndZrnaIdAndTipPrzenjaId(LocalDate datum, Integer zrnaId, Integer tipPrzenjaId);

    @Query("SELECT new com.njt.jbeans.dto.DnevnoPrzenjeDTO(z.naziv, tp.naziv, p.kolicinaPrzena, p.opis) "
            + "FROM Proizvod p "
            + "JOIN p.zrna z "
            + "JOIN p.tipPrzenja tp "
            + "WHERE p.datumPrzenja = :datum")
    List<DnevnoPrzenjeDTO> pronadjiSveZaPrzenjeNaDan(@Param("datum") LocalDate datum);
}
