/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.njt.jbeans.service;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.HashMap;

/**
 *
 * @author cid
 */
@Service
public class DostavaIAnalitikaService {
    public Object updateIshodDostave(Long id, String ishod) {
        return "Dostava " + id + " evidentirana sa ishodom: " + ishod;
    }

    // koji object ce tacno biti povratna vrednosT??
    public Object getGlobalnaAnalitika() {
        return "mockAnalitika";
    }
}
