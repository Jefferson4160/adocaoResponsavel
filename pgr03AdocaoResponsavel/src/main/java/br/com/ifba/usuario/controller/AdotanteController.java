/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.controller;

import br.com.ifba.adotante.entity.Adotante;
import br.com.ifba.usuario.service.AdotanteIService; 
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; 

@Controller
public class AdotanteController implements AdotanteIController {

    @Autowired
    private AdotanteIService adotanteService;

    @Override
    public List<Adotante> findAll() {
        return adotanteService.findAll();
    }

    @Override
    public Optional<Adotante> findById(Long id) {
        return adotanteService.findById(id);
    }

    @Override
    public List<Adotante> findByNomeContaining(String nome) {
        return adotanteService.findByNomeContaining(nome);
    }

    @Override
    public Adotante save(Adotante adotante) {
        return adotanteService.save(adotante);
    }

    @Override
    public Adotante update(Adotante adotante) {
        return adotanteService.update(adotante);
    }

    @Override
    public void delete(Long id) {
        Optional<Adotante> adotanteToDelete = adotanteService.findById(id);
        if (adotanteToDelete.isPresent()) {
            adotanteService.delete(adotanteToDelete.get());
        } else {
            // Lançar uma exceção ou lidar com o caso de adotante não encontrado
            throw new RuntimeException("Adotante com ID " + id + " não encontrado para remoção.");
        }
    }
}
