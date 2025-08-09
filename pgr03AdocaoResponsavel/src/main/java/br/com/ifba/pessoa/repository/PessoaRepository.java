/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.pessoa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.ifba.pessoa.entity.Pessoa;

/**
 *
 * @author Jefferson S
 */

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    
    // Aqui posso adicionar métodos personalizados
}