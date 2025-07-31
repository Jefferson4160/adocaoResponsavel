/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.lar_temporario.controller;

import br.com.ifba.lar_temporario.entity.LarTemporario;
import java.util.List;

/**
 * Interface responsável pelas operações de controle de Lar Temporário.
 * 
 * @author Luan Alves
 */
public interface LarTemporarioIController {

    List<LarTemporario> findAll() throws RuntimeException;

    LarTemporario save(LarTemporario lar) throws RuntimeException;

    LarTemporario update(LarTemporario lar) throws RuntimeException;

    void delete(LarTemporario lar) throws RuntimeException;

    LarTemporario findById(Long id) throws RuntimeException;
}
