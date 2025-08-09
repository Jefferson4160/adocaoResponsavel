/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.perfilDeUsuario.service;

import br.com.ifba.perfilDeUsuario.entity.PerfilDeUsuario;
import br.com.ifba.perfilDeUsuario.repository.PerfilDeUsuarioRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jefferson S
 */
@Service // Anotação para o Spring gerenciar esta classe como um serviço.
@RequiredArgsConstructor // Gera o construtor para injeção de dependência.
@Slf4j // Facilita o uso de logs.
public class PerfilDeUsuarioService implements PerfilDeUsuarioIService {

    // Injeção de dependência do repositório para acesso ao banco de dados.
    private final PerfilDeUsuarioRepository perfilDeUsuarioRepository;
    
    @Override
    public PerfilDeUsuario save(PerfilDeUsuario perfil) {
        log.info("Salvando perfil de usuário: {}", perfil.getNomeDoPerfil());
        // Adicionar validações de regra de negócio aqui, se necessário.
        try {
            return perfilDeUsuarioRepository.save(perfil);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao salvar perfil de usuário: " + e.getMessage(), e);
        }
    }

    @Override
    public PerfilDeUsuario update(PerfilDeUsuario perfil) {
        log.info("Atualizando perfil de usuário: {}", perfil.getId());
        // Validações de regra de negócio.
        try {
            return perfilDeUsuarioRepository.save(perfil);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao atualizar perfil de usuário: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) {
        log.info("Deletando perfil de usuário com ID: {}", id);
        try {
            perfilDeUsuarioRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao remover perfil de usuário: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<PerfilDeUsuario> findById(Long id) {
        log.info("Buscando perfil de usuário com ID: {}", id);
        try {
            return perfilDeUsuarioRepository.findById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao buscar perfil de usuário por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<PerfilDeUsuario> findAll() {
        log.info("Buscando todos os perfis de usuário.");
        try {
            return perfilDeUsuarioRepository.findAll();
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao listar todos os perfis de usuário: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<PerfilDeUsuario> findByNomeContaining(String nome) {
        log.info("Buscando perfis por nome: {}", nome);
        return perfilDeUsuarioRepository.findByNomeDoPerfilContainingIgnoreCase(nome);
    }
}