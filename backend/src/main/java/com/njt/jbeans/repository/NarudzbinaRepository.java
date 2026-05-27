package com.njt.jbeans.repository;

import com.njt.jbeans.model.Dostavljanje;
import com.njt.jbeans.model.Narudzbina;
import com.njt.jbeans.model.Klijent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author mihdjo
 */
@Repository
public interface NarudzbinaRepository extends JpaRepository<Narudzbina, Integer> {

    // Spring Data JPA automatski generiše SQL upit na osnovu naziva metode!
    // Pronađi sve narudžbine određenog klijenta
    List<Narudzbina> findByKlijent(Klijent klijent);

    // Pronađi sve narudžbine koje imaju ili nemaju aktivnu pretplatu
    List<Narudzbina> findByPretplata(boolean pretplata);

    public List<Narudzbina> findByDostavljanje(Dostavljanje sacuvanoDostavljanje);

    // Prosečna vrednost korpe
    @Query("SELECT AVG(n.ukupnaCena) FROM Narudzbina n")
    Double dobijProsecnuVrednostKorpe();

    // Ukupan prihod od početka
    @Query("SELECT SUM(n.ukupnaCena) FROM Narudzbina n")
    Double dobijUkupanPrihod();
}
