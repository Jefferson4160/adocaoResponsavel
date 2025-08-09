/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.pessoa.controller;

import br.com.ifba.pessoa.service.PessoaIService;
import br.com.ifba.pessoa.entity.Pessoa;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Jefferson S
 */
@Controller // Anotação que marca a classe como um controlador do Spring.
@RequiredArgsConstructor // Gera o construtor para injeção de dependência do PessoaIService.
@Slf4j // Habilita o uso de logs na classe.
public class PessoaController implements PessoaIController {

    // O atributo final para injeção de dependência do serviço.
    private final PessoaIService pessoaService;

    @Override
    public Pessoa salvarPessoa(Pessoa pessoa) {
        log.info("Recebendo solicitação para salvar pessoa: {}", pessoa.getNome());
        try {
            // Delega a tarefa de salvar ao serviço.
            return pessoaService.save(pessoa);
        } catch (RuntimeException e) {
            log.error("Erro ao salvar pessoa: {}", e.getMessage());
            // A exceção pode ser relançada ou tratada de forma a exibir uma mensagem na tela.
            throw e;
        }
    }

    @Override
    public Pessoa atualizarPessoa(Pessoa pessoa) {
        log.info("Recebendo solicitação para atualizar pessoa: {}", pessoa.getId());
        try {
            // Delega a atualização ao serviço.
            return pessoaService.update(pessoa);
        } catch (RuntimeException e) {
            log.error("Erro ao atualizar pessoa: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void deletarPessoa(Long id) {
        log.info("Recebendo solicitação para deletar pessoa com ID: {}", id);
        try {
            // Delega a exclusão ao serviço.
            pessoaService.delete(id);
        } catch (RuntimeException e) {
            log.error("Erro ao deletar pessoa: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<Pessoa> buscarPessoaPorId(Long id) {
        log.info("Recebendo solicitação para buscar pessoa com ID: {}", id);
        try {
            // Delega a busca ao serviço.
            return pessoaService.findById(id);
        } catch (RuntimeException e) {
            log.error("Erro ao buscar pessoa: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Pessoa> buscarTodasPessoas() {
        log.info("Recebendo solicitação para buscar todas as pessoas.");
        try {
            // Delega a busca de todas as pessoas ao serviço.
            return pessoaService.findAll();
        } catch (RuntimeException e) {
            log.error("Erro ao buscar todas as pessoas: {}", e.getMessage());
            throw e;
        }
    }
}