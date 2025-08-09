/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.controller;

import br.com.ifba.funcionario.entity.Funcionario;
import br.com.ifba.usuario.service.FuncionarioIService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Controller // Anotação que marca a classe como um controlador do Spring.
@RequiredArgsConstructor // Gera o construtor para injeção de dependência do FuncionarioIService.
@Slf4j // Habilita o uso de logs na classe.
public class FuncionarioController implements FuncionarioIController {
    
    // Atributo final para injeção de dependência do serviço.
    private final FuncionarioIService funcionarioService;

    @Override
    public Funcionario salvarFuncionario(Funcionario funcionario) {
        log.info("Recebendo solicitação para salvar funcionário: {}", funcionario.getNome());
        try {
            // Delega a tarefa de salvar ao serviço.
            return funcionarioService.save(funcionario);
        } catch (RuntimeException e) {
            log.error("Erro ao salvar funcionário: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Funcionario atualizarFuncionario(Funcionario funcionario) {
        log.info("Recebendo solicitação para atualizar funcionário: {}", funcionario.getId());
        try {
            // Delega a atualização ao serviço.
            return funcionarioService.update(funcionario);
        } catch (RuntimeException e) {
            log.error("Erro ao atualizar funcionário: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void deletarFuncionario(Funcionario funcionario) {
        log.info("Recebendo solicitação para deletar funcionário com ID: {}", funcionario.getId());
        try {
            // Delega a exclusão ao serviço.
            funcionarioService.delete(funcionario);
        } catch (RuntimeException e) {
            log.error("Erro ao deletar funcionário: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<Funcionario> buscarFuncionarioPorId(Long id) {
        log.info("Recebendo solicitação para buscar funcionário com ID: {}", id);
        try {
            // Delega a busca ao serviço.
            return funcionarioService.findById(id);
        } catch (RuntimeException e) {
            log.error("Erro ao buscar funcionário por ID: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Funcionario> buscarTodosFuncionarios() {
        log.info("Recebendo solicitação para buscar todos os funcionários.");
        try {
            // Delega a busca de todos os funcionários ao serviço.
            return funcionarioService.findAll();
        } catch (RuntimeException e) {
            log.error("Erro ao buscar todos os funcionários: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Funcionario> buscarFuncionarioPorNome(String nome) {
        log.info("Recebendo solicitação para buscar funcionário por nome: {}", nome);
        try {
            // Delega a busca por nome ao serviço.
            return funcionarioService.findByNomeContaining(nome);
        } catch (RuntimeException e) {
            log.error("Erro ao buscar funcionário por nome: {}", e.getMessage());
            throw e;
        }
    }
}
