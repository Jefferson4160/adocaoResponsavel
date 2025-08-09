/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.usuario.service;

import br.com.ifba.adotante.entity.Adotante;
import java.util.List;
import java.util.Optional;

/**
 * Interface de Serviço para a entidade Adotante.
 * @author Jefferson S
 */
// Interface que define o contrato para os serviços de Adotante.
public interface AdotanteIService {

    Adotante save(Adotante adotante);

    Adotante update(Adotante adotante);

    void delete(Adotante adotante);

    List<Adotante> findAll();

    Optional<Adotante> findById(Long id);
    
    // Método para buscar adotantes por parte do nome.
    List<Adotante> findByNomeContaining(String nome);
    
    // Declaração do método de adotantes com denúncias.
    List<Adotante> findAdotantesComDenuncias();
}
