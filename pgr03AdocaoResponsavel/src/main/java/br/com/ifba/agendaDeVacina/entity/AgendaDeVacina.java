/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.agendaDeVacina.entity;

/**
 *
 * @author ADMIN
 */
import br.com.ifba.animal.entity.Animal;
import br.com.ifba.infrastructure.entity.PersistenceEntity;
import br.com.ifba.usuario.entity.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_agendadevacina")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AgendaDeVacina extends PersistenceEntity {
    
    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @Column(name = "nome_vacina", nullable = false)
    private String nomeVacina;
    
    @Column(name = "data_aplicacao", nullable = false)
    private LocalDate dataAplicacao;
    
}
