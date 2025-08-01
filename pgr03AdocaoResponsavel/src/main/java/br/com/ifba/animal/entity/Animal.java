/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.com.ifba.animal.entity;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author almei
 */

@Entity
@Table(name = "tb_animal")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Animal extends PersistenceEntity {
   
    @Column(name = "nome", nullable = false)
    private String nome;
    
    @Column(name = "especie", nullable = false)
    private String especie;
    
    @Column(name = "raca", nullable = false)
    private String raca;
    
    @Column(name = "idade", nullable = false)
    private int idade;
    
    @Column(name = "genero", nullable = false)
    private String genero;
    
    @Column(name = "adotdato", nullable = false)
    private boolean adotado; // Se foi adotado ou não
    
   
    
}
