/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.com.ifba.agendamento.controller;

import br.com.ifba.agendamento.entity.AgendamentoVisita;
import br.com.ifba.agendamento.service.AgendamentoVisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.List;

/**
 *
 * @author inque
 */
@Controller
public class AgendamentoVisitaController {
    
    @Autowired
    private AgendamentoVisitaService agendamentoService;

    @Override
    public AgendamentoVisita save(AgendamentoVisita agendamento) {
        return agendamentoService.salvar(agendamento);
    }

    @Override
    public AgendamentoVisita update(AgendamentoVisita agendamento) {
        return agendamentoService.salvar(agendamento);
    }

    @Override
    public void delete(AgendamentoVisita agendamento) {
        agendamentoService.excluir(agendamento.getId());
    }

    @Override
    public List<AgendamentoVisita> findAll() {
        return agendamentoService.buscarTodos();
    }

    @Override
    public AgendamentoVisita findById(Long id) {
        return agendamentoService.buscarPorId(id).orElse(null);
    }

}
