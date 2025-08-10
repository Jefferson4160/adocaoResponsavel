/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.service;

import br.com.ifba.adotante.entity.Adotante;
import br.com.ifba.funcionario.entity.Funcionario;
import br.com.ifba.usuario.entity.Usuario; 
import br.com.ifba.usuario.repository.UsuarioRepository; 
import br.com.ifba.infrastructure.util.StringUtil; 
import br.com.ifba.perfilDeUsuario.entity.PerfilDeUsuario;
import br.com.ifba.perfilDeUsuario.repository.PerfilDeUsuarioRepository;
import br.com.ifba.pessoa.entity.Pessoa;
import br.com.ifba.pessoa.repository.PessoaRepository;
import br.com.ifba.usuario.repository.AdotanteRepository;
import br.com.ifba.usuario.repository.FuncionarioRepository;
import br.com.ifba.usuario.repository.VoluntarioRepository;
import br.com.ifba.voluntario.entity.Voluntario;
import lombok.RequiredArgsConstructor; 
import lombok.extern.slf4j.Slf4j;     
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Transactional; 
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implementação de Serviço para a entidade base Usuario.
 * @author Jefferson S
 */
@Service
@RequiredArgsConstructor
@Slf4j

public class UsuarioService implements UsuarioIService {

    // Injeção de dependência do repositório para acesso ao banco de dados.
    
    private final UsuarioRepository usuarioRepository;
    private final PessoaRepository pessoaRepository;
    private final PerfilDeUsuarioRepository perfilDeUsuarioRepository;
    
    private final FuncionarioRepository funcionarioRepository;
    private final AdotanteRepository adotanteRepository;
    private final VoluntarioRepository voluntarioRepository;
    
    @Override
    public List<Usuario> findAll() {
        try {
            // Retorna a lista completa de usuários.
            return usuarioRepository.findAll();
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro no service ao listar todos os usuários: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        // Validação básica do ID.
        if (id == null) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo para busca.");
        }
        try {
            // Busca o usuário por ID.
            return usuarioRepository.findById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro no service ao buscar usuário por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Usuario> findByNomeContaining(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome para busca não pode ser vazio.");
        }
        try {
            // Chama o novo método do repositório
            return usuarioRepository.findByNomeContainingIgnoreCase(nome);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro no service ao buscar usuário por nome: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Optional<Usuario> findByCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("O CPF para busca não pode ser vazio.");
        }
        try {
            // Chama o novo método do repositório
            return usuarioRepository.findByCpf(cpf);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro no service ao buscar usuário por CPF: " + e.getMessage(), e);
        }
    }

    @Override
    public Usuario save(Usuario usuario) {
        // Lógica de validação pode ser adicionada aqui.
        try {
            // Salva o usuário no banco de dados. Funciona para criação e atualização.
            return usuarioRepository.save(usuario);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro no service ao salvar ou atualizar o usuário: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Usuario usuario) {
        // Lógica de validação pode ser adicionada aqui.
        try {
            // Deleta o usuário.
            usuarioRepository.delete(usuario);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro no service ao deletar o usuário: " + e.getMessage(), e);
        }
    }
    
    @Transactional
    @Override
    public Usuario salvarNovoUsuarioCompleto(Pessoa pessoa, String perfilSelecionado, String tipoUsuario, String areaAtuacao, String cargo, Double salario) {
        log.info("Iniciando salvamento completo do novo usuário.");
        try {
            // 1. Encontrar o Perfil de Usuário
            Optional<PerfilDeUsuario> perfilOptional = perfilDeUsuarioRepository.findByNomeDoPerfil(perfilSelecionado);
            PerfilDeUsuario perfil = perfilOptional.orElseThrow(() -> new RuntimeException("Perfil de usuário não encontrado."));

            Usuario usuarioSalvo = null;

            // 2. Criar e salvar a entidade mais específica
            switch (tipoUsuario) {
                case "Adotante":
                    Adotante adotante = new Adotante();
                    // Copia os dados da Pessoa para a entidade Adotante (que herda de Pessoa)
                    adotante.setNome(pessoa.getNome());
                    adotante.setCpf(pessoa.getCpf());
                    adotante.setEmail(pessoa.getEmail());
                    adotante.setTelefone(pessoa.getTelefone());
                    adotante.setEndereco(pessoa.getEndereco());
                    adotante.setCidade(pessoa.getCidade());
                    adotante.setEstado(pessoa.getEstado());

                    // Seta o perfil e o campo específico
                    adotante.setPerfilDeUsuario(perfil);
                    // Você pode adicionar uma validação aqui se historicoDoAnimal for obrigatório
                    // adotante.setHistoricoDoAnimal(historicoDoAnimal); 

                    // Salva o Adotante. O Hibernate se encarrega de criar as entradas em Pessoa e Usuario.
                    usuarioSalvo = adotanteRepository.save(adotante);
                    break;
                case "Voluntário":
                    Voluntario voluntario = new Voluntario();
                    // Copia os dados da Pessoa
                    voluntario.setNome(pessoa.getNome());
                    voluntario.setCpf(pessoa.getCpf());
                    voluntario.setEmail(pessoa.getEmail());
                    voluntario.setTelefone(pessoa.getTelefone());
                    voluntario.setEndereco(pessoa.getEndereco());
                    voluntario.setCidade(pessoa.getCidade());
                    voluntario.setEstado(pessoa.getEstado());

                    // Seta o perfil e o campo específico
                    voluntario.setPerfilDeUsuario(perfil);
                    voluntario.setAreaDeAtuacao(areaAtuacao);

                    usuarioSalvo = voluntarioRepository.save(voluntario);
                    break;
                case "Funcionário":
                    Funcionario funcionario = new Funcionario();
                    // Copia os dados da Pessoa
                    funcionario.setNome(pessoa.getNome());
                    funcionario.setCpf(pessoa.getCpf());
                    funcionario.setEmail(pessoa.getEmail());
                    funcionario.setTelefone(pessoa.getTelefone());
                    funcionario.setEndereco(pessoa.getEndereco());
                    funcionario.setCidade(pessoa.getCidade());
                    funcionario.setEstado(pessoa.getEstado());

                    // Seta o perfil e os campos específicos
                    funcionario.setPerfilDeUsuario(perfil);
                    funcionario.setCargo(cargo);
                    funcionario.setSalario(salario);

                    usuarioSalvo = funcionarioRepository.save(funcionario);
                    break;
            }

            log.info("Salvamento completo concluído para o usuário: {}", pessoa.getNome());
            return usuarioSalvo;
        } catch (RuntimeException e) {
            log.error("Erro fatal ao salvar o novo usuário. Transação será revertida: {}", e.getMessage());
            throw e;
        }
    }
    
    @Override
    public void deleteById(Long id) {
        try {
            usuarioRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro no service ao deletar o usuário: " + e.getMessage(), e);
        }
    }
    
    @Transactional
    @Override
    public Usuario atualizarUsuarioCompleto(Usuario usuarioAtualizado, String tipoUsuario, String areaAtuacao, String cargo, Double salario) {
        log.info("Iniciando atualização completa do usuário com ID: {}", usuarioAtualizado.getId());
        try {
            // 1. Atualizar o Usuario (que agora é a Pessoa também)
            Usuario usuarioSalvo = usuarioRepository.save(usuarioAtualizado);

            // 2. Buscar a entidade específica e atualizar seus campos
            if (usuarioSalvo instanceof Voluntario) {
                Voluntario voluntario = (Voluntario) usuarioSalvo;
                voluntario.setAreaDeAtuacao(areaAtuacao);
                voluntarioRepository.save(voluntario);
            } else if (usuarioSalvo instanceof Funcionario) {
                Funcionario funcionario = (Funcionario) usuarioSalvo;
                funcionario.setCargo(cargo);
                funcionario.setSalario(salario);
                funcionarioRepository.save(funcionario);
            } // Adotante não precisa ser atualizado, pois não tem campos específicos

            log.info("Atualização concluída para o usuário: {}", usuarioAtualizado.getNome());
            return usuarioSalvo;
        } catch (RuntimeException e) {
            log.error("Erro fatal ao atualizar o usuário. Transação será revertida: {}", e.getMessage());
            throw e;
        }
    }
}
