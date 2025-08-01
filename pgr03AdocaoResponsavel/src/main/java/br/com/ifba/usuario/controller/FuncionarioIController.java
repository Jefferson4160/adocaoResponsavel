/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.usuario.controller;

import br.com.ifba.funcionario.entity.Funcionario;
import java.util.List;
import java.util.Optional;

public interface FuncionarioIController {

    List<Funcionario> findAll();
    
    Optional<Funcionario> findById(Long id);
    
    List<Funcionario> findByNomeContaining(String nome);
    
    // Métodos para operações de CRUD
    Funcionario save(Funcionario funcionario);
    
    Funcionario update(Funcionario funcionario);
    
    void delete(Long id); 
}
