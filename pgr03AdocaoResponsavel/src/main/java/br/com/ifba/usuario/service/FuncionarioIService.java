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
// Interface que define o contrato para os serviços de Funcionario.
public interface FuncionarioIService {

    Funcionario save(Funcionario funcionario);

    Funcionario update(Funcionario funcionario);

    void delete(Funcionario funcionario);

    List<Funcionario> findAll();

    Optional<Funcionario> findById(Long id);

    // Método para buscar funcionários por parte do nome.
    List<Funcionario> findByNomeContaining(String nome);
}
