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
import java.util.List;
import java.util.Optional;

/**
 * Interface que define o contrato do controlador para Campanha.
 * @author luis
 */
public interface ICampanhaController {
    Campanha saveCampanha(Campanha campanha);
    Campanha updateCampanha(Campanha campanha);
    void deleteCampanha(Long id);
    Optional<Campanha> findById(Long id);
    List<Campanha> findAllCampanhas();
}
