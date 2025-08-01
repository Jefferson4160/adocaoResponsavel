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
public interface UsuarioIService {
    List<Usuario> findAll();
    Optional<Usuario> findById(Long id);
    List<Usuario> findByNomeContaining(String nome); // Para busca genérica por nome
    Optional<Usuario> findByCpf(String cpf); // Para busca por CPF para identificação
    Usuario save(Usuario usuario); // Para salvar/atualizar
    void delete(Usuario usuario); // Para deletar
}
