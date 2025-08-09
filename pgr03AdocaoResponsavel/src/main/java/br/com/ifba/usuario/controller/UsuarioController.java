/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.controller;

import br.com.ifba.usuario.service.UsuarioIService;
import br.com.ifba.usuario.entity.Usuario;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;


@Controller // Anotação que marca a classe como um controlador do Spring.
@RequiredArgsConstructor // Gera o construtor para injeção de dependência do UsuarioIService.
@Slf4j // Habilita o uso de logs na classe.
public class UsuarioController implements UsuarioIController {
    
    // Atributo final para injeção de dependência do serviço.
    private final UsuarioIService usuarioService;

    @Override
    public Usuario salvarUsuario(Usuario usuario) {
        log.info("Recebendo solicitação para salvar usuário.");
        try {
            // Delega a tarefa de salvar ao serviço.
            return usuarioService.save(usuario);
        } catch (RuntimeException e) {
            log.error("Erro ao salvar usuário: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Usuario atualizarUsuario(Usuario usuario) {
        log.info("Recebendo solicitação para atualizar usuário.");
        try {
            // Delega a atualização ao serviço.
            return usuarioService.save(usuario);
        } catch (RuntimeException e) {
            log.error("Erro ao atualizar usuário: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void deletarUsuario(Usuario usuario) {
        log.info("Recebendo solicitação para deletar usuário.");
        try {
            // Delega a exclusão ao serviço.
            usuarioService.delete(usuario);
        } catch (RuntimeException e) {
            log.error("Erro ao deletar usuário: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        log.info("Recebendo solicitação para buscar usuário com ID: {}", id);
        try {
            // Delega a busca ao serviço.
            return usuarioService.findById(id);
        } catch (RuntimeException e) {
            log.error("Erro ao buscar usuário por ID: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Usuario> buscarTodosUsuarios() {
        log.info("Recebendo solicitação para buscar todos os usuários.");
        try {
            // Delega a busca de todos os usuários ao serviço.
            return usuarioService.findAll();
        } catch (RuntimeException e) {
            log.error("Erro ao buscar todos os usuários: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Usuario> buscarUsuarioPorNome(String nome) {
        log.info("Recebendo solicitação para buscar usuário por nome: {}", nome);
        try {
            // Delega a busca por nome ao serviço.
            return usuarioService.findByNomeContaining(nome);
        } catch (RuntimeException e) {
            log.error("Erro ao buscar usuário por nome: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<Usuario> buscarUsuarioPorCpf(String cpf) {
        log.info("Recebendo solicitação para buscar usuário por CPF: {}", cpf);
        try {
            // Delega a busca por CPF ao serviço.
            return usuarioService.findByCpf(cpf);
        } catch (RuntimeException e) {
            log.error("Erro ao buscar usuário por CPF: {}", e.getMessage());
            throw e;
        }
    }
}