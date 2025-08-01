/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.usuario.controller;

import br.com.ifba.voluntario.entity.Voluntario;
import java.util.List;
import java.util.Optional;

public interface VoluntarioIController {

    List<Voluntario> findAll();
    
    Optional<Voluntario> findById(Long id);
    
    List<Voluntario> findByNomeContaining(String nome);

    List<Voluntario> findByAreaAtuacao(String areaAtuacao); // Método específico para Voluntário
    
    // Métodos para operações de CRUD
    Voluntario save(Voluntario voluntario);
    
    Voluntario update(Voluntario voluntario);
    
    void delete(Long id);
}
