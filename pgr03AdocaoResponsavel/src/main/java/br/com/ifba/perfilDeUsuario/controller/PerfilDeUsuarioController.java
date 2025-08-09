/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.perfilDeUsuario.controller;

import br.com.ifba.perfilDeUsuario.entity.PerfilDeUsuario;
import br.com.ifba.perfilDeUsuario.service.PerfilDeUsuarioIService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Jefferson S
 */
@Controller // Anotação para o Spring gerenciar esta classe como um controlador.
@RequiredArgsConstructor // Gera o construtor para injeção de dependência.
@Slf4j // Habilita o uso de logs na classe.
public class PerfilDeUsuarioController implements PerfilDeUsuarioIController {

    // Injeção de dependência do serviço.
    private final PerfilDeUsuarioIService perfilDeUsuarioService;

    @Override
    public PerfilDeUsuario salvarPerfil(PerfilDeUsuario perfil) {
        log.info("Recebendo solicitação para salvar perfil: {}", perfil.getNomeDoPerfil());
        try {
            // Delega a tarefa de salvar ao serviço.
            return perfilDeUsuarioService.save(perfil);
        } catch (RuntimeException e) {
            log.error("Erro ao salvar perfil: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public PerfilDeUsuario atualizarPerfil(PerfilDeUsuario perfil) {
        log.info("Recebendo solicitação para atualizar perfil com ID: {}", perfil.getId());
        try {
            // Delega a atualização ao serviço.
            return perfilDeUsuarioService.update(perfil);
        } catch (RuntimeException e) {
            log.error("Erro ao atualizar perfil: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void deletarPerfil(Long id) {
        log.info("Recebendo solicitação para deletar perfil com ID: {}", id);
        try {
            // Delega a exclusão ao serviço.
            perfilDeUsuarioService.delete(id);
        } catch (RuntimeException e) {
            log.error("Erro ao deletar perfil: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<PerfilDeUsuario> buscarPerfilPorId(Long id) {
        log.info("Recebendo solicitação para buscar perfil com ID: {}", id);
        try {
            // Delega a busca ao serviço.
            return perfilDeUsuarioService.findById(id);
        } catch (RuntimeException e) {
            log.error("Erro ao buscar perfil por ID: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<PerfilDeUsuario> buscarTodosPerfis() {
        log.info("Recebendo solicitação para buscar todos os perfis.");
        try {
            // Delega a busca de todos os perfis ao serviço.
            return perfilDeUsuarioService.findAll();
        } catch (RuntimeException e) {
            log.error("Erro ao buscar todos os perfis: {}", e.getMessage());
            throw e;
        }
    }
    
    @Override
    public List<PerfilDeUsuario> buscarPorNome(String nome) {
        log.info("Recebendo solicitação para buscar perfis por nome: {}", nome);
        try {
            return perfilDeUsuarioService.findByNomeContaining(nome); // Chama o serviço
        } catch (RuntimeException e) {
            log.error("Erro ao buscar perfis por nome: {}", e.getMessage());
            throw e;
        }
    }
}