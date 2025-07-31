/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.lar_temporario.controller;

import br.com.ifba.lar_temporario.entity.LarTemporario;
import br.com.ifba.lar_temporario.service.LarTemporarioIService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

/**
 * @author Luan Alves
 * Controller responsável por lidar com requisições relacionadas ao Lar Temporário.
 */
@Controller
@RequiredArgsConstructor
public class LarTemporarioController implements LarTemporarioIController {

    private final LarTemporarioIService larTemporarioIService;
    private static final Logger log = LoggerFactory.getLogger(LarTemporarioController.class);

    public LarTemporario save(LarTemporario lar) {
        log.info("Cadastrando novo Lar Temporário");
        return larTemporarioIService.save(lar);
    }

    public LarTemporario update(LarTemporario lar) {
        log.info("Atualizando Lar Temporário");
        return larTemporarioIService.update(lar);
    }

    public void delete(LarTemporario lar) {
        log.info("Deletando Lar Temporário");
        larTemporarioIService.delete(lar);
    }

    public List<LarTemporario> findAll() {
        log.info("Buscando todos os Lares Temporários");
        return larTemporarioIService.findAll();
    }

    public LarTemporario findById(Long id) {
        log.info("Buscando Lar Temporário por ID");
        return larTemporarioIService.findById(id);
    } 
}
