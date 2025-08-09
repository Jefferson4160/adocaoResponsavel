/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.entity;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import br.com.ifba.perfilDeUsuario.entity.PerfilDeUsuario;
import br.com.ifba.pessoa.entity.Pessoa;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario extends PersistenceEntity {

    // Mapeamento bidirecional: Usuario referencia Pessoa
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pessoa_id", nullable = false, unique = true)
    private Pessoa pessoa;

    // Mapeamento bidirecional: Usuario referencia PerfilDeUsuario
    @ManyToOne
    @JoinColumn(name = "perfil_id", nullable = false)
    private PerfilDeUsuario perfilDeUsuario;
    
}