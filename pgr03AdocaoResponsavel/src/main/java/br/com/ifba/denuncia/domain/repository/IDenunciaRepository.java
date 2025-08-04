/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.denuncia.domain.repository;

/**
 *
 * @author luis2
 */

import br.com.ifba.denuncia.domain.entity.Denuncia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade Denuncia.
 * @author luis
 */
@Repository
public interface IDenunciaRepository extends JpaRepository<Denuncia, Long> {
    
    // Encontra denúncias com base no CPF do adotante denunciado
    List<Denuncia> findByDenunciadoCpf(String cpf);
    
    // Encontra denúncias com base no ID do adotante denunciado
    List<Denuncia> findByDenunciadoId(Long id);
}
