/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.usuario.repository;

import br.com.ifba.voluntario.entity.Voluntario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 

/**
 * Repositório para a entidade Voluntario.
 * @author Jefferson S
 */
@Repository
public interface VoluntarioRepository extends JpaRepository<Voluntario, Long> {

    public List<Voluntario> findByNomeContainingIgnoreCase(String nome);

    public List<Voluntario> findByAreaAtuacaoContainingIgnoreCase(String areaAtuacao);
    
}
