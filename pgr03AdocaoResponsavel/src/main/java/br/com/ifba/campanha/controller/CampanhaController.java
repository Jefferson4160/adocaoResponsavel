/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.campanha.controller;

/**
 *
 * @author luis2
 */

import br.com.ifba.campanha.domain.entity.Campanha;
import br.com.ifba.campanha.domain.service.ICampanhaService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Controlador para a entidade Campanha.
 * @author luis
 */
@Controller
public class CampanhaController implements ICampanhaController {

    @Autowired
    private ICampanhaService campanhaService;

    @Override
    public Campanha saveCampanha(Campanha campanha) {
        return campanhaService.saveCampanha(campanha);
    }

    @Override
    public Campanha updateCampanha(Campanha campanha) {
        return campanhaService.updateCampanha(campanha);
    }

    @Override
    public void deleteCampanha(Long id) {
        campanhaService.deleteCampanha(id);
    }

    @Override
    public Optional<Campanha> findById(Long id) {
        return campanhaService.findById(id);
    }

    @Override
    public List<Campanha> findAllCampanhas() {
        return campanhaService.findAllCampanhas();
    }
}