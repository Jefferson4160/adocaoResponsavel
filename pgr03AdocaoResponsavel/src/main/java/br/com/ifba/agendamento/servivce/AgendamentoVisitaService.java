/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.com.ifba.agendamento.servivce;

import br.com.ifba.agendamento.entity.AgendamentoVisita;
import br.com.ifba.agendamento.repository.AgendamentoVisitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author inque
 */
@Service
public class AgendamentoVisitaService {
    @Autowired
    private AgendamentoVisitaRepository agendamentoRepository;

    public AgendamentoVisita salvar(AgendamentoVisita agendamento) {
        // REGRAS DE NEGÓCIO AQUI:
        // Verificar se já não existe um agendamento no mesmo horário para o mesmo animal.
        // Verificar se o animal já não foi adotado.
        if (agendamento.getId() == null) {
            agendamento.setStatus(StatusAgendamento.AGENDADO); // Define o status inicial
        }
        return agendamentoRepository.save(agendamento);
    }

    public void excluir(Long id) {
        agendamentoRepository.deleteById(id);
    }

    public Optional<AgendamentoVisita> buscarPorId(Long id) {
        return agendamentoRepository.findById(id);
    }

    public List<AgendamentoVisita> buscarTodos() {
        return agendamentoRepository.findAll();
    }

}
