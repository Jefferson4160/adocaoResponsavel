/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.usuario.service;

import br.com.ifba.usuario.entity.Usuario; 
import java.util.List;
import java.util.Optional;

/**
 * Interface de Serviço para a entidade base Usuario.
 * @author Jefferson S
 */
// Interface que define os métodos que o serviço de Usuario deve oferecer.
public interface UsuarioIService {

    List<Usuario> findAll();
    
    Optional<Usuario> findById(Long id);
    
    // Método para buscar usuários por parte do nome, ignorando a diferença entre maiúsculas e minúsculas.
    List<Usuario> findByNomeContaining(String nome);
    
    // Método para buscar um usuário por um CPF específico, retornando um Optional para segurança.
    Optional<Usuario> findByCpf(String cpf);
    
    // Método genérico para salvar ou atualizar um usuário.
    Usuario save(Usuario usuario);
    
    // Método para deletar um usuário passando o objeto.
    void delete(Usuario usuario);
}
