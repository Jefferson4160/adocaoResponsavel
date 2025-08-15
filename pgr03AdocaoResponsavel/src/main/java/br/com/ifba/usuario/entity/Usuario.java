/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.entity;

import br.com.ifba.lar_temporario.entity.LarTemporario;
import br.com.ifba.perfilDeUsuario.entity.PerfilDeUsuario;
import br.com.ifba.pessoa.entity.Pessoa;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true) // Importante para herança
@Inheritance(strategy = InheritanceType.JOINED) // Para que Adotante, Funcionario, etc. herdem
@PrimaryKeyJoinColumn(name = "id") // Associa a PK do Usuario à PK da Pessoa
public class Usuario extends Pessoa {

    @ManyToOne
    @JoinColumn(name = "perfil_id", nullable = false)
    private PerfilDeUsuario perfilDeUsuario;
    
    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY)
    private LarTemporario larTemporario;
    
}