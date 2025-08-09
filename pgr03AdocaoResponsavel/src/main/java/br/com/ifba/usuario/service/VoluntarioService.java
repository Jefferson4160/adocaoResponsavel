/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.service;

import br.com.ifba.usuario.repository.VoluntarioRepository;
import br.com.ifba.voluntario.entity.Voluntario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service // Anotação para o Spring gerenciar esta classe como um bean de serviço.
@RequiredArgsConstructor // Gera um construtor com os atributos finais para a injeção de dependência.
@Slf4j // Habilita o uso de logs na classe.
public class VoluntarioService implements VoluntarioIService {

    // Atributo final para injeção de dependência via construtor.
    private final VoluntarioRepository voluntarioRepository;
    
    @Override
    public Voluntario save(Voluntario voluntario) {
        log.info("Salvando voluntário: {}", voluntario.getNome());
        // Lógica de validação pode ser adicionada aqui.
        return voluntarioRepository.save(voluntario);
    }

    @Override
    public Voluntario update(Voluntario voluntario) {
        log.info("Atualizando voluntário: {}", voluntario.getNome());
        // Lógica para verificar se o voluntário existe antes de atualizar.
        return voluntarioRepository.save(voluntario);
    }

    @Override
    public void delete(Voluntario voluntario) {
        log.info("Deletando voluntário com ID: {}", voluntario.getId());
        // Lógica para verificar se o voluntário existe antes de deletar.
        voluntarioRepository.delete(voluntario);
    }

    @Override
    public List<Voluntario> findAll() {
        log.info("Buscando todos os voluntários.");
        return voluntarioRepository.findAll();
    }

    @Override
    public Optional<Voluntario> findById(Long id) {
        log.info("Buscando voluntário com ID: {}", id);
        return voluntarioRepository.findById(id);
    }

    @Override
    public List<Voluntario> findByNomeContaining(String nome) {
        log.info("Buscando voluntários pelo nome: {}", nome);
        // O repositório irá buscar o nome na entidade Pessoa.
        return voluntarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Override
    public List<Voluntario> findByAreaAtuacao(String areaAtuacao) {
        log.info("Buscando voluntários pela área de atuação: {}", areaAtuacao);
        // Delega a busca por área de atuação diretamente ao repositório.
        return voluntarioRepository.findByAreaDeAtuacao(areaAtuacao);
    }
}
