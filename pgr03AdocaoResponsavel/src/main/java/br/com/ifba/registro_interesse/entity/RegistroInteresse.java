/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.com.ifba.registro_interesse.entity;

import br.com.ifba.animal.entity.Animal;
import br.com.ifba.infrastructure.entity.PersistenceEntity;
import br.com.ifba.usuario.entity.ExcluirEstaClasse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *
 * @author inque
 */
@Entity
@Table(name = "tb_reg._interesse")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroInteresse extends PersistenceEntity{
    
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id", referencedColumnName = "id")
    ExcluirEstaClasse pessoa;
    
    @ManyToOne(fetch = FetchType.LAZY) // Relacionamento (n,1): N interesse para um Animal
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @Enumerated(EnumType.STRING) // Armazena o status como String no bd
    @Column(name = "status_interesse", nullable = false)
    private StatusInteresse statusInteresse;
    
    @Column(name = "questionário", columnDefinition = "TEXT")
    private String questionario;

    public void setDataRegistro(LocalDateTime now) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object getStatusInteresse() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
