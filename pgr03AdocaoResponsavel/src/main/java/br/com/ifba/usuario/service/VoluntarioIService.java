/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.usuario.service;

import br.com.ifba.voluntario.entity.Voluntario;
import java.util.List;
import java.util.Optional;

/**
 * Interface de Serviço para a entidade Voluntario.
 * @author Jefferson S
 */
// Interface que define o contrato para os serviços de Voluntário.
public interface VoluntarioIService {
    
    Voluntario save(Voluntario voluntario);
    
    Voluntario update(Voluntario voluntario);
    
    void delete(Voluntario voluntario);
    
    List<Voluntario> findAll();
    
    Optional<Voluntario> findById(Long id);
    
    // Método para buscar voluntários por parte do nome.
    List<Voluntario> findByNomeContaining(String nome);
    
    // Método para buscar voluntários pela área de atuação.
    List<Voluntario> findByAreaAtuacao(String areaAtuacao);
}
