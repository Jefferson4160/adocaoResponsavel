/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.adotante.entity;

import br.com.ifba.pessoa.entity.Pessoa;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn; 
import jakarta.persistence.Table;
import lombok.AllArgsConstructor; 
import lombok.Data;             
import lombok.NoArgsConstructor; 

/**
 * Classe que representa um Adotante, herdando de Usuario.
 * @author Jefferson S
 */
@Entity // Uma entidade gerenciada pelo JPA
@Table(name = "tb_adotante") // Mapeia para a tabela específica de adotantes
@Data // Gera getters, setters, equals, hashCode, toString
@AllArgsConstructor // Gera construtor com todos os atributos
@NoArgsConstructor // Gera construtor vazio
@PrimaryKeyJoinColumn(name = "id_usuario") // Define a coluna de junção com a tabela pai (tb_usuario)

public class Adotante extends Pessoa {

    @Column(name = "historico_animal", columnDefinition = "TEXT")
    private String historicoDoAnimal;
}
