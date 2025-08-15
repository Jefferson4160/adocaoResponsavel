/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.agendaDeVacina.service;

/**
 *
 * @author ADMIN
 */
import br.com.ifba.agendaDeVacina.entity.AgendaDeVacina; 
import br.com.ifba.agendaDeVacina.repository.AgendaDeVacinaRepository; 
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgendaDeVacinaService implements AgendaDeVacinaIService {
    
    private final AgendaDeVacinaRepository agendaDeVacinaRepository;

    @Override
    public AgendaDeVacina save(AgendaDeVacina agendaDeVacina) {
        log.info("Salvando registro de vacina para o animal de ID: {}", agendaDeVacina.getAnimal().getId());
        return agendaDeVacinaRepository.save(agendaDeVacina);
    }

    @Override
    public AgendaDeVacina update(AgendaDeVacina agendaDeVacina) {
        log.info("Atualizando registro de vacina com ID: {}", agendaDeVacina.getId());
        return agendaDeVacinaRepository.save(agendaDeVacina);
    }

    @Override
    public void delete(Long id) {
        log.info("Deletando registro de vacina com ID: {}", id);
        agendaDeVacinaRepository.deleteById(id);
    }

    @Override
    public Optional<AgendaDeVacina> findById(Long id) {
        log.info("Buscando registro de vacina com ID: {}", id);
        return agendaDeVacinaRepository.findById(id);
    }

    @Override
    public List<AgendaDeVacina> findAll() {
        log.info("Buscando todos os registros de vacina.");
        return agendaDeVacinaRepository.findAll();
    }

    @Override
    public List<AgendaDeVacina> findByAnimalId(Long idAnimal) {
        log.info("Buscando registros de vacina para o animal de ID: {}", idAnimal);
        return agendaDeVacinaRepository.findByAnimal_Id(idAnimal);
    }
}
