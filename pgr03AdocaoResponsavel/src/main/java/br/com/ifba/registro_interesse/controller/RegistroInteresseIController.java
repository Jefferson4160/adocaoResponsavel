/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.com.ifba.registro_interesse.controller;

import br.com.ftba.registro_interesse.entity.RegistroInteresse;
import java.util.List;

/**
 *
 * @author inque
 */
public class RegistroInteresseIController {
    
    RegistroInteresse save(RegistroInteresse registroInteresse);

    RegistroInteresse update(RegistroInteresse registroInteresse);

    void delete(RegistroInteresse registroInteresse);

    List<RegistroInteresse> findAll();

    RegistroInteresse findById(Long id);

}
