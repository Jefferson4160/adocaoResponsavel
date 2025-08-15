/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.com.ifba.agendamento.repository;

import br.com.ifba.agendamento.entity.AgendamentoVisita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * @author inque
 */
@Repository
public class AgendamentoVisitaRepository extends JpaRepository<AgendamentoVisita, Long> {
    // customizar futuramente
}
