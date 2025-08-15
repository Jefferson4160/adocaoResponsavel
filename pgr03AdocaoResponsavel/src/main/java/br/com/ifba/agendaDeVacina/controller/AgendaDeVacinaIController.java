/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.agendaDeVacina.controller;

/**
 *
 * @author ADMIN
 */
import br.com.ifba.agendaDeVacina.entity.AgendaDeVacina;
import java.util.List;
import java.util.Optional;

public interface AgendaDeVacinaIController {
    
    AgendaDeVacina salvarAgendaDeVacina(AgendaDeVacina agendaDeVacina);
    
    AgendaDeVacina atualizarAgendaDeVacina(AgendaDeVacina agendaDeVacina);
    
    void deletarAgendaDeVacina(Long id);
    
    Optional<AgendaDeVacina> buscarAgendaDeVacinaPorId(Long id);
    
    List<AgendaDeVacina> buscarTodasAgendasDeVacina();
    
    List<AgendaDeVacina> buscarAgendasDeVacinaPorAnimalId(Long idAnimal);
}