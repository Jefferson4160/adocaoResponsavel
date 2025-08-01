/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.usuario.repository;

import br.com.ifba.adotante.entity.Adotante;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 

/**
 * Repositório para a entidade Adotante.
 * @author Jefferson S
 */
@Repository
public interface AdotanteRepository extends JpaRepository<Adotante, Long> {

    public List<Adotante> findByNomeContainingIgnoreCase(String nome);
    
}
