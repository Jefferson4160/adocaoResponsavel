/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.campanha.domain.entity;

/**
 *
 * @author luis2
 */

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import br.com.ifba.voluntario.entity.Voluntario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Representa uma campanha ou evento de arrecadação/adoção.
 * @author luis
 */
@Entity
@Table(name = "tb_campanha")
@Data
@EqualsAndHashCode(callSuper = true)
public class Campanha extends PersistenceEntity {

    @Column(nullable = false)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private LocalDate dataInicio;
    private LocalDate dataFim;

    @Enumerated(EnumType.STRING)
    private TipoCampanha tipo;

    @Enumerated(EnumType.STRING)
    private StatusCampanha status;

    private double meta;
    private double arrecadado;

    // Relacionamento Muitos-para-Muitos com Voluntario
    @ManyToMany
    private List<Voluntario> voluntariosResponsaveis;
}
