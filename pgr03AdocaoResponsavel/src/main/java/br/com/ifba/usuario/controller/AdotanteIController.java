/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.usuario.controller;

import br.com.ifba.adotante.entity.Adotante;
import java.util.List;
import java.util.Optional;

public interface AdotanteIController {

    List<Adotante> findAll();
    
    Optional<Adotante> findById(Long id);
    
    List<Adotante> findByNomeContaining(String nome);
    
    // Métodos para operações de CRUD
    Adotante save(Adotante adotante);
    
    Adotante update(Adotante adotante);
    
    void delete(Long id);
}
