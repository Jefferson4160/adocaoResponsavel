/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.controller;

import br.com.ifba.adotante.entity.Adotante;
import br.com.ifba.usuario.service.AdotanteIService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Controller // Anotação que marca a classe como um controlador do Spring.
@RequiredArgsConstructor // Gera o construtor para injeção de dependência do AdotanteIService.
@Slf4j // Habilita o uso de logs na classe.
public class AdotanteController implements AdotanteIController {
    
    // Atributo final para injeção de dependência do serviço.
    private final AdotanteIService adotanteService;

    @Override
    public Adotante salvarAdotante(Adotante adotante) {
        log.info("Recebendo solicitação para salvar adotante: {}", adotante.getNome());
        try {
            // Delega a tarefa de salvar ao serviço.
            return adotanteService.save(adotante);
        } catch (RuntimeException e) {
            log.error("Erro ao salvar adotante: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Adotante atualizarAdotante(Adotante adotante) {
        log.info("Recebendo solicitação para atualizar adotante: {}", adotante.getId());
        try {
            // Delega a atualização ao serviço.
            return adotanteService.update(adotante);
        } catch (RuntimeException e) {
            log.error("Erro ao atualizar adotante: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void deletarAdotante(Adotante adotante) {
        log.info("Recebendo solicitação para deletar adotante com ID: {}", adotante.getId());
        try {
            // Delega a exclusão ao serviço.
            adotanteService.delete(adotante);
        } catch (RuntimeException e) {
            log.error("Erro ao deletar adotante: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<Adotante> buscarAdotantePorId(Long id) {
        log.info("Recebendo solicitação para buscar adotante com ID: {}", id);
        try {
            // Delega a busca ao serviço.
            return adotanteService.findById(id);
        } catch (RuntimeException e) {
            log.error("Erro ao buscar adotante por ID: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Adotante> buscarTodosAdotantes() {
        log.info("Recebendo solicitação para buscar todos os adotantes.");
        try {
            // Delega a busca de todos os adotantes ao serviço.
            return adotanteService.findAll();
        } catch (RuntimeException e) {
            log.error("Erro ao buscar todos os adotantes: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Adotante> buscarAdotantePorNome(String nome) {
        log.info("Recebendo solicitação para buscar adotante por nome: {}", nome);
        try {
            // Delega a busca por nome ao serviço.
            return adotanteService.findByNomeContaining(nome);
        } catch (RuntimeException e) {
            log.error("Erro ao buscar adotante por nome: {}", e.getMessage());
            throw e;
        }
    }
    
    @Override
    public List<Adotante> buscarAdotantesComDenuncias() {
        log.info("Recebendo solicitação para buscar adotantes com denúncias.");
        try {
            // Delega a busca ao serviço. A implementação real está na camada de serviço.
            return adotanteService.findAdotantesComDenuncias();
        } catch (RuntimeException e) {
            log.error("Erro ao buscar adotantes com denúncias: {}", e.getMessage());
            throw e;
        }
    }
}