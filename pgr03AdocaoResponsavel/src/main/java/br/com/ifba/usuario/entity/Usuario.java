/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.entity; 

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import br.com.ifba.usuario.util.TipoAcesso;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Inheritance; 
import jakarta.persistence.InheritanceType; 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;


/**
 * Classe base abstrata para todos os tipos de usuários (Funcionário, Adotante, Voluntário).
 * @author Jefferson S
 */
@Entity // Uma entidade gerenciada pelo JPA
@Table(name = "tb_usuario") 
@Data //Gera getters, setters, equals, hashCode, toString
@EqualsAndHashCode(callSuper = true) // Para eliminar warning no @Data
@AllArgsConstructor // Gera construtor com todos os atributos
@NoArgsConstructor // Gera construtor vazio
@Inheritance(strategy = InheritanceType.JOINED) // Estratégia de herança para criar tabelas separadas e uni-las

public abstract class Usuario extends PersistenceEntity { 

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "cpf", nullable = false, length = 14, unique = true)
    private String cpf;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "endereco", nullable = false, columnDefinition = "TEXT")
    private String endereco;

    @Column(name = "cidade", nullable = false, length = 50)
    private String cidade;

    @Column(name = "estado", nullable = false, length = 2)
    private String estado;
    
    @Enumerated(EnumType.STRING) // Armazena o nome do enum no BD
    @Column(name = "tipo_acesso", nullable = false)
    private TipoAcesso tipoAcesso;
    
 
}