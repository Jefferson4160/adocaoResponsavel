/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.usuario.repository;

import br.com.ifba.funcionario.entity.Funcionario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 

/**
 * Repositório para a entidade Funcionario.
 * Contém métodos específicos para funcionários.
 * @author Jefferson S
 */
@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    
    List<Funcionario> findByNomeContainingIgnoreCase(String nome);
    
}
