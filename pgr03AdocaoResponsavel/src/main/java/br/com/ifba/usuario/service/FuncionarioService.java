/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.service;

import br.com.ifba.funcionario.entity.Funcionario;
import br.com.ifba.usuario.repository.FuncionarioRepository; // Importar
import br.com.ifba.usuario.repository.UsuarioRepository; // Importar para verificar CPF
import br.com.ifba.infrastructure.util.StringUtil;
import br.com.ifba.usuario.entity.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FuncionarioService implements FuncionarioIService {

    private final FuncionarioRepository funcionarioRepository;
    private final UsuarioRepository usuarioRepository; // Para verificar CPF (todos usuários)

    @Override
    @Transactional
    public Funcionario save(Funcionario funcionario) throws RuntimeException {
        validarFuncionario(funcionario, false); // Validação de funcionário
        

        try {
            log.info("Salvando novo funcionário: {}", funcionario.getNome());
            // JpaRepository.save() lida com persistência (novo) e merge (existente)
            return funcionarioRepository.save(funcionario);
        } catch (Exception e) {
            log.error("Erro ao salvar funcionário: {}", e.getMessage(), e);
            throw new RuntimeException("Falha ao salvar funcionário", e);
        }
    }

    @Override
    @Transactional
    public Funcionario update(Funcionario funcionario) throws RuntimeException {
        validarFuncionario(funcionario, true);

        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(funcionario.getId());
        Funcionario funcionarioExistente = optionalFuncionario.orElse(null);

        if (funcionarioExistente == null) {
            throw new RuntimeException("Funcionário com ID " + funcionario.getId() + " nao encontrado para atualização.");
        }
        // Atualiza campos específicos de Funcionario
        
        funcionarioExistente.setCargo(funcionario.getCargo());
        funcionarioExistente.setSalario(funcionario.getSalario());

        // Atualiza campos herdados de Usuario
        funcionarioExistente.setNome(funcionario.getNome());
        funcionarioExistente.setCpf(funcionario.getCpf());
        funcionarioExistente.setEmail(funcionario.getEmail());
        funcionarioExistente.setTelefone(funcionario.getTelefone());
        funcionarioExistente.setEndereco(funcionario.getEndereco());
        funcionarioExistente.setCidade(funcionario.getCidade());
        funcionarioExistente.setEstado(funcionario.getEstado());
        funcionarioExistente.setTipoAcesso(funcionario.getTipoAcesso()); // Atualiza o tipo de acesso

        try {
            log.info("Atualizando funcionário com ID: {}", funcionario.getId());
            return funcionarioRepository.save(funcionarioExistente);
        } catch (RuntimeException e) {
            log.error("Erro ao atualizar funcionário com ID {}: {}", funcionario.getId(), e.getMessage(), e);
            throw new RuntimeException("Falha ao atualizar funcionário", e);
        }
    }

    @Override
    @Transactional
    public void delete(Funcionario funcionario) throws RuntimeException {
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(funcionario.getId());
        Funcionario funcionarioExistente = optionalFuncionario.orElse(null);

        if (funcionarioExistente == null) {
            throw new RuntimeException("Funcionário com ID" + funcionario.getId() + " nao encontrado para remoção.");
        }
        try {
            log.info("Removendo funcionário com ID: {}", funcionario.getId());
            funcionarioRepository.delete(funcionarioExistente);
        } catch (RuntimeException e) {
            log.error("Erro ao remover funcionário com ID {}: {}", funcionario.getId(), e.getMessage(), e);
            throw new RuntimeException("Falha ao remover funcionário", e);
        }
    }

    @Override
    public List<Funcionario> findAll() {
        try {
            log.debug("Listando todos os funcionários...");
            return funcionarioRepository.findAll();
        } catch (RuntimeException e) {
            log.error("Erro ao listar todos os funcionários: {}", e.getMessage(), e);
            throw new RuntimeException("Falha ao listar todos os funcionários", e);
        }
    }

    @Override
    public Optional<Funcionario> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do funcionário não pode ser nulo para busca.");
        }
        try {
            log.debug("Buscando funcionário por ID: {}", id);
            return funcionarioRepository.findById(id); // Retorna Optional diretamente
        } catch (RuntimeException e) {
            log.error("Erro ao buscar funcionário por ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Falha ao buscar funcionário por ID", e);
        }
    }


    @Override
    public List<Funcionario> findByNomeContaining(String nome) {
        if (StringUtil.isNullOrEmpty(nome)) {
            return findAll();
        }
        try {
            log.debug("Buscando funcionários por nome: {}", nome);
            return funcionarioRepository.findByNomeContainingIgnoreCase(nome);
        } catch (RuntimeException e) {
            log.error("Erro ao buscar funcionários por nome '{}': {}", nome, e.getMessage(), e);
            throw new RuntimeException("Falha ao buscar funcionários por nome", e);
        }
    }

    // Método auxiliar privado para validação específica de Funcionario
    private void validarFuncionario(Funcionario funcionario, boolean isUpdate) {
        // Validações herdadas de Usuario (campos comuns)
        if (funcionario == null) {
            throw new RuntimeException("Dados do funcionário não preenchidos.");
        }
        if (StringUtil.isNullOrEmpty(funcionario.getNome())) {
            throw new IllegalArgumentException("Nome do funcionário não pode ser vazio.");
        }
        if (StringUtil.isNullOrEmpty(funcionario.getCpf())) {
            throw new IllegalArgumentException("CPF do funcionário não pode ser vazio.");
        }
        if (StringUtil.isNullOrEmpty(funcionario.getEmail())) {
            throw new IllegalArgumentException("Email do funcionário não pode ser vazio.");
        }
        if (StringUtil.isNullOrEmpty(funcionario.getEndereco())) {
            throw new IllegalArgumentException("Endereço do funcionário não pode ser vazio.");
        }
        if (StringUtil.isNullOrEmpty(funcionario.getCidade())) {
            throw new IllegalArgumentException("Cidade do funcionário não pode ser vazio.");
        }
        if (StringUtil.isNullOrEmpty(funcionario.getEstado())) {
            throw new IllegalArgumentException("Estado do funcionário não pode ser vazio.");
        }

        // Validações específicas de Funcionario
       
        if (StringUtil.isNullOrEmpty(funcionario.getCargo())) {
            throw new IllegalArgumentException("Cargo do funcionário não pode ser vazio.");
        }
        if (funcionario.getSalario() == null || funcionario.getSalario() <= 0) {
            throw new IllegalArgumentException("Salário do funcionário deve ser um valor positivo.");
        }

        // Valida CPF único (se não for update ou se o CPF foi alterado)
        if (!isUpdate) { // Se for um novo funcionário
            if (usuarioRepository.findByCpf(funcionario.getCpf()).isPresent()) {
                throw new RuntimeException("CPF já cadastrado para outro usuário.");
            }
            
        } else { // Se for um update
            // Verifica se o CPF foi alterado e se o novo CPF já existe para outro usuário
            Optional<Usuario> usuarioComCpfExistente = usuarioRepository.findByCpf(funcionario.getCpf());
            if (usuarioComCpfExistente.isPresent() && !usuarioComCpfExistente.get().getId().equals(funcionario.getId())) {
                throw new RuntimeException("CPF já cadastrado para outro usuário.");
            }
            
        }
    }

   
}