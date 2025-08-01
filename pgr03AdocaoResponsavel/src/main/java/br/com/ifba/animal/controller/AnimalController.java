/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.animal.controller;

import br.com.ifba.animal.entity.Animal;
import br.com.ifba.animal.service.AnimalIService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author almei
 */
@Controller
public class AnimalController implements AnimalIController {

    @Autowired
    private AnimalIService animalService;

    private static final Logger log = LoggerFactory.getLogger(AnimalController.class);

    @Override
    public Animal save(Animal animal) {
        log.info("Cadastrando novo Animal");
        return animalService.save(animal);
    }

    @Override
    public Animal update(Animal animal) {
        log.info("Atualizando Animal");
        return animalService.update(animal);
    }

    @Override
    public void delete(Animal animal) {
        log.info("Deletando Animal");
        animalService.delete(animal);
    }

    @Override
    public List<Animal> findAll() {
        log.info("Buscando todos os Animais");
        return animalService.findAll();
    }

    @Override
    public Animal findById(Long id) {
        log.info("Buscando Animal por ID");
        return animalService.findById(id);
    }
}
