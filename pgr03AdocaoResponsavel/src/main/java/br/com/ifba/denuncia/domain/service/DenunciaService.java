/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.denuncia.domain.service;

/**
 *
 * @author luis2
 */

import br.com.ifba.denuncia.domain.entity.Denuncia;
import br.com.ifba.denuncia.domain.repository.IDenunciaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementação da lógica de negócio para Denuncia.
 * @author luis
 */
@Service
public class DenunciaService implements IDenunciaService {

    @Autowired
    private IDenunciaRepository denunciaRepository;

    @Override
    public Denuncia saveDenuncia(Denuncia denuncia) {
        return denunciaRepository.save(denuncia);
    }

    @Override
    public Denuncia updateDenuncia(Denuncia denuncia) {
        // Garante que a denúncia existe antes de tentar atualizar
        if (!denunciaRepository.existsById(denuncia.getId())) {
             throw new RuntimeException("Denúncia não encontrada no banco de dados.");
        }
        return denunciaRepository.save(denuncia);
    }

    @Override
    public List<Denuncia> findAllDenuncias() {
        return denunciaRepository.findAll();
    }

    @Override
    public Denuncia findById(Long id) {
        return denunciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Denúncia não encontrada com o ID: " + id));
    }
    
    @Override
    public List<Denuncia> findByCpf(String cpf) {
        return denunciaRepository.findByDenunciadoCpf(cpf);
    }
    
    @Override
    public List<Denuncia> findByAdotanteId(Long id) {
        return denunciaRepository.findByDenunciadoId(id);
    }
}
