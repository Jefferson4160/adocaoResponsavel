/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.animal.controller;


import br.com.ifba.animal.entity.Animal;
import java.util.List;

/**
 *
 * @author almei
 */

public interface AnimalIController {

    Animal save(Animal animal);

    Animal update(Animal animal);

    void delete(Animal animal);

    List<Animal> findAll();

    Animal findById(Long id);
}

