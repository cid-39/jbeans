package com.njt.jbeans.controller;

import com.njt.jbeans.dto.NarudzbinaRequestDTO;
import com.njt.jbeans.model.Narudzbina;
import com.njt.jbeans.service.NarudzbinaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.core.Authentication;
/**
 *
 * @author cid
 */
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/narudzbina")
public class NarudzbinaController {

    private final NarudzbinaService narudzbinaService;

    public NarudzbinaController(NarudzbinaService narudzbinaService) {
        this.narudzbinaService = narudzbinaService;
    }

    @GetMapping("/getall")
    public List<Narudzbina> getAll() {
        return narudzbinaService.getAllNarudzbine();
    }

    @GetMapping("/moja")
    public List<Narudzbina> getMoja(Authentication authentication) {
        String email = authentication.getName();
        return narudzbinaService.getMojeNarudzbine(email);
    }

    @GetMapping("/get")
    public Narudzbina getById(@RequestParam int id) {
        return narudzbinaService.getNarudzbinaById(id);
    }

    @PostMapping("/create")
    public Narudzbina create(@Valid @RequestBody NarudzbinaRequestDTO dto, Authentication authentication) {
        String email = authentication.getName();
        return narudzbinaService.createNarudzbina(dto, email);
    }

    @PostMapping("/update")
    public Narudzbina update(@RequestParam int id, @RequestBody Narudzbina narudzbina) {
        return narudzbinaService.updateNarudzbina(id, narudzbina);
    }
}