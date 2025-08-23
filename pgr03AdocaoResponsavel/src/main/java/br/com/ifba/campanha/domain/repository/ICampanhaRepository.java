/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.campanha.domain.repository;

/**
 *
 * @author luis2
 */

import br.com.ifba.campanha.domain.entity.Campanha;
import br.com.ifba.campanha.domain.entity.StatusCampanha;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade Campanha.
 * @author luis
 */
@Repository
public interface ICampanhaRepository extends JpaRepository<Campanha, Long> {
    
    // Método de exemplo para futuras buscas
    List<Campanha> findByStatus(StatusCampanha status);
}