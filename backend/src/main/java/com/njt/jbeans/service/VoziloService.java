/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.service;

import com.njt.jbeans.model.Vozilo;
import com.njt.jbeans.repository.VoziloRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author cid
 */
@Service
public class VoziloService {
    
    private final VoziloRepository voziloRepository;

    public VoziloService(VoziloRepository voziloRepository) {
        this.voziloRepository = voziloRepository;
    }
    
    
    
    public List<Vozilo> getAllVozilo() {
        return voziloRepository.findAll();
    }
    
}
