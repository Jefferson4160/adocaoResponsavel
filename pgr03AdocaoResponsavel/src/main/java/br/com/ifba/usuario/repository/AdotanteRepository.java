/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.usuario.repository;

import br.com.ifba.adotante.entity.Adotante;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository; 

/**
 * Repositório para a entidade Adotante.
 * @author Jefferson S
 */
@Repository
public interface AdotanteRepository extends JpaRepository<Adotante, Long> {

    List<Adotante> findByNomeContainingIgnoreCase(String nome);
    
    // Novo método para buscar denuncias de adotantes
    @Query("SELECT a FROM Adotante a WHERE a.id IN (SELECT d.denunciado.id FROM Denuncia d)")
    List<Adotante> findAdotantesComDenuncias();
}
