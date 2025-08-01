/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.usuario.controller;

import br.com.ifba.usuario.entity.Usuario;
import java.util.List;
import java.util.Optional;

/**
 * Interface que define o contrato da camada de Controle para operações de Usuários.
 * @author Jefferson S
 */
public interface UsuarioIController {

   
    List<Usuario> findAll(); 
    
    Optional<Usuario> findById(Long id); 
    
    List<Usuario> findByNomeContaining(String nome); 
    
    Optional<Usuario> findByCpf(String cpf);


}
