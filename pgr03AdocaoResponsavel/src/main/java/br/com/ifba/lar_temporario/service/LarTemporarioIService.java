/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.lar_temporario.service;
import br.com.ifba.lar_temporario.entity.LarTemporario;
import java.util.List;
/**
 *
 * @author a1591
 */
public interface LarTemporarioIService {
    LarTemporario save(LarTemporario lar);

    LarTemporario update(LarTemporario lar);

    void delete(LarTemporario lar);

    List<LarTemporario> findAll();

    LarTemporario findById(Long id);
}
