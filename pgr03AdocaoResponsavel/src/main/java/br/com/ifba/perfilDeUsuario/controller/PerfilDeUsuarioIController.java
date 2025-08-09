/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.perfilDeUsuario.controller;


import br.com.ifba.perfilDeUsuario.entity.PerfilDeUsuario;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Jefferson S
 */
public interface PerfilDeUsuarioIController {
    // Métodos de CRUD
    PerfilDeUsuario salvarPerfil(PerfilDeUsuario perfil);
    PerfilDeUsuario atualizarPerfil(PerfilDeUsuario perfil);
    void deletarPerfil(Long id);
    
    // Métodos de busca
    Optional<PerfilDeUsuario> buscarPerfilPorId(Long id);
    List<PerfilDeUsuario> buscarTodosPerfis();
    List<PerfilDeUsuario> buscarPorNome(String nome);
}
