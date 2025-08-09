/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.usuario.controller;

import br.com.ifba.adotante.entity.Adotante;
import java.util.List;
import java.util.Optional;

// Interface que define o contrato do controlador para as operações de Adotante.
public interface AdotanteIController {
    
    // Método para salvar um novo adotante.
    Adotante salvarAdotante(Adotante adotante);

    // Método para atualizar um adotante existente.
    Adotante atualizarAdotante(Adotante adotante);

    // Método para deletar um adotante.
    void deletarAdotante(Adotante adotante);

    // Método para buscar um adotante pelo seu ID.
    Optional<Adotante> buscarAdotantePorId(Long id);

    // Método para buscar todos os adotantes.
    List<Adotante> buscarTodosAdotantes();

    // Método para buscar adotantes por parte do nome.
    List<Adotante> buscarAdotantePorNome(String nome);
    
    // Método para buscar adotantes com denúncias (implementação futura).
    List<Adotante> buscarAdotantesComDenuncias();
}
