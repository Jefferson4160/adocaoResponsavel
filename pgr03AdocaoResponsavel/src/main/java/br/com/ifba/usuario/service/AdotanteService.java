/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.service;

import br.com.ifba.adotante.entity.Adotante;
import br.com.ifba.usuario.repository.AdotanteRepository; 
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service // Marca a classe como um serviço do Spring.
@RequiredArgsConstructor // Gera o construtor para injeção de dependência.
@Slf4j // Facilita o uso de logs.
public class AdotanteService implements AdotanteIService {

    // Injeção do repositório via construtor, por ser final.
    private final AdotanteRepository adotanteRepository;

    @Override
    public Adotante save(Adotante adotante) {
        log.info("Salvando adotante: {}", adotante.getNome());
        // Lógica de validação pode ser adicionada aqui.
        return adotanteRepository.save(adotante);
    }

    @Override
    public Adotante update(Adotante adotante) {
        log.info("Atualizando adotante: {}", adotante.getNome());
        // Lógica para verificar se o adotante existe antes de atualizar.
        return adotanteRepository.save(adotante);
    }

    @Override
    public void delete(Adotante adotante) {
        log.info("Deletando adotante com ID: {}", adotante.getId());
        // Lógica para verificar se o adotante existe antes de deletar.
        adotanteRepository.delete(adotante);
    }

    @Override
    public List<Adotante> findAll() {
        log.info("Buscando todos os adotantes.");
        return adotanteRepository.findAll();
    }

    @Override
    public Optional<Adotante> findById(Long id) {
        log.info("Buscando adotante com ID: {}", id);
        return adotanteRepository.findById(id);
    }

    @Override
    public List<Adotante> findByNomeContaining(String nome) {
        log.info("Buscando adotantes pelo nome: {}", nome);
        // Delega a busca ao repositório, que a realiza na entidade Pessoa.
        return adotanteRepository.findByNomeContainingIgnoreCase(nome);
    }
    
    @Override
    public List<Adotante> findAdotantesComDenuncias() {
        log.info("Buscando adotantes com denúncias (método a ser implementado).");
        // Por enquanto, apenas retorna uma lista vazia.
        return Collections.emptyList();
    }
}