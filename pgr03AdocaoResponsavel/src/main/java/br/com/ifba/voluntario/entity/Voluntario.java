/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.voluntario.entity;

import br.com.ifba.usuario.entity.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn; 
import jakarta.persistence.Table;
import lombok.AllArgsConstructor; 
import lombok.Data;              
import lombok.NoArgsConstructor;   
import lombok.EqualsAndHashCode; 



/**
 * Classe que representa um Voluntário, herdando de Usuario.
 * @author Jefferson S
 */
@Entity // Uma entidade gerenciada pelo JPA
@Table(name = "tb_voluntario") // Mapeia para a tabela específica de voluntários
@Data // Gera getters, setters, equals, hashCode, toString
@AllArgsConstructor // Gera construtor com todos os atributos
@NoArgsConstructor // Gera construtor vazio
@EqualsAndHashCode(callSuper = true) // Inclui atributos da superclasse em equals/hashCode
@PrimaryKeyJoinColumn(name = "id_usuario") // Define a coluna de junção com a tabela pai (tb_usuario)

public class Voluntario extends Usuario { 

    
    @Column(name = "area_atuacao", length = 100)
    private String areaAtuacao; // Ex: "Resgate", "Eventos", "Educação"

    @Column(name = "disponibilidade", columnDefinition = "TEXT")
    private String disponibilidade; // Ex: "Finais de semana", "Segundas e Quartas à tarde"

   
}
