/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.perfilDeUsuario.service;

import br.com.ifba.perfilDeUsuario.entity.PerfilDeUsuario;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Jefferson S
 */
// Interface que define o contrato para os serviços de PerfilDeUsuario.
public interface PerfilDeUsuarioIService {
    
    PerfilDeUsuario save(PerfilDeUsuario perfil);
    
    PerfilDeUsuario update(PerfilDeUsuario perfil);
    
    void delete(Long id);
    
    Optional<PerfilDeUsuario> findById(Long id);
    
    List<PerfilDeUsuario> findAll();
    
    List<PerfilDeUsuario> findByNomeContaining(String nome);
}
