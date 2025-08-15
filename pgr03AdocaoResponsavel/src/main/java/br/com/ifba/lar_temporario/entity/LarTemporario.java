/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.lar_temporario.entity;

import br.com.ifba.animal.entity.Animal;
import br.com.ifba.infrastructure.entity.PersistenceEntity;
import br.com.ifba.usuario.entity.Usuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Luan Alves
 */
@Entity
@Table(name = "tb_lar_temporario")
@Data // @EqualsAndHashCode(callSuper = true) // Para eliminar warning no @Data porém não é uma pratica recomendada.
@AllArgsConstructor
@NoArgsConstructor
public class LarTemporario extends PersistenceEntity{
    
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @OneToMany(mappedBy = "larTemporario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Animal> animais;

    @Column(name = "quantidade_max_animais", nullable = false)
    private Integer quantidadeMaxAnimais;

    @Column(name = "disponivel", nullable = false)
    private boolean disponivel;
    
    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
    
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;
    
}
