/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.animal.service;

import br.com.ifba.animal.entity.Animal;
import br.com.ifba.animal.repository.AnimalRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author almei
 */
@Service
@RequiredArgsConstructor
public class AnimalService implements AnimalIService{
    
    @Autowired
    private final AnimalRepository animalRepository;
    
    private static final Logger log = LoggerFactory.getLogger(AnimalService.class);
    
    @Override
    public Animal save(Animal animal) throws RuntimeException {
        log.info("Tentando salvar Animal: {}", animal);

        if (animal == null) {
            log.info("Tentativa de salvar animal nulo.");
            throw new RuntimeException("Dados do animal não preenchidos.");
        }

        if (animal.getId() != null && animalRepository.existsById(animal.getId())) {
            log.info("Tentativa de salvar animal já existente com ID: {}", animal.getId());
            throw new RuntimeException("Animal já existente no banco de dados.");
        }

        if (animal.getIdade() < 0) {
            log.info("Idade do animal inválida.");
            throw new RuntimeException("A idade do animal deve ser maior ou igual a 0.");
        }
        
               Animal salvo = animalRepository.save(animal);
        log.info("Animal salvo com sucesso: {}", salvo);
        return salvo;
    }
    
     @Override
    public Animal update(Animal animal) throws RuntimeException {
        log.info("Tentando atualizar Animal: {}", animal);

        if (animal == null || animal.getId() == null) {
            log.info("Tentativa de atualizar animal inválido ou sem ID.");
            throw new RuntimeException("Animal inválido para atualização.");
        }

        Animal atualizado = animalRepository.save(animal);
        log.info("Animal atualizado com sucesso: {}", atualizado);
        return atualizado;
    }

    @Override
    public void delete(Animal animal) throws RuntimeException {
        log.info("Tentando deletar Animal: {}", animal);

        if (animal == null || animal.getId() == null) {
            log.info("Tentativa de deletar animal inválido.");
            throw new RuntimeException("Animal inválido para exclusão.");
        }

        animalRepository.delete(animal);
        log.info("Animal deletado com sucesso.");
    }

    @Override
    public List<Animal> findAll() {
        log.info("Buscando todos os Animais");
        return animalRepository.findAll();
    }

    @Override
    public Animal findById(Long id) {
        log.info("Buscando Animal por ID: {}", id);
        return animalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Animal não encontrado com o ID fornecido."));
    }
}
