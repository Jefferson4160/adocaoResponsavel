/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.agendaDeVacina.repository;

/**
 *
 * @author ADMIN
 */
import br.com.ifba.agendaDeVacina.entity.AgendaDeVacina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AgendaDeVacinaRepository extends JpaRepository<AgendaDeVacina, Long> {
    
    // Método para buscar as vacinas de um animal específico
    List<AgendaDeVacina> findByAnimal_Id(Long idAnimal);

}
