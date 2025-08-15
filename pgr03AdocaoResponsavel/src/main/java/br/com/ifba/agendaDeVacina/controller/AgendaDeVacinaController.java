/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.agendaDeVacina.controller;

/**
 *
 * @author ADMIN
 */
import br.com.ifba.agendaDeVacina.entity.AgendaDeVacina; 
import br.com.ifba.agendaDeVacina.service.AgendaDeVacinaIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AgendaDeVacinaController implements AgendaDeVacinaIController {

    private final AgendaDeVacinaIService agendaDeVacinaService;

    @Override
    public AgendaDeVacina salvarAgendaDeVacina(AgendaDeVacina agendaDeVacina) {
        log.info("Recebendo solicitação para salvar agenda de vacina.");
        return agendaDeVacinaService.save(agendaDeVacina);
    }

    @Override
    public AgendaDeVacina atualizarAgendaDeVacina(AgendaDeVacina agendaDeVacina) {
        log.info("Recebendo solicitação para atualizar agenda de vacina com ID: {}", agendaDeVacina.getId());
        return agendaDeVacinaService.update(agendaDeVacina);
    }

    @Override
    public void deletarAgendaDeVacina(Long id) {
        log.info("Recebendo solicitação para deletar agenda de vacina com ID: {}", id);
        agendaDeVacinaService.delete(id);
    }

    @Override
    public Optional<AgendaDeVacina> buscarAgendaDeVacinaPorId(Long id) {
        log.info("Recebendo solicitação para buscar agenda de vacina com ID: {}", id);
        return agendaDeVacinaService.findById(id);
    }

    @Override
    public List<AgendaDeVacina> buscarTodasAgendasDeVacina() {
        log.info("Recebendo solicitação para buscar todas as agendas de vacina.");
        return agendaDeVacinaService.findAll();
    }

    @Override
    public List<AgendaDeVacina> buscarAgendasDeVacinaPorAnimalId(Long idAnimal) {
        log.info("Recebendo solicitação para buscar agendas de vacina por ID do animal: {}", idAnimal);
        return agendaDeVacinaService.findByAnimalId(idAnimal);
    }
}
