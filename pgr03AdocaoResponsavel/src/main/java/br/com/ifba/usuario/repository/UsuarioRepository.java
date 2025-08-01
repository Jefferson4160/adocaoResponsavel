/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.usuario.repository;

import br.com.ifba.usuario.entity.Usuario; 
import java.util.List;
import java.util.Optional; 
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 

/**
 * Repositório para a entidade base Usuario.
 * @author Jefferson S
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // --- MÉTODOS DE BUSCA ---
    // Busca por nome
    List<Usuario> findByNomeContainingIgnoreCase(String nome);

    // Busca por CPF
    Optional<Usuario> findByCpf(String cpf);
}
