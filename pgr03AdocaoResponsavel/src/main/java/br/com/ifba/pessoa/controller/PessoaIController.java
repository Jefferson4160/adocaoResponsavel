/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.pessoa.controller;

import br.com.ifba.pessoa.entity.Pessoa;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Jefferson S
 */
// Interface que define o contrato do controlador para as operações de Pessoa.
public interface PessoaIController {
    
    // Método para salvar uma nova pessoa.
    Pessoa salvarPessoa(Pessoa pessoa);
    
    // Método para atualizar uma pessoa existente.
    Pessoa atualizarPessoa(Pessoa pessoa);
    
    // Método para deletar uma pessoa pelo seu ID.
    void deletarPessoa(Long id);
    
    // Método para buscar uma pessoa pelo seu ID.
    Optional<Pessoa> buscarPessoaPorId(Long id);
    
    // Método para buscar todas as pessoas.
    List<Pessoa> buscarTodasPessoas();
}
