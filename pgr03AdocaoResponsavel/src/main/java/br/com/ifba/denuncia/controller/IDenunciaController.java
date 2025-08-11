/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.denuncia.controller;

/**
 *
 * @author luis2
 */

import br.com.ifba.denuncia.domain.entity.Denuncia;
import java.util.List;

/**
 * Interface que define o contrato do controlador para as operações de Denúncia.
 * @author luis
 */
public interface IDenunciaController {
    
    Denuncia saveDenuncia(Denuncia denuncia);
    
    Denuncia updateDenuncia(Denuncia denuncia);
    
    List<Denuncia> findAllDenuncias();
    
    Denuncia findById(Long id);
    
    List<Denuncia> findByCpf(String cpf);
    
    List<Denuncia> findByAdotanteId(Long id);
}
