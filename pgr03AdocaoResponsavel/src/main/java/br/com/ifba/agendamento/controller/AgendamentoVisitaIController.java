/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.com.ifba.agendamento.controller;

import br.com.ifba.agendamento.entity.AgendamentoVisita;
import java.util.List;

/**
 *
 * @author inque
 */
public interface AgendamentoVisitaIController {
    
    AgendamentoVisita save(AgendamentoVisita agendamento);
    AgendamentoVisita update(AgendamentoVisita agendamento);
    void delete(AgendamentoVisita agendamento);
    List<AgendamentoVisita> findAll();
    AgendamentoVisita findById(Long id);

}
