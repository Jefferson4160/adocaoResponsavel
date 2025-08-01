/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.usuario.service;

import br.com.ifba.funcionario.entity.Funcionario;
import java.util.List;
import java.util.Optional; 

/**
 * Interface de Serviço para a entidade Funcionario.
 * @author Jefferson S
 */
public interface FuncionarioIService {
    Funcionario save(Funcionario funcionario);
    Funcionario update(Funcionario funcionario);
    void delete(Funcionario funcionario);
    List<Funcionario> findAll();
    Optional<Funcionario> findById(Long id);
    List<Funcionario> findByNomeContaining(String nome);
}
