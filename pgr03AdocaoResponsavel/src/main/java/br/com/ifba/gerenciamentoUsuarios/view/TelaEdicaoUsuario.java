/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package br.com.ifba.gerenciamentoUsuarios.view;

import br.com.ifba.perfilDeUsuario.controller.PerfilDeUsuarioIController;
import br.com.ifba.perfilDeUsuario.entity.PerfilDeUsuario;
import br.com.ifba.usuario.controller.UsuarioIController;
import br.com.ifba.usuario.controller.AdotanteIController;
import br.com.ifba.usuario.controller.FuncionarioIController;
import br.com.ifba.usuario.controller.VoluntarioIController;
import br.com.ifba.adotante.entity.Adotante;
import br.com.ifba.funcionario.entity.Funcionario;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.voluntario.entity.Voluntario;
import jakarta.annotation.PostConstruct;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.util.Optional;

/**
 *
 * @author Jefferson S
 */
public class TelaEdicaoUsuario extends javax.swing.JDialog {

    private final Usuario usuarioOriginal;
    private final PerfilDeUsuarioIController perfilDeUsuarioController;
    private final AdotanteIController adotanteController;
    private final FuncionarioIController funcionarioController;
    private final VoluntarioIController voluntarioController;
    private final UsuarioIController usuarioController;

    public TelaEdicaoUsuario(JFrame parent, boolean modal,
                             Usuario usuario,
                             PerfilDeUsuarioIController perfilDeUsuarioController,
                             AdotanteIController adotanteController,
                             FuncionarioIController funcionarioController,
                             VoluntarioIController voluntarioController,
                             UsuarioIController usuarioController) {
        super(parent, modal);
        this.usuarioOriginal = usuario;
        this.perfilDeUsuarioController = perfilDeUsuarioController;
        this.adotanteController = adotanteController;
        this.funcionarioController = funcionarioController;
        this.voluntarioController = voluntarioController;
        this.usuarioController = usuarioController;
        initComponents();
        init();
    }

    @PostConstruct
    public void init() {
        this.setLocationRelativeTo(null);
        carregarPerfisDeUsuario();
        carregarDadosDoUsuario();
        ajustarCamposParaTipoUsuario();
        
        btnSalvar.addActionListener(this::salvarEdicao);
        btnCancelar.addActionListener(e -> dispose());
    }
    
    private void carregarPerfisDeUsuario() {
        try {
            List<PerfilDeUsuario> perfis = perfilDeUsuarioController.buscarTodosPerfis();
            boxPerfil.removeAllItems();
            if (perfis != null && !perfis.isEmpty()) {
                for (PerfilDeUsuario perfil : perfis) {
                    boxPerfil.addItem(perfil.getNomeDoPerfil());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum perfil de usuário encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                btnSalvar.setEnabled(false);
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar perfis de usuário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void carregarDadosDoUsuario() {
        if (usuarioOriginal != null) {
            textNome.setText(usuarioOriginal.getNome());
            textCpf.setText(usuarioOriginal.getCpf());
            textCpf.setEditable(false); // CPF não deve ser editável
            textEmail.setText(usuarioOriginal.getEmail());
            textTelefone.setText(usuarioOriginal.getTelefone());
            textEndereco.setText(usuarioOriginal.getEndereco());
            textCidade.setText(usuarioOriginal.getCidade());
            textEstado.setText(usuarioOriginal.getEstado());
            
            if (usuarioOriginal.getPerfilDeUsuario() != null) {
                boxPerfil.setSelectedItem(usuarioOriginal.getPerfilDeUsuario().getNomeDoPerfil());
            }
            
            // Carregar dados específicos do tipo de usuário
            if (usuarioOriginal instanceof Adotante) {
                // Não há campos específicos para Adotante, então nada a fazer aqui
            } else if (usuarioOriginal instanceof Funcionario) {
                Funcionario funcionario = (Funcionario) usuarioOriginal;
                lblCargo.setVisible(true);
                textCargo.setVisible(true);
                textCargo.setText(funcionario.getCargo());
                lblSalario.setVisible(true);
                textSalario.setVisible(true);
                textSalario.setText(String.format("%.2f", funcionario.getSalario()).replace(".", ","));
            } else if (usuarioOriginal instanceof Voluntario) {
                Voluntario voluntario = (Voluntario) usuarioOriginal;
                lblArea.setVisible(true);
                textArea.setVisible(true);
                textArea.setText(voluntario.getAreaDeAtuacao());
            }
        }
    }
    

    private void salvarEdicao(ActionEvent evt) {
        String nome = textNome.getText().trim();
        String email = textEmail.getText().trim();
        String telefone = textTelefone.getText().trim();
        String endereco = textEndereco.getText().trim();
        String cidade = textCidade.getText().trim();
        String estado = textEstado.getText().trim();
        String perfilSelecionado = (String) boxPerfil.getSelectedItem();

        // 1. Validação dos dados
        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty() || endereco.isEmpty() || cidade.isEmpty() || estado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. CORREÇÃO: MODIFICAMOS O OBJETO ORIGINAL, NÃO CRIAMOS UM NOVO
        usuarioOriginal.setNome(nome);
        usuarioOriginal.setEmail(email);
        usuarioOriginal.setTelefone(telefone);
        usuarioOriginal.setEndereco(endereco);
        usuarioOriginal.setCidade(cidade);
        usuarioOriginal.setEstado(estado);

        String tipoUsuario = "";
        String areaAtuacao = "";
        String cargo = "";
        Double salario = null;

        if (usuarioOriginal instanceof Voluntario) {
            tipoUsuario = "Voluntário";
            areaAtuacao = textArea.getText().trim();
        } else if (usuarioOriginal instanceof Funcionario) {
            tipoUsuario = "Funcionário";
            cargo = textCargo.getText().trim();
            try {
                salario = Double.parseDouble(textSalario.getText().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "O salário deve ser um número válido. Ex: 4000,00", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else if (usuarioOriginal instanceof Adotante) {
            tipoUsuario = "Adotante";
        }

        try {
            // 3. Encontrar o perfil selecionado
            Optional<PerfilDeUsuario> perfilOpt = perfilDeUsuarioController.buscarPorNomeExato(perfilSelecionado);
            if (perfilOpt.isEmpty()) {
                throw new RuntimeException("Perfil de usuário não encontrado.");
            }
            usuarioOriginal.setPerfilDeUsuario(perfilOpt.get());

            // 4. Chamar o método de serviço para atualizar tudo em uma transação
            // Passamos o objeto ORIGINAL modificado
            usuarioController.atualizarUsuarioCompleto(usuarioOriginal, tipoUsuario, areaAtuacao, cargo, salario);

            JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar usuário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ajustarCamposParaTipoUsuario() {
        // Esconde todos os campos específicos por padrão
        lblArea.setVisible(false);
        textArea.setVisible(false);
        lblCargo.setVisible(false);
        textCargo.setVisible(false);
        lblSalario.setVisible(false);
        textSalario.setVisible(false);

        // Identifica o tipo de usuário e exibe os campos corretos
        if (usuarioOriginal instanceof Voluntario) {
            Voluntario voluntario = (Voluntario) usuarioOriginal;
            lblArea.setVisible(true);
            textArea.setVisible(true);
            textArea.setText(voluntario.getAreaDeAtuacao());
        } else if (usuarioOriginal instanceof Funcionario) {
            Funcionario funcionario = (Funcionario) usuarioOriginal;
            lblCargo.setVisible(true);
            textCargo.setVisible(true);
            textCargo.setText(funcionario.getCargo());
            lblSalario.setVisible(true);
            textSalario.setVisible(true);
            textSalario.setText(String.format("%.2f", funcionario.getSalario()).replace(".", ","));
        }
        // Adotante não possui campos específicos, então nada é exibido para ele.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textCidade = new javax.swing.JTextField();
        lblArea = new javax.swing.JLabel();
        lblEndereco = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        lblPerfil = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        boxPerfil = new javax.swing.JComboBox<>();
        lblCargo = new javax.swing.JLabel();
        lblCidade = new javax.swing.JLabel();
        textNome = new javax.swing.JTextField();
        lblEstado = new javax.swing.JLabel();
        textCargo = new javax.swing.JTextField();
        textEmail = new javax.swing.JTextField();
        lblTelefone = new javax.swing.JLabel();
        lblNome = new javax.swing.JLabel();
        lblSalario = new javax.swing.JLabel();
        textTelefone = new javax.swing.JTextField();
        textCpf = new javax.swing.JTextField();
        textEstado = new javax.swing.JTextField();
        textSalario = new javax.swing.JTextField();
        textEndereco = new javax.swing.JTextField();
        btnSalvar = new javax.swing.JButton();
        lblCpf = new javax.swing.JLabel();
        textArea = new javax.swing.JTextField();
        lblTitulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        textCidade.setText("Irecê");

        lblArea.setText("Área de atuação");

        lblEndereco.setText("Endereço:");

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        lblPerfil.setText("Perfil:");

        lblEmail.setText("E-mail:");

        boxPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblCargo.setText("Cargo:");

        lblCidade.setText("Cidade:");

        textNome.setText("nome completo...");

        lblEstado.setText("Estado:");

        textCargo.setText("nome do cargo...");
        textCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textCargoActionPerformed(evt);
            }
        });

        textEmail.setText("exemplo@dominio.com");

        lblTelefone.setText("Telefone:");

        lblNome.setText("Nome:");

        lblSalario.setText("Salário");

        textTelefone.setText("somente digitos, sem espaços...");

        textCpf.setText("somente números...");

        textEstado.setText("BA");

        textSalario.setText("digitos separados por vírgula: 4000,00");
        textSalario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSalarioActionPerformed(evt);
            }
        });

        textEndereco.setText("Rua exemplo N 01...");

        btnSalvar.setText("Salvar");

        lblCpf.setText("CPF:");

        textArea.setText("limpeza, medicaçao...");
        textArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textAreaActionPerformed(evt);
            }
        });

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTitulo.setText("Edição de cadastro");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(184, 184, 184))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNome)
                            .addComponent(lblTelefone)
                            .addComponent(lblEndereco)
                            .addComponent(lblCidade)
                            .addComponent(lblEstado)
                            .addComponent(lblEmail)
                            .addComponent(lblPerfil))
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(boxPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textNome, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(btnCancelar)
                        .addGap(47, 47, 47)
                        .addComponent(btnSalvar))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblArea)
                        .addGap(18, 18, 18)
                        .addComponent(textArea, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCargo)
                            .addComponent(lblSalario))
                        .addGap(68, 68, 68)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textSalario, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(textCargo))))
                .addGap(81, 81, 81))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblTitulo)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boxPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPerfil))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNome))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCpf))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTelefone))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEndereco))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCidade))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEstado))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblArea)
                    .addComponent(textArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCargo)
                    .addComponent(textCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textSalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSalario))
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar)
                    .addComponent(btnSalvar))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void textCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textCargoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textCargoActionPerformed

    private void textSalarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSalarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSalarioActionPerformed

    private void textAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textAreaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textAreaActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> boxPerfil;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel lblArea;
    private javax.swing.JLabel lblCargo;
    private javax.swing.JLabel lblCidade;
    private javax.swing.JLabel lblCpf;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEndereco;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblPerfil;
    private javax.swing.JLabel lblSalario;
    private javax.swing.JLabel lblTelefone;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField textArea;
    private javax.swing.JTextField textCargo;
    private javax.swing.JTextField textCidade;
    private javax.swing.JTextField textCpf;
    private javax.swing.JTextField textEmail;
    private javax.swing.JTextField textEndereco;
    private javax.swing.JTextField textEstado;
    private javax.swing.JTextField textNome;
    private javax.swing.JTextField textSalario;
    private javax.swing.JTextField textTelefone;
    // End of variables declaration//GEN-END:variables
}
