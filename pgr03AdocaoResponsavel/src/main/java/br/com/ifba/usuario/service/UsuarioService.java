/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.service;

import br.com.ifba.usuario.entity.Usuario; 
import br.com.ifba.usuario.repository.UsuarioRepository; 
import br.com.ifba.infrastructure.util.StringUtil; 
import lombok.RequiredArgsConstructor; 
import lombok.extern.slf4j.Slf4j;     
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Transactional; 
import java.util.List;
import java.util.Optional;

/**
 * Implementação de Serviço para a entidade base Usuario.
 * @author Jefferson S
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService implements UsuarioIService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> findAll() {
        try {
            log.debug("Listando todos os usuários...");
            return usuarioRepository.findAll();
        } catch (RuntimeException e) {
            log.error("Erro ao listar todos os usuários: {}", e.getMessage(), e);
            throw new RuntimeException("Falha ao listar todos os usuários", e);
        }
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo para busca.");
        }
        try {
            log.debug("Buscando usuário por ID: {}", id);
            return usuarioRepository.findById(id);
        } catch (RuntimeException e) {
            log.error("Erro ao buscar usuário por ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Falha ao buscar usuário por ID", e);
        }
    }

    @Override
    public List<Usuario> findByNomeContaining(String nome) {
        if (StringUtil.isNullOrEmpty(nome)) {
            return findAll(); // Retorna todos se a busca por nome for vazia
        }
        try {
            log.debug("Buscando usuários por nome: {}", nome);
            return usuarioRepository.findByNomeContainingIgnoreCase(nome); // Chama o método do repositório
        } catch (RuntimeException e) {
            log.error("Erro ao buscar usuários por nome '{}': {}", nome, e.getMessage(), e);
            throw new RuntimeException("Falha ao buscar usuários por nome", e);
        }
    }

    @Override
    public Optional<Usuario> findByCpf(String cpf) {
        if (StringUtil.isNullOrEmpty(cpf)) {
            throw new IllegalArgumentException("CPF não pode ser vazio para busca.");
        }
        try {
            log.debug("Buscando usuário por CPF: {}", cpf);
            return usuarioRepository.findByCpf(cpf);
        } catch (RuntimeException e) {
            log.error("Erro ao buscar usuário por CPF {}: {}", cpf, e.getMessage(), e);
            throw new RuntimeException("Falha ao buscar usuário por CPF", e);
        }
    }

    @Override
    @Transactional
    public Usuario save(Usuario usuario) {
        // Validação de usuário 
        if (usuario == null) {
            throw new RuntimeException("Dados do usuário não preenchidos.");
        }
        if (StringUtil.isNullOrEmpty(usuario.getNome())) {
            throw new IllegalArgumentException("Nome do usuário não pode ser vazio.");
        }
        if (StringUtil.isNullOrEmpty(usuario.getCpf())) {
            throw new IllegalArgumentException("CPF do usuário não pode ser vazio.");
        }
        // Valida CPF único
        if (usuario.getId() == null && findByCpf(usuario.getCpf()).isPresent()) {
            throw new RuntimeException("CPF já cadastrado.");
        }

        try {
            log.info("Salvando/Atualizando usuário: {}", usuario.getNome());
            // JpaRepository.save() lida com persistência (novo) e merge (existente)
            return usuarioRepository.save(usuario);
        } catch (RuntimeException e) {
            log.error("Erro ao salvar/atualizar usuário {}: {}", usuario.getNome(), e.getMessage(), e);
            throw new RuntimeException("Falha ao salvar/atualizar usuário", e);
        }
    }

    @Override
    @Transactional
    public void delete(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) {
            throw new IllegalArgumentException("Dados do usuário ou ID não preenchidos para remoção.");
        }
        // Verifica se o usuário existe antes de tentar remover
        Usuario usuarioExistente = usuarioRepository.findById(usuario.getId()).orElse(null);
        if (usuarioExistente == null) {
            throw new RuntimeException("Usuário com ID " + usuario.getId() + " não encontrado para remoção.");
        }

        try {
            log.info("Removendo usuário com ID: {}", usuario.getId());
            usuarioRepository.delete(usuarioExistente);
        } catch (RuntimeException e) {
            log.error("Erro ao remover usuário com ID {}: {}", usuario.getId(), e.getMessage(), e);
            throw new RuntimeException("Falha ao remover usuário", e);
        }
    }

    
}
