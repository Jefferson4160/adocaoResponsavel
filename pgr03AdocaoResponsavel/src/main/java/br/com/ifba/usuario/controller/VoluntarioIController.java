/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.usuario.controller;

import br.com.ifba.voluntario.entity.Voluntario;
import java.util.List;
import java.util.Optional;

// Interface que define o contrato do controlador para as operações de Voluntário.
public interface VoluntarioIController {

    // Método para salvar um novo voluntário.
    Voluntario salvarVoluntario(Voluntario voluntario);

    // Método para atualizar um voluntário existente.
    Voluntario atualizarVoluntario(Voluntario voluntario);

    // Método para deletar um voluntário.
    void deletarVoluntario(Voluntario voluntario);

    // Método para buscar um voluntário pelo seu ID.
    Optional<Voluntario> buscarVoluntarioPorId(Long id);

    // Método para buscar todos os voluntários.
    List<Voluntario> buscarTodosVoluntarios();

    // Método para buscar voluntários por parte do nome da pessoa associada.
    List<Voluntario> buscarVoluntarioPorNome(String nome);

    // Método para buscar voluntários pela área de atuação.
    List<Voluntario> buscarVoluntarioPorAreaAtuacao(String areaAtuacao);
}
