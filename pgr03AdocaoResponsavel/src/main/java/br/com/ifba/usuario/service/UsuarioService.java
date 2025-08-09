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
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implementação de Serviço para a entidade base Usuario.
 * @author Jefferson S
 */
@Service
@RequiredArgsConstructor
@Slf4j

public class UsuarioService implements UsuarioIService {

    // Injeção de dependência do repositório para acesso ao banco de dados.
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Override
    public List<Usuario> findAll() {
        try {
            // Retorna a lista completa de usuários.
            return usuarioRepository.findAll();
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro no service ao listar todos os usuários: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        // Validação básica do ID.
        if (id == null) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo para busca.");
        }
        try {
            // Busca o usuário por ID.
            return usuarioRepository.findById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro no service ao buscar usuário por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Usuario> findByNomeContaining(String nome) {
        // Valida se o nome não está vazio.
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome para busca não pode ser vazio.");
        }
        try {
            // Delega a busca por nome ao repositório. Ele sabe como buscar na entidade Pessoa.
            return usuarioRepository.findByPessoa_NomeContainingIgnoreCase(nome);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro no service ao buscar usuário por nome: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Usuario> findByCpf(String cpf) {
        // Valida se o CPF não está vazio.
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("O CPF para busca não pode ser vazio.");
        }
        try {
            // Delega a busca por CPF ao repositório. Ele busca na entidade Pessoa.
            return usuarioRepository.findByPessoa_Cpf(cpf);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro no service ao buscar usuário por CPF: " + e.getMessage(), e);
        }
    }

    @Override
    public Usuario save(Usuario usuario) {
        // Lógica de validação pode ser adicionada aqui.
        try {
            // Salva o usuário no banco de dados. Funciona para criação e atualização.
            return usuarioRepository.save(usuario);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro no service ao salvar ou atualizar o usuário: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Usuario usuario) {
        // Lógica de validação pode ser adicionada aqui.
        try {
            // Deleta o usuário.
            usuarioRepository.delete(usuario);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro no service ao deletar o usuário: " + e.getMessage(), e);
        }
    }
}
