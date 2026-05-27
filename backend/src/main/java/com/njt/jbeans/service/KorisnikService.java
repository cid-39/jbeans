/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.service;

import com.njt.jbeans.model.Korisnik;
import com.njt.jbeans.repository.AdminRepository;
import com.njt.jbeans.repository.KorisnikRepository;
import com.njt.jbeans.security.JwtProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 *
 * @author cid
 */
@Service
public class KorisnikService {

    private final KorisnikRepository korisnikRepository;
    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;

    public KorisnikService(KorisnikRepository korisnikRepository,
            JwtProvider jwtProvider,
            BCryptPasswordEncoder passwordEncoder,
            AdminRepository adminRepository) {
        this.korisnikRepository = korisnikRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
    }

    public Korisnik registrujKorisnika(Korisnik korisnik) {
        if (korisnikRepository.existsByEmail(korisnik.getEmail())) {
            throw new RuntimeException("Korisnik sa ovim email-om već postoji!");
        }

        String hesiranaLozinka = passwordEncoder.encode(korisnik.getPassword());
        korisnik.setPassword(hesiranaLozinka);
        return korisnikRepository.save(korisnik);
    }

    public Map<String, String> login(Object loginRequest) {
        Map<String, String> podaci = (Map<String, String>) loginRequest;
        String email = podaci.get("email");
        String lozinka = podaci.get("password");

        Korisnik korisnik = korisnikRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Korisnik sa ovim email-om ne postoji!"));

        // BCrypt provera poklapanja lozinki
        if (passwordEncoder.matches(lozinka, korisnik.getPassword())) {
            String uloga = "KORISNIK"; // Podrazumevana uloga     
            // Proveravamo da li ID ovog korisnika postoji u admin tabeli
            if (adminRepository.existsById(korisnik.getId())) {
                uloga = "ADMIN";
            }

            // Generišemo token sa ispravnom ulogom
            String token = jwtProvider.generateToken(korisnik.getEmail(), uloga);

            // Vraćamo kompletne podatke nazad kontroleru
            return Map.of(
                    "token", token,
                    "uloga", uloga,
                    "username", korisnik.getUsername()
            );
        } else {
            throw new RuntimeException("Pogrešna lozinka!");
        }
    }
}
