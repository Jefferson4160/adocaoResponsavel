/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.controller;

import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.service.UsuarioService; // Importar a interface de serviço
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired; // Para injeção de dependência
import org.springframework.stereotype.Controller;


@Controller 
public class UsuarioController implements UsuarioIController {

    @Autowired
    private UsuarioService usuarioService;

    
    @Override
    public List<Usuario> findAll() {
        // Log ou tratamento de requisição antes de chamar o serviço
        System.out.println("Requisição para buscar todos os usuários recebida no Controller.");
        return usuarioService.findAll();
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        System.out.println("Requisição para buscar usuário pelo ID: " + id + " recebida no Controller.");
        return usuarioService.findById(id);
    }

    @Override
    public List<Usuario> findByNomeContaining(String nome) {
        System.out.println("Requisição para buscar usuários pelo nome contendo: " + nome + " recebida no Controller.");
        return usuarioService.findByNomeContaining(nome);
    }

    @Override
    public Optional<Usuario> findByCpf(String cpf) {
        System.out.println("Requisição para buscar usuário pelo CPF: " + cpf + " recebida no Controller.");
        return usuarioService.findByCpf(cpf);
    }
}
