/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.service;

import br.com.ifba.adotante.entity.Adotante;
import br.com.ifba.usuario.repository.AdotanteRepository; 
import br.com.ifba.usuario.repository.UsuarioRepository; 
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
public class AdotanteService implements AdotanteIService {

    private final AdotanteRepository adotanteRepository;
    private final UsuarioRepository usuarioRepository; // Para verificar CPF (todos usuários)

    @Override
    @Transactional
    public Adotante save(Adotante adotante) throws RuntimeException {
        validarAdotante(adotante, false);

        try {
            log.info("Salvando novo adotante: {}", adotante.getNome());
            return adotanteRepository.save(adotante);
        } catch (Exception e) {
            log.error("Erro ao salvar adotante: {}", e.getMessage(), e);
            throw new RuntimeException("Falha ao salvar adotante", e);
        }
    }

    @Override
    @Transactional
    public Adotante update(Adotante adotante) throws RuntimeException {
        validarAdotante(adotante, true);

        Optional<Adotante> optionalAdotante = adotanteRepository.findById(adotante.getId());
        Adotante adotanteExistente = optionalAdotante.orElse(null);

        if (adotanteExistente == null) {
            throw new RuntimeException("Adotante com ID " + adotante.getId() + " nao encontrado para atualização.");
        }
        // Atualiza campos específicos de Adotante
        adotanteExistente.setHistoricoAnimal(adotante.getHistoricoAnimal());

        // Atualiza campos herdados de Usuario
        adotanteExistente.setNome(adotante.getNome());
        adotanteExistente.setCpf(adotante.getCpf());
        adotanteExistente.setEmail(adotante.getEmail());
        adotanteExistente.setTelefone(adotante.getTelefone());
        adotanteExistente.setEndereco(adotante.getEndereco());
        adotanteExistente.setCidade(adotante.getCidade());
        adotanteExistente.setEstado(adotante.getEstado());
        adotanteExistente.setTipoAcesso(adotante.getTipoAcesso()); // Atualiza o tipo de acesso

        try {
            log.info("Atualizando adotante com ID: {}", adotante.getId());
            return adotanteRepository.save(adotanteExistente);
        } catch (RuntimeException e) {
            log.error("Erro ao atualizar adotante com ID {}: {}", adotante.getId(), e.getMessage(), e);
            throw new RuntimeException("Falha ao atualizar adotante", e);
        }
    }

    @Override
    @Transactional
    public void delete(Adotante adotante) throws RuntimeException {
        Optional<Adotante> optionalAdotante = adotanteRepository.findById(adotante.getId());
        Adotante adotanteExistente = optionalAdotante.orElse(null);

        if (adotanteExistente == null) {
            throw new RuntimeException("Adotante com ID" + adotante.getId() + " nao encontrado para remoção.");
        }
        try {
            log.info("Removendo adotante com ID: {}", adotante.getId());
            adotanteRepository.delete(adotanteExistente);
        } catch (RuntimeException e) {
            log.error("Erro ao remover adotante com ID {}: {}", adotante.getId(), e.getMessage(), e);
            throw new RuntimeException("Falha ao remover adotante", e);
        }
    }

    @Override
    public List<Adotante> findAll() {
        try {
            log.debug("Listando todos os adotantes...");
            return adotanteRepository.findAll();
        } catch (RuntimeException e) {
            log.error("Erro ao listar todos os adotantes: {}", e.getMessage(), e);
            throw new RuntimeException("Falha ao listar todos os adotantes", e);
        }
    }

    @Override
    public Optional<Adotante> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do adotante não pode ser nulo para busca.");
        }
        try {
            log.debug("Buscando adotante por ID: {}", id);
            return adotanteRepository.findById(id);
        } catch (RuntimeException e) {
            log.error("Erro ao buscar adotante por ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Falha ao buscar adotante por ID", e);
        }
    }

    @Override
    public List<Adotante> findByNomeContaining(String nome) {
        if (StringUtil.isNullOrEmpty(nome)) {
            return findAll();
        }
        try {
            log.debug("Buscando adotantes por nome: {}", nome);
            return adotanteRepository.findByNomeContainingIgnoreCase(nome);
        } catch (RuntimeException e) {
            log.error("Erro ao buscar adotantes por nome '{}': {}", nome, e.getMessage(), e);
            throw new RuntimeException("Falha ao buscar adotantes por nome", e);
        }
    }

    // Método auxiliar para validação específica de Adotante
    private void validarAdotante(Adotante adotante, boolean isUpdate) {
        // Validações herdadas de Usuario (campos comuns)
        if (adotante == null) {
            throw new RuntimeException("Dados do adotante não preenchidos.");
        }
        if (StringUtil.isNullOrEmpty(adotante.getNome())) {
            throw new IllegalArgumentException("Nome do adotante não pode ser vazio.");
        }
        if (StringUtil.isNullOrEmpty(adotante.getCpf())) {
            throw new IllegalArgumentException("CPF do adotante não pode ser vazio.");
        }
        if (StringUtil.isNullOrEmpty(adotante.getEmail())) {
            throw new IllegalArgumentException("Email do adotante não pode ser vazio.");
        }
        if (StringUtil.isNullOrEmpty(adotante.getEndereco())) {
            throw new IllegalArgumentException("Endereço do adotante não pode ser vazio.");
        }
        if (StringUtil.isNullOrEmpty(adotante.getCidade())) {
            throw new IllegalArgumentException("Cidade do adotante não pode ser vazia.");
        }
        if (StringUtil.isNullOrEmpty(adotante.getEstado())) {
            throw new IllegalArgumentException("Estado do adotante não pode ser vazio.");
        }

        // Valida CPF único (se não for update ou se o CPF foi alterado)
        if (!isUpdate) { // Se for um novo adotante
            if (usuarioRepository.findByCpf(adotante.getCpf()).isPresent()) {
                throw new RuntimeException("CPF já cadastrado para outro usuário.");
            }
        } else { // Se for um update
            Optional<Usuario> usuarioComCpfExistente = usuarioRepository.findByCpf(adotante.getCpf());
            if (usuarioComCpfExistente.isPresent() && !usuarioComCpfExistente.get().getId().equals(adotante.getId())) {
                throw new RuntimeException("CPF já cadastrado para outro usuário.");
            }
        }
    }
    
    // Metodo de busca para adotantes com denuncias
    @Override
    public List<Adotante> findAdotantesComDenuncias() {
        try {
            log.debug("Listando todos os adotantes com denúncias...");
            return adotanteRepository.findAdotantesComDenuncias();
        } catch (RuntimeException e) {
            log.error("Erro ao listar adotantes com denúncias: {}", e.getMessage(), e);
            throw new RuntimeException("Falha ao listar adotantes com denúncias", e);
        }
    }
}