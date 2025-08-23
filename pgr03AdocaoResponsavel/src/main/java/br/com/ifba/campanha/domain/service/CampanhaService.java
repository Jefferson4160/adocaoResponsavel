/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.campanha.domain.service;

/**
 *
 * @author luis2
 */

import br.com.ifba.campanha.domain.entity.Campanha;
import br.com.ifba.campanha.domain.repository.ICampanhaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementação da lógica de negócio para Campanha.
 * @author luis
 */
@Service
public class CampanhaService implements ICampanhaService {

    @Autowired
    private ICampanhaRepository campanhaRepository;

    @Override
    public Campanha saveCampanha(Campanha campanha) {
        if (campanha == null) {
            throw new IllegalArgumentException("A campanha não pode ser nula.");
        }
        return campanhaRepository.save(campanha);
    }

    @Override
    public Campanha updateCampanha(Campanha campanha) {
        if (campanha == null || campanha.getId() == null) {
            throw new IllegalArgumentException("A campanha e seu ID não podem ser nulos para atualização.");
        }
        if (!campanhaRepository.existsById(campanha.getId())) {
            throw new RuntimeException("Campanha não encontrada para atualização.");
        }
        return campanhaRepository.save(campanha);
    }

    @Override
    public void deleteCampanha(Long id) {
        if (!campanhaRepository.existsById(id)) {
            throw new RuntimeException("Campanha com ID " + id + " não encontrada para exclusão.");
        }
        campanhaRepository.deleteById(id);
    }

    @Override
    public Optional<Campanha> findById(Long id) {
        return campanhaRepository.findById(id);
    }

    @Override
    public List<Campanha> findAllCampanhas() {
        return campanhaRepository.findAll();
    }
}