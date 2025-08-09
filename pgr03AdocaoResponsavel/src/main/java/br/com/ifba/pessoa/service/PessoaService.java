/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.pessoa.service;

import br.com.ifba.pessoa.entity.Pessoa;
import br.com.ifba.pessoa.repository.PessoaRepository;
import br.com.ifba.infrastructure.util.StringUtil; // 
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jefferson S
 */
@Service // Anotação para o Spring gerenciar esta classe como um bean de serviço.
public class PessoaService implements PessoaIService {

    // Injeção do repositório para acesso aos métodos de persistência.
    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public Pessoa save(Pessoa pessoa) {
        // Valida o objeto Pessoa antes de salvar.
        validarPessoa(pessoa, false);

        try {
            // Delega a tarefa de salvar ao repositório.
            return pessoaRepository.save(pessoa);
        } catch (RuntimeException e) {
            // Captura e relança o erro com uma mensagem mais específica.
            throw new RuntimeException("Erro no service ao salvar a pessoa: " + e.getMessage(), e);
        }
    }

    @Override
    public Pessoa update(Pessoa pessoa) {
        // Valida o objeto para atualização.
        validarPessoa(pessoa, true);

        // Verifica se a pessoa com o ID fornecido existe no banco de dados.
        Optional<Pessoa> pessoaExistente = pessoaRepository.findById(pessoa.getId());
        if (pessoaExistente.isEmpty()) {
            throw new RuntimeException("Pessoa com ID " + pessoa.getId() + " não encontrada.");
        }
        
        // Atualiza os atributos da pessoa existente com os novos valores.
        Pessoa pessoaParaAtualizar = pessoaExistente.get();
        pessoaParaAtualizar.setNome(pessoa.getNome());
        pessoaParaAtualizar.setCpf(pessoa.getCpf());
        pessoaParaAtualizar.setEmail(pessoa.getEmail());
        pessoaParaAtualizar.setTelefone(pessoa.getTelefone());
        pessoaParaAtualizar.setEndereco(pessoa.getEndereco());
        pessoaParaAtualizar.setCidade(pessoa.getCidade());
        pessoaParaAtualizar.setEstado(pessoa.getEstado());

        try {
            // Delega a atualização ao repositório.
            return pessoaRepository.save(pessoaParaAtualizar);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro no service ao atualizar a pessoa: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Long id) {
        // Busca a pessoa para garantir que ela exista antes de deletar.
        if (pessoaRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Pessoa com ID " + id + " não encontrada para exclusão.");
        }
        
        try {
            // Remove a pessoa do banco de dados.
            pessoaRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro no service ao remover a pessoa: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Pessoa> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID da pessoa não pode ser nulo para busca.");
        }
        try {
            // Busca a pessoa por ID e retorna um Optional.
            return pessoaRepository.findById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro no service ao buscar pessoa por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Pessoa> findAll() {
        try {
            // Retorna a lista completa de pessoas do banco de dados.
            return pessoaRepository.findAll();
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro no service ao listar todas as pessoas: " + e.getMessage(), e);
        }
    }

    // Método auxiliar para validação dos dados de Pessoa.
    private void validarPessoa(Pessoa pessoa, boolean isUpdate) {
        if (pessoa == null) {
            throw new RuntimeException("Dados da pessoa não preenchidos.");
        }
        // Validações específicas para atualização.
        if (isUpdate && pessoa.getId() == null) {
            throw new IllegalArgumentException("ID da pessoa é obrigatório para atualização.");
        }
        if (!isUpdate && pessoa.getId() != null) {
            throw new RuntimeException("ID da pessoa deve ser nulo para o cadastro.");
        }
        // Validação de campos obrigatórios.
        if (StringUtil.isNullOrEmpty(pessoa.getNome())) {
            throw new IllegalArgumentException("Nome da pessoa não pode ser vazio.");
        }
        if (StringUtil.isNullOrEmpty(pessoa.getCpf())) {
            throw new IllegalArgumentException("CPF da pessoa não pode ser vazio.");
        }
        if (StringUtil.isNullOrEmpty(pessoa.getEndereco())) {
            throw new IllegalArgumentException("Endereço da pessoa não pode ser vazio.");
        }
        if (StringUtil.isNullOrEmpty(pessoa.getCidade())) {
            throw new IllegalArgumentException("Cidade da pessoa não pode ser vazia.");
        }
        if (StringUtil.isNullOrEmpty(pessoa.getEstado())) {
            throw new IllegalArgumentException("Estado da pessoa não pode ser vazio.");
        }
    }
}