/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.pessoa.service;

import br.com.ifba.pessoa.entity.Pessoa;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Jefferson S
 */

// Interface que define os métodos que o serviço de Pessoa deve implementar.
public interface PessoaIService {

    Pessoa save(Pessoa pessoa);

    Pessoa update(Pessoa pessoa);

    void delete(Long id);

    Optional<Pessoa> findById(Long id);

    List<Pessoa> findAll();
}
