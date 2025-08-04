/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.com.ifba.registro_interesse.controller;

import br.com.ifba.registro_interesse.entity.RegistroInteresse;
import br.com.ifba.registro_interesse.service.RegistroInteresseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.List;

/**
 *
 * @author inque
 */
@Controller
public class RegistroInteresseController {
    
    private final RegistroInteresseService registroInteresseService;

    private static final Logger log = LoggerFactory.getLogger(RegistroInteresseController.class);

    @Autowired
    public RegistroInteresseController(RegistroInteresseService registroInteresseService) {
        this.registroInteresseService = registroInteresseService;
    }

    @Override
    public RegistroInteresse save(RegistroInteresse registroInteresse) {
        log.info("Cadastrando novo Registro de Interesse");
        return registroInteresseService.salvar(registroInteresse);
    }

    @Override
    public RegistroInteresse update(RegistroInteresse registroInteresse) {
        log.info("Atualizando Registro de Interesse");
        return registroInteresseService.salvar(registroInteresse);
    }

    @Override
    public void delete(RegistroInteresse registroInteresse) {
        log.info("Deletando Registro de Interesse");
        registroInteresseService.excluir(registroInteresse.getId());
    }

    @Override
    public List<RegistroInteresse> findAll() {
        log.info("Buscando todos os Registros de Interesse");
        return registroInteresseService.buscarTodos();
    }

    @Override
    public RegistroInteresse findById(Long id) {
        log.info("Buscando Registro de Interesse por ID");
        return registroInteresseService.buscarPorId(id).orElse(null);
    }

}
