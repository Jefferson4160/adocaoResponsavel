/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.agendaDeVacina.service;

/**
 *
 * @author ADMIN
 */
import br.com.ifba.agendaDeVacina.entity.AgendaDeVacina;
import java.util.List;
import java.util.Optional;

public interface AgendaDeVacinaIService {
    
    AgendaDeVacina save(AgendaDeVacina agendaDeVacina);
    
    AgendaDeVacina update(AgendaDeVacina agendaDeVacina);
    
    void delete(Long id);
    
    Optional<AgendaDeVacina> findById(Long id);
    
    List<AgendaDeVacina> findAll();
    
    List<AgendaDeVacina> findByAnimalId(Long idAnimal);
}
