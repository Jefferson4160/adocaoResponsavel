/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package br.com.ifba.gerenciamentoUsuarios.view;

import br.com.ifba.perfilDeUsuario.controller.PerfilDeUsuarioIController;
import br.com.ifba.usuario.controller.VoluntarioIController;
import br.com.ifba.usuario.controller.AdotanteIController;
import br.com.ifba.usuario.controller.FuncionarioIController;
import br.com.ifba.perfilDeUsuario.entity.PerfilDeUsuario;
import br.com.ifba.pessoa.entity.Pessoa; // Pacote da entidade Pessoa
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.adotante.entity.Adotante;
import br.com.ifba.voluntario.entity.Voluntario;
import br.com.ifba.funcionario.entity.Funcionario;
import br.com.ifba.pessoa.controller.PessoaIController;
import br.com.ifba.usuario.controller.UsuarioIController;
import jakarta.annotation.PostConstruct;
import java.awt.event.ItemEvent;
import java.util.List;
import javax.swing.JOptionPane;
import java.util.Optional;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;

/**
 *
 * @author Jefferson S
 */
public class NovoUsuario extends javax.swing.JDialog {

    private final AdotanteIController adotanteController;
    private final FuncionarioIController funcionarioController;
    private final VoluntarioIController voluntarioController;
    private final PerfilDeUsuarioIController perfilDeUsuarioController;
    private final UsuarioIController usuarioController;
    private final PessoaIController pessoaController;
    
    
    public NovoUsuario(JFrame parent, boolean modal, 
                       AdotanteIController adotanteController, 
                       FuncionarioIController funcionarioController, 
                       VoluntarioIController voluntarioController, 
                       PerfilDeUsuarioIController perfilDeUsuarioController,
                       PessoaIController pessoaController,
                       UsuarioIController usuarioController) {
        super(parent, modal);
        this.adotanteController = adotanteController;
        this.funcionarioController = funcionarioController;
        this.voluntarioController = voluntarioController;
        this.perfilDeUsuarioController = perfilDeUsuarioController;
        this.pessoaController = pessoaController;
        this.usuarioController = usuarioController;
        initComponents();
        init();
    }

    @PostConstruct
    public void init() {
        this.setLocationRelativeTo(null);
        carregarPerfisDeUsuario();
        
        boxTipoUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Adotante", "Voluntário", "Funcionário"}));
        boxTipoUsuario.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String tipoSelecionado = (String) e.getItem();
                ajustarCampos(tipoSelecionado);
            }
        });
        
        boxTipoUsuario.setSelectedIndex(0);
        ajustarCampos("Adotante");
        
        btnSalvar.addActionListener(this::salvarNovoUsuario);
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
                // Se não houver perfis, avisa o usuário (embora a tela principal já faça isso)
                JOptionPane.showMessageDialog(this, "Nenhum perfil de usuário encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                btnSalvar.setEnabled(false); // Desabilita o botão de salvar
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar perfis de usuário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ajustarCampos(String tipo) {
        lblArea.setVisible(false);
        textArea.setVisible(false);
        lblCargo.setVisible(false);
        textCargo.setVisible(false);
        lblSalario.setVisible(false);
        textSalario.setVisible(false);

        switch (tipo) {
            case "Voluntário":
                lblArea.setVisible(true);
                textArea.setVisible(true);
                break;
            case "Funcionário":
                lblCargo.setVisible(true);
                textCargo.setVisible(true);
                lblSalario.setVisible(true);
                textSalario.setVisible(true);
                break;
        }
    }
    
    // Em br.com.ifba.gerenciamentoUsuarios.view.NovoUsuario.java
private void salvarNovoUsuario(ActionEvent evt) {
    try {
        String tipoSelecionado = (String) boxTipoUsuario.getSelectedItem();
        String perfilSelecionadoNome = (String) boxPerfil.getSelectedItem();

        // 1. Busque o perfil de usuário
        Optional<PerfilDeUsuario> perfilOpt = perfilDeUsuarioController.buscarPorNome(perfilSelecionadoNome).stream().findFirst();
        if (perfilOpt.isEmpty()) {
            throw new RuntimeException("Perfil de usuário não encontrado.");
        }
        PerfilDeUsuario perfil = perfilOpt.get();

        // 2. Cria a entidade Pessoa
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(textNome.getText());
        pessoa.setCpf(textCpf.getText());
        pessoa.setEmail(textEmail.getText());
        pessoa.setTelefone(textTelefone.getText());
        pessoa.setEndereco(textEndereco.getText());
        pessoa.setCidade(textCidade.getText());
        pessoa.setEstado(textEstado.getText());

        // 3. Cria o Usuario e o associa à Pessoa
        Usuario novoUsuario = new Usuario();
        novoUsuario.setPessoa(pessoa);
        novoUsuario.setPerfilDeUsuario(perfil);

        // 4. Salva a subclasse diretamente
        switch (tipoSelecionado) {
            case "Adotante":
                Adotante adotante = new Adotante();
                adotante.setNome(pessoa.getNome());
                adotante.setCpf(pessoa.getCpf());
                adotante.setEmail(pessoa.getEmail());
                adotante.setTelefone(pessoa.getTelefone());
                adotante.setEndereco(pessoa.getEndereco());
                adotante.setCidade(pessoa.getCidade());
                adotante.setEstado(pessoa.getEstado());
                adotante.setUsuario(novoUsuario);
                adotanteController.salvarAdotante(adotante);
                break;
            case "Voluntário":
                Voluntario voluntario = new Voluntario();
                voluntario.setNome(pessoa.getNome());
                voluntario.setCpf(pessoa.getCpf());
                voluntario.setEmail(pessoa.getEmail());
                voluntario.setTelefone(pessoa.getTelefone());
                voluntario.setEndereco(pessoa.getEndereco());
                voluntario.setCidade(pessoa.getCidade());
                voluntario.setEstado(pessoa.getEstado());
                voluntario.setAreaDeAtuacao(textArea.getText());
                voluntario.setUsuario(novoUsuario);
                voluntarioController.salvarVoluntario(voluntario);
                break;
            case "Funcionário":
                Funcionario funcionario = new Funcionario();
                funcionario.setNome(pessoa.getNome());
                funcionario.setCpf(pessoa.getCpf());
                funcionario.setEmail(pessoa.getEmail());
                funcionario.setTelefone(pessoa.getTelefone());
                funcionario.setEndereco(pessoa.getEndereco());
                funcionario.setCidade(pessoa.getCidade());
                funcionario.setEstado(pessoa.getEstado());
                funcionario.setCargo(textCargo.getText());
                funcionario.setSalario(Double.parseDouble(textSalario.getText().replace(",", ".")));
                funcionario.setUsuario(novoUsuario);
                funcionarioController.salvarFuncionario(funcionario);
                break;
        }

        JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        dispose();

    } catch (RuntimeException e) {
        JOptionPane.showMessageDialog(this, "Erro ao salvar usuário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
   
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem2 = new javax.swing.JRadioButtonMenuItem();
        textCidade = new javax.swing.JTextField();
        lblTitulo = new javax.swing.JLabel();
        lblEndereco = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        lblEmail = new javax.swing.JLabel();
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
        lblArea = new javax.swing.JLabel();
        lblTipoUsuario = new javax.swing.JLabel();
        boxTipoUsuario = new javax.swing.JComboBox<>();
        lblPerfil = new javax.swing.JLabel();
        boxPerfil = new javax.swing.JComboBox<>();

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        jRadioButtonMenuItem2.setSelected(true);
        jRadioButtonMenuItem2.setText("jRadioButtonMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        textCidade.setText("Irecê");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTitulo.setText("Novo cadastro");

        lblEndereco.setText("Endereço:");

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        lblEmail.setText("E-mail:");

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

        lblArea.setText("Área de atuação");

        lblTipoUsuario.setText("Tipo de usuário:");

        boxTipoUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblPerfil.setText("Perfil:");

        boxPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(187, 187, 187)
                        .addComponent(btnCancelar)
                        .addGap(47, 47, 47)
                        .addComponent(btnSalvar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCargo)
                                    .addComponent(lblSalario))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textSalario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textCargo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblArea)
                                .addGap(21, 21, 21)
                                .addComponent(textArea))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblCpf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(21, 21, 21)
                                .addComponent(textCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNome)
                                    .addComponent(lblTelefone)
                                    .addComponent(lblEndereco)
                                    .addComponent(lblCidade)
                                    .addComponent(lblEstado)
                                    .addComponent(lblTipoUsuario)
                                    .addComponent(lblPerfil)
                                    .addComponent(lblEmail))
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textNome, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(boxTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(boxPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(40, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(195, 195, 195)
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(lblTitulo)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipoUsuario)
                    .addComponent(boxTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boxPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPerfil))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNome)
                    .addComponent(textNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCpf)
                    .addComponent(textCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(textEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTelefone)
                    .addComponent(textTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEndereco)
                    .addComponent(textEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCidade)
                    .addComponent(textCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEstado)
                    .addComponent(textEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblArea)
                    .addComponent(textArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCargo)
                    .addComponent(textCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSalario)
                    .addComponent(textSalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar)
                    .addComponent(btnSalvar))
                .addGap(19, 19, 19))
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
    private javax.swing.JComboBox<String> boxTipoUsuario;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem2;
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
    private javax.swing.JLabel lblTipoUsuario;
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
