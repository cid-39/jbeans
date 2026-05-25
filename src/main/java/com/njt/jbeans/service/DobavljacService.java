package com.njt.jbeans.service;

import com.njt.jbeans.model.Dobavljac;
import com.njt.jbeans.repository.DobavljacRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DobavljacService {

    private final DobavljacRepository dobavljacRepository;

    public DobavljacService(DobavljacRepository dobavljacRepository) {
        this.dobavljacRepository = dobavljacRepository;
    }

    public List<Dobavljac> getAllDobavljaci() {
        return dobavljacRepository.findAll();
    }

    public Dobavljac getDobavljacById(String pib) {
        return dobavljacRepository.findById(pib).orElse(null);
    }

    public Dobavljac createDobavljac(Dobavljac dobavljac) {
        return dobavljacRepository.save(dobavljac);
    }

    public Dobavljac updateDobavljac(String pib, Dobavljac dobavljac) {
        if (dobavljacRepository.existsById(pib)) {
            return dobavljacRepository.save(dobavljac);
        }
        return null;
    }

    public boolean removeDobavljac(String pib) {
        if (dobavljacRepository.existsById(pib)) {
            dobavljacRepository.deleteById(pib);
            return true;
        }
        return false;
    }
}