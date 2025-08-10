/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.usuario.controller;

import br.com.ifba.pessoa.entity.Pessoa;
import br.com.ifba.usuario.entity.Usuario;
import java.util.List;
import java.util.Optional;

/**
 * Interface que define o contrato da camada de Controle para operações de Usuários.
 * @author Jefferson S
 */
// Interface que define o contrato do controlador para as operações de Usuário.
public interface UsuarioIController {
    
    // Método para salvar um novo usuário.
    Usuario salvarUsuario(Usuario usuario);
    
    // Método para atualizar um usuário existente.
    Usuario atualizarUsuario(Usuario usuario);
    
    // Método para deletar um usuário.
    void deletarUsuario(Usuario usuario);
    
    // Método para buscar um usuário pelo seu ID.
    Optional<Usuario> buscarUsuarioPorId(Long id);
    
    // Método para buscar todos os usuários.
    List<Usuario> buscarTodosUsuarios();
    
    // Método para buscar usuários por parte do nome da pessoa associada.
    List<Usuario> buscarUsuarioPorNome(String nome);
    
    // Método para buscar um usuário por CPF.
    Optional<Usuario> buscarUsuarioPorCpf(String cpf);
    
    Usuario salvarNovoUsuarioCompleto(Pessoa pessoa, String perfilSelecionado, String tipoUsuario, String areaAtuacao, String cargo, Double salario);
}
