/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.controller;

import br.com.ifba.voluntario.entity.Voluntario;
import br.com.ifba.usuario.service.VoluntarioIService; 
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; 

@Controller
public class VoluntarioController implements VoluntarioIController {

    @Autowired
    private VoluntarioIService voluntarioService;

    @Override
    public List<Voluntario> findAll() {
        return voluntarioService.findAll();
    }

    @Override
    public Optional<Voluntario> findById(Long id) {
        return voluntarioService.findById(id);
    }

    @Override
    public List<Voluntario> findByNomeContaining(String nome) {
        return voluntarioService.findByNomeContaining(nome);
    }

    @Override
    public List<Voluntario> findByAreaAtuacao(String areaAtuacao) {
        return voluntarioService.findByAreaAtuacao(areaAtuacao);
    }

    @Override
    public Voluntario save(Voluntario voluntario) {
        return voluntarioService.save(voluntario);
    }

    @Override
    public Voluntario update(Voluntario voluntario) {
        return voluntarioService.update(voluntario);
    }

    @Override
    public void delete(Long id) {
        Optional<Voluntario> voluntarioToDelete = voluntarioService.findById(id);
        if (voluntarioToDelete.isPresent()) {
            voluntarioService.delete(voluntarioToDelete.get());
        } else {
            // Lançar uma exceção ou lidar com o caso de voluntário não encontrado
            throw new RuntimeException("Voluntário com ID " + id + " não encontrado para remoção.");
        }
    }
}
