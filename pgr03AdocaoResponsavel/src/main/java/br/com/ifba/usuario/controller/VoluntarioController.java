/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.controller;

import br.com.ifba.usuario.service.VoluntarioIService;
import br.com.ifba.voluntario.entity.Voluntario;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Controller // Anotação que marca a classe como um controlador do Spring.
@RequiredArgsConstructor // Gera o construtor para injeção de dependência do VoluntarioIService.
@Slf4j // Habilita o uso de logs na classe.
public class VoluntarioController implements VoluntarioIController {

    // Atributo final para injeção de dependência do serviço.
    private final VoluntarioIService voluntarioService;

    @Override
    public Voluntario salvarVoluntario(Voluntario voluntario) {
        log.info("Recebendo solicitação para salvar voluntário: {}", voluntario.getNome());
        try {
            // Delega a tarefa de salvar ao serviço.
            return voluntarioService.save(voluntario);
        } catch (RuntimeException e) {
            log.error("Erro ao salvar voluntário: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Voluntario atualizarVoluntario(Voluntario voluntario) {
        log.info("Recebendo solicitação para atualizar voluntário: {}", voluntario.getId());
        try {
            // Delega a atualização ao serviço.
            return voluntarioService.update(voluntario);
        } catch (RuntimeException e) {
            log.error("Erro ao atualizar voluntário: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void deletarVoluntario(Voluntario voluntario) {
        log.info("Recebendo solicitação para deletar voluntário com ID: {}", voluntario.getId());
        try {
            // Delega a exclusão ao serviço.
            voluntarioService.delete(voluntario);
        } catch (RuntimeException e) {
            log.error("Erro ao deletar voluntário: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<Voluntario> buscarVoluntarioPorId(Long id) {
        log.info("Recebendo solicitação para buscar voluntário com ID: {}", id);
        try {
            // Delega a busca ao serviço.
            return voluntarioService.findById(id);
        } catch (RuntimeException e) {
            log.error("Erro ao buscar voluntário por ID: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Voluntario> buscarTodosVoluntarios() {
        log.info("Recebendo solicitação para buscar todos os voluntários.");
        try {
            // Delega a busca de todos os voluntários ao serviço.
            return voluntarioService.findAll();
        } catch (RuntimeException e) {
            log.error("Erro ao buscar todos os voluntários: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Voluntario> buscarVoluntarioPorNome(String nome) {
        log.info("Recebendo solicitação para buscar voluntário por nome: {}", nome);
        try {
            // Delega a busca por nome ao serviço.
            return voluntarioService.findByNomeContaining(nome);
        } catch (RuntimeException e) {
            log.error("Erro ao buscar voluntário por nome: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Voluntario> buscarVoluntarioPorAreaAtuacao(String areaAtuacao) {
        log.info("Recebendo solicitação para buscar voluntário por área de atuação: {}", areaAtuacao);
        try {
            // Delega a busca por área de atuação ao serviço.
            return voluntarioService.findByAreaAtuacao(areaAtuacao);
        } catch (RuntimeException e) {
            log.error("Erro ao buscar voluntário por área de atuação: {}", e.getMessage());
            throw e;
        }
    }
}
