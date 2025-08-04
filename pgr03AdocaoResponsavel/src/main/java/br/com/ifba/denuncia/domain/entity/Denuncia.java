/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.denuncia.domain.entity;

/**
 *
 * @author luis2
 */

import br.com.ifba.adotante.entity.Adotante;
import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Representa uma denúncia feita contra um adotante.
 * @author luis
 */
@Entity
@Table(name = "tb_denuncia")
@Data
@EqualsAndHashCode(callSuper = true)
public class Denuncia extends PersistenceEntity {

    private String descricao;
    
    private LocalDateTime dataDenuncia;

    @Enumerated(EnumType.STRING)
    private StatusDenuncia status;

    // Relaciona a denúncia a um Adotante específico
    @ManyToOne
    private Adotante denunciado;
}
