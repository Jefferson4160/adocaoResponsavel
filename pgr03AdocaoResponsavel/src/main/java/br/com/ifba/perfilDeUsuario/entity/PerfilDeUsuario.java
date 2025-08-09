/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.perfilDeUsuario.entity;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_perfil_usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerfilDeUsuario extends PersistenceEntity {

    @Column(name = "nome_perfil", nullable = false, unique = true, length = 50)
    private String nomeDoPerfil;
}
