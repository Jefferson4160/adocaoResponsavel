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
import br.com.ifba.denuncia.domain.service.IDenunciaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Controlador para a entidade Denuncia.
 * @author luis
 */
@Controller
public class DenunciaController implements IDenunciaController {

    @Autowired
    private IDenunciaService denunciaService;

    @Override
    public Denuncia saveDenuncia(Denuncia denuncia) {
        return denunciaService.saveDenuncia(denuncia);
    }

    @Override
    public Denuncia updateDenuncia(Denuncia denuncia) {
        return denunciaService.updateDenuncia(denuncia);
    }

    @Override
    public List<Denuncia> findAllDenuncias() {
        return denunciaService.findAllDenuncias();
    }

    @Override
    public Denuncia findById(Long id) {
        return denunciaService.findById(id);
    }

    @Override
    public List<Denuncia> findByCpf(String cpf) {
        return denunciaService.findByCpf(cpf);
    }
    
    @Override
    public List<Denuncia> findByAdotanteId(Long id) {
        return denunciaService.findByAdotanteId(id);
    }
}
