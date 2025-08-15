/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.com.ifba.agendamento.entity;

import br.com.ifba.adotante.entity.Adotante; // para visitar é preciso demonstrar iteresse
import br.com.ifba.animal.entity.Animal;
import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 *
 * @author inque
 */
@Entity
@Table(name = "agendamento_visita")
@Data
public class AgendamentoVisita extends PersistenceEntity{
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adotante_id", nullable = false)
    private Adotante adotante; // Quem vai visitar

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal; // Animal a ser visitado

    @Column(name = "data_hora_visita", nullable = false)
    private LocalDateTime dataHoraVisita;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAgendamento status;

    private String observacoes; // Ex: "Confirmar com o voluntário"

}
