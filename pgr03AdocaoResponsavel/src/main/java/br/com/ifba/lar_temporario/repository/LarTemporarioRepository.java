/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.lar_temporario.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ifba.lar_temporario.entity.LarTemporario;
/**
 *
 * @author Luan Alves
 */
public interface LarTemporarioRepository extends JpaRepository<LarTemporario, Long>{
    
}
