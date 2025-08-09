/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.service;

import br.com.ifba.funcionario.entity.Funcionario;
import br.com.ifba.usuario.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service // Marca a classe como um serviço do Spring.
@RequiredArgsConstructor // Gera o construtor para injeção de dependência.
@Slf4j // Facilita o uso de logs.
public class FuncionarioService implements FuncionarioIService {

    // Injeção do repositório via construtor, por ser final.
    private final FuncionarioRepository funcionarioRepository;

    @Override
    public Funcionario save(Funcionario funcionario) {
        log.info("Salvando funcionário: {}", funcionario.getNome());
        // Lógica de validação pode ser adicionada aqui.
        return funcionarioRepository.save(funcionario);
    }

    @Override
    public Funcionario update(Funcionario funcionario) {
        log.info("Atualizando funcionário: {}", funcionario.getNome());
        // Lógica para verificar se o funcionário existe antes de atualizar.
        return funcionarioRepository.save(funcionario);
    }

    @Override
    public void delete(Funcionario funcionario) {
        log.info("Deletando funcionário com ID: {}", funcionario.getId());
        // Lógica para verificar se o funcionário existe antes de deletar.
        funcionarioRepository.delete(funcionario);
    }

    @Override
    public List<Funcionario> findAll() {
        log.info("Buscando todos os funcionários.");
        return funcionarioRepository.findAll();
    }

    @Override
    public Optional<Funcionario> findById(Long id) {
        log.info("Buscando funcionário com ID: {}", id);
        return funcionarioRepository.findById(id);
    }

    @Override
    public List<Funcionario> findByNomeContaining(String nome) {
        log.info("Buscando funcionários pelo nome: {}", nome);
        // Delega a busca ao repositório, que a realiza na entidade Pessoa.
        return funcionarioRepository.findByNomeContainingIgnoreCase(nome);
    }
}