/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.lar_temporario.service;


import br.com.ifba.lar_temporario.entity.LarTemporario;
import br.com.ifba.lar_temporario.repository.LarTemporarioRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Luan Alves
 */
@Service
@RequiredArgsConstructor
public class LarTemporarioService implements LarTemporarioIService {

    @Autowired
    private final LarTemporarioRepository larTemporarioRepository;

    private static final Logger log = LoggerFactory.getLogger(LarTemporarioService.class);

    @Override
    public LarTemporario save(LarTemporario lar) throws RuntimeException {
        log.info("Tentando salvar Lar Temporário: {}", lar);

        if (lar == null || lar.getPessoa() == null) {
            log.info("Tentativa de salvar lar nulo ou sem pessoa.");
            throw new RuntimeException("Dados do lar não preenchidos.");
        }

        if (lar.getId() != null && larTemporarioRepository.existsById(lar.getId())) {
            log.info("Tentativa de salvar lar já existente com ID: {}", lar.getId());
            throw new RuntimeException("Lar já existente no banco de dados.");
        }

        if (lar.getQuantidadeMaxAnimais() <= 0) {
            log.info("Quantidade máxima de animais inválida.");
            throw new RuntimeException("Quantidade máxima de animais deve ser maior que 0.");
        }

        LarTemporario salvo = larTemporarioRepository.save(lar);
        log.info("Lar Temporário salvo com sucesso: {}", salvo);
        return salvo;
    }

    @Override
    public LarTemporario update(LarTemporario lar) throws RuntimeException {
        log.info("Tentando atualizar Lar Temporário: {}", lar);

        if (lar == null || lar.getId() == null) {
            log.info("Tentativa de atualizar lar inválido ou sem ID.");
            throw new RuntimeException("Lar inválido para atualização.");
        }

        LarTemporario atualizado = larTemporarioRepository.save(lar);
        log.info("Lar Temporário atualizado com sucesso: {}", atualizado);
        return atualizado;
    }

    @Override
    public void delete(LarTemporario lar) throws RuntimeException {
        log.info("Tentando deletar Lar Temporário: {}", lar);

        if (lar == null || lar.getId() == null) {
            log.info("Tentativa de deletar lar inválido.");
            throw new RuntimeException("Lar inválido para exclusão.");
        }

        larTemporarioRepository.delete(lar);
        log.info("Lar Temporário deletado com sucesso.");
    }

    @Override
    public List<LarTemporario> findAll() {
        log.info("Buscando todos os Lares Temporários");
        return larTemporarioRepository.findAll();
    }

    @Override
    public LarTemporario findById(Long id) {
        log.info("Buscando Lar Temporário por ID: {}", id);
        return larTemporarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Lar Temporário não encontrado com o ID fornecido."));
    }
}
