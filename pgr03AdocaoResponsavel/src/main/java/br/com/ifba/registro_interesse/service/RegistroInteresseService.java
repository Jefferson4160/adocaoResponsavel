/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.com.ifba.registro_interesse.service;

import br.com.ifba.registro_interesse.entity.RegistroInteresse;
import br.com.ifba.registro_interesse.repository.RegistroInteresseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author inque
 */
@Service
public class RegistroInteresseService {
    
    private final RegistroInteresseRepository registroInteresseRepository;

    @Autowired
    public RegistroInteresseService(RegistroInteresseRepository registroInteresseRepository) {
        this.registroInteresseRepository = registroInteresseRepository;
    }

    public RegistroInteresse salvar(RegistroInteresse registroInteresse) {
        if (registroInteresse.getId() == null) {
            registroInteresse.setDataRegistro(LocalDateTime.now());
            if (registroInteresse.getStatusInteresse() == null) {
            //      registroInteresse.setStatusInteresse(RegistroInteresse.StatusInteresse.EM_ANALISE);
            }
        }
        // outras regras quando tiver
        return registroInteresseRepository.save(registroInteresse);
    }

    public Optional<RegistroInteresse> buscarPorId(Long id) {
        return registroInteresseRepository.findById(id);
    }

    public List<RegistroInteresse> buscarTodos() {
        return registroInteresseRepository.findAll();
    }

    public void excluir(Long id) {
        registroInteresseRepository.deleteById(id);
    }

}
