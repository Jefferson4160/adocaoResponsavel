/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.service;

import br.com.ifba.usuario.repository.VoluntarioRepository; 
import br.com.ifba.usuario.repository.UsuarioRepository; 
import br.com.ifba.infrastructure.util.StringUtil;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.voluntario.entity.Voluntario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoluntarioService implements VoluntarioIService {

    private final VoluntarioRepository voluntarioRepository;
    private final UsuarioRepository usuarioRepository; // Para verificar CPF (todos usuários)

    @Override
    @Transactional
    public Voluntario save(Voluntario voluntario) throws RuntimeException {
        validarVoluntario(voluntario, false);

        try {
            log.info("Salvando novo voluntário: {}", voluntario.getNome());
            return voluntarioRepository.save(voluntario);
        } catch (Exception e) {
            log.error("Erro ao salvar voluntário: {}", e.getMessage(), e);
            throw new RuntimeException("Falha ao salvar voluntário", e);
        }
    }

    @Override
    @Transactional
    public Voluntario update(Voluntario voluntario) throws RuntimeException {
        validarVoluntario(voluntario, true);

        Optional<Voluntario> optionalVoluntario = voluntarioRepository.findById(voluntario.getId());
        Voluntario voluntarioExistente = optionalVoluntario.orElse(null);

        if (voluntarioExistente == null) {
            throw new RuntimeException("Voluntário com ID " + voluntario.getId() + " nao encontrado para atualização.");
        }
        // Atualiza campos específicos de Voluntário
        voluntarioExistente.setAreaAtuacao(voluntario.getAreaAtuacao());
        voluntarioExistente.setDisponibilidade(voluntario.getDisponibilidade());

        // Atualiza campos herdados de Usuario
        voluntarioExistente.setNome(voluntario.getNome());
        voluntarioExistente.setCpf(voluntario.getCpf());
        voluntarioExistente.setEmail(voluntario.getEmail());
        voluntarioExistente.setTelefone(voluntario.getTelefone());
        voluntarioExistente.setEndereco(voluntario.getEndereco());
        voluntarioExistente.setCidade(voluntario.getCidade());
        voluntarioExistente.setEstado(voluntario.getEstado());
        voluntarioExistente.setTipoAcesso(voluntario.getTipoAcesso()); // Atualiza o tipo de acesso

        try {
            log.info("Atualizando voluntário com ID: {}", voluntario.getId());
            return voluntarioRepository.save(voluntarioExistente);
        } catch (RuntimeException e) {
            log.error("Erro ao atualizar voluntário com ID {}: {}", voluntario.getId(), e.getMessage(), e);
            throw new RuntimeException("Falha ao atualizar voluntário", e);
        }
    }

    @Override
    @Transactional
    public void delete(Voluntario voluntario) throws RuntimeException {
        Optional<Voluntario> optionalVoluntario = voluntarioRepository.findById(voluntario.getId());
        Voluntario voluntarioExistente = optionalVoluntario.orElse(null);

        if (voluntarioExistente == null) {
            throw new RuntimeException("Voluntário com ID" + voluntario.getId() + " nao encontrado para remoção.");
        }
        try {
            log.info("Removendo voluntário com ID: {}", voluntario.getId());
            voluntarioRepository.delete(voluntarioExistente);
        } catch (RuntimeException e) {
            log.error("Erro ao remover voluntário com ID {}: {}", voluntario.getId(), e.getMessage(), e);
            throw new RuntimeException("Falha ao remover voluntário", e);
        }
    }

    @Override
    public List<Voluntario> findAll() {
        try {
            log.debug("Listando todos os voluntários...");
            return voluntarioRepository.findAll();
        } catch (RuntimeException e) {
            log.error("Erro ao listar todos os voluntários: {}", e.getMessage(), e);
            throw new RuntimeException("Falha ao listar todos os voluntários", e);
        }
    }

    @Override
    public Optional<Voluntario> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do voluntário não pode ser nulo para busca.");
        }
        try {
            log.debug("Buscando voluntário por ID: {}", id);
            return voluntarioRepository.findById(id);
        } catch (RuntimeException e) {
            log.error("Erro ao buscar voluntário por ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Falha ao buscar voluntário por ID", e);
        }
    }

    @Override
    public List<Voluntario> findByNomeContaining(String nome) {
        if (StringUtil.isNullOrEmpty(nome)) {
            return findAll();
        }
        try {
            log.debug("Buscando voluntários por nome: {}", nome);
            return voluntarioRepository.findByNomeContainingIgnoreCase(nome);
        } catch (RuntimeException e) {
            log.error("Erro ao buscar voluntários por nome '{}': {}", nome, e.getMessage(), e);
            throw new RuntimeException("Falha ao buscar voluntários por nome", e);
        }
    }

    @Override
    public List<Voluntario> findByAreaAtuacao(String areaAtuacao) {
        if (StringUtil.isNullOrEmpty(areaAtuacao)) {
            return findAll();
        }
        try {
            log.debug("Buscando voluntários por área de atuação: {}", areaAtuacao);
            return voluntarioRepository.findByAreaAtuacaoContainingIgnoreCase(areaAtuacao); 
        } catch (RuntimeException e) {
            log.error("Erro ao buscar voluntários por área de atuação '{}': {}", areaAtuacao, e.getMessage(), e);
            throw new RuntimeException("Falha ao buscar voluntários por área de atuação", e);
        }
    }

    // Método auxiliar para validação específica de Voluntario
    private void validarVoluntario(Voluntario voluntario, boolean isUpdate) {
        // Validações herdadas de Usuario (campos comuns)
        if (voluntario == null) {
            throw new RuntimeException("Dados do voluntário não preenchidos.");
        }
        if (StringUtil.isNullOrEmpty(voluntario.getNome())) {
            throw new IllegalArgumentException("Nome do voluntário não pode ser vazio.");
        }
        if (StringUtil.isNullOrEmpty(voluntario.getCpf())) {
            throw new IllegalArgumentException("CPF do voluntário não pode ser vazio.");
        }
        if (StringUtil.isNullOrEmpty(voluntario.getEmail())) {
            throw new IllegalArgumentException("Email do voluntário não pode ser vazio.");
        }
        if (StringUtil.isNullOrEmpty(voluntario.getEndereco())) {
            throw new IllegalArgumentException("Endereço do voluntário não pode ser vazio.");
        }
        if (StringUtil.isNullOrEmpty(voluntario.getCidade())) {
            throw new IllegalArgumentException("Cidade do voluntário não pode ser vazia.");
        }
        if (StringUtil.isNullOrEmpty(voluntario.getEstado())) {
            throw new IllegalArgumentException("Estado do voluntário não pode ser vazio.");
        }

        // Validações específicas de Voluntario
        if (StringUtil.isNullOrEmpty(voluntario.getAreaAtuacao())) {
            throw new IllegalArgumentException("Área de atuação do voluntário não pode ser vazia.");
        }
        if (StringUtil.isNullOrEmpty(voluntario.getDisponibilidade())) {
            throw new IllegalArgumentException("Disponibilidade do voluntário não pode ser vazia.");
        }

        // Valida CPF único (se não for update ou se o CPF foi alterado)
        if (!isUpdate) { // Se for um novo voluntário
            if (usuarioRepository.findByCpf(voluntario.getCpf()).isPresent()) {
                throw new RuntimeException("CPF já cadastrado para outro usuário.");
            }
        } else { // Se for um update
            Optional<Usuario> usuarioComCpfExistente = usuarioRepository.findByCpf(voluntario.getCpf());
            if (usuarioComCpfExistente.isPresent() && !usuarioComCpfExistente.get().getId().equals(voluntario.getId())) {
                throw new RuntimeException("CPF já cadastrado para outro usuário.");
            }
        }
    }
}
