/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.com.ifba.registro_interesse.repository;

import br.com.ifba.registro_interesse.entity.RegistroInteresse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author inque
 */
@Repository
public interface RegistroInteresseRepository extends JpaRepository<RegistroInteresse, Long>{
    
    //exemplo de consulta
    //List<RegistroInteresse> findByStatusInteresse(RegistroInteresse.StatusInteresse status);
    //List<RegistroInteresse> findByPessoaId(Long pessoaId);

    
}
