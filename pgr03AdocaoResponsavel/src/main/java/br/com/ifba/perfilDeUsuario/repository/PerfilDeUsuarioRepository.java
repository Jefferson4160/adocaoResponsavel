/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.perfilDeUsuario.repository;

import br.com.ifba.perfilDeUsuario.entity.PerfilDeUsuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jefferson S
 */
@Repository
public interface PerfilDeUsuarioRepository extends JpaRepository<PerfilDeUsuario, Long> {

    List<PerfilDeUsuario> findByNomeDoPerfilContainingIgnoreCase(String nomeDoPerfil);
    
    Optional<PerfilDeUsuario> findByNomeDoPerfil(String nomeDoPerfil);
}
