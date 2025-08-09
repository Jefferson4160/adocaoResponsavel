/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.usuario.controller;

import br.com.ifba.funcionario.entity.Funcionario;
import java.util.List;
import java.util.Optional;

// Interface que define o contrato do controlador para as operações de Funcionário.
public interface FuncionarioIController {
    
    // Método para salvar um novo funcionário.
    Funcionario salvarFuncionario(Funcionario funcionario);

    // Método para atualizar um funcionário existente.
    Funcionario atualizarFuncionario(Funcionario funcionario);

    // Método para deletar um funcionário.
    void deletarFuncionario(Funcionario funcionario);

    // Método para buscar um funcionário pelo seu ID.
    Optional<Funcionario> buscarFuncionarioPorId(Long id);

    // Método para buscar todos os funcionários.
    List<Funcionario> buscarTodosFuncionarios();

    // Método para buscar funcionários por parte do nome da pessoa associada.
    List<Funcionario> buscarFuncionarioPorNome(String nome);
}
