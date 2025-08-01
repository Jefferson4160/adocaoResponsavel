/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.controller;

import br.com.ifba.funcionario.entity.Funcionario;
import br.com.ifba.usuario.service.FuncionarioIService; 
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; 

@Controller
public class FuncionarioController implements FuncionarioIController {

    @Autowired
    private FuncionarioIService funcionarioService;

    @Override
    public List<Funcionario> findAll() {
        return funcionarioService.findAll();
    }

    @Override
    public Optional<Funcionario> findById(Long id) {
        return funcionarioService.findById(id);
    }

    @Override
    public List<Funcionario> findByNomeContaining(String nome) {
        return funcionarioService.findByNomeContaining(nome);
    }

    @Override
    public Funcionario save(Funcionario funcionario) {
        return funcionarioService.save(funcionario);
    }

    @Override
    public Funcionario update(Funcionario funcionario) {
        return funcionarioService.update(funcionario);
    }

    @Override
    public void delete(Long id) {
        
        Optional<Funcionario> funcionarioToDelete = funcionarioService.findById(id);
        if (funcionarioToDelete.isPresent()) {
            funcionarioService.delete(funcionarioToDelete.get());
        } else {
            // Lançar uma exceção ou lidar com o caso de funcionário não encontrado
            throw new RuntimeException("Funcionário com ID " + id + " não encontrado para remoção.");
        }
    }
}
