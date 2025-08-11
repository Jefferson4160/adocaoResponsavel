/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.com.ifba.animal.entity;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import br.com.ifba.registro_interesse.entity.RegistroInteresse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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
   
   
    @OneToMany(mappedBy = "animal")
    private List<RegistroInteresse> registrosInteresse;
    
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
    
    @Column(name = "adotado", nullable = false)
    private boolean adotado;
    
    @Column(name = "descricao")
    private String descricao;
   
    @CreationTimestamp
    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;
    
    // Formatter para a data (dd/MM/yyyy HH:mm)
    private static final DateTimeFormatter DATE_FORMATTER = 
            DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
    

    public String getDescricao() {
        return this.descricao;
    }
    

    public String getInformacoesCompletas() {
        return String.format(
            "Animal: %s%n" +
            "Espécie: %s%n" +
            "Raça: %s%n" +
            "Idade: %d anos%n" +
            "Gênero: %s%n" +
            "Adotado: %s%n" +       
            "Data de Cadastro: %s%n%n" +
            "Descrição: %s",
            nome,
            especie,
            raca,
            idade,
            genero,
            adotado ? "Sim" : "Não",
            dataCadastro != null ? 
                dataCadastro.format(DATE_FORMATTER) : "Não cadastrada",
            descricao != null ? descricao : "Nenhuma descrição fornecida"
        );
    }
}
