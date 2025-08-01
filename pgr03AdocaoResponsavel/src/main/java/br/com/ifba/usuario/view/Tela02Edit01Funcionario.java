/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package br.com.ifba.usuario.view;

import br.com.ifba.funcionario.entity.Funcionario;
import br.com.ifba.infrastructure.util.StringUtil;
import br.com.ifba.usuario.controller.FuncionarioIController;
import br.com.ifba.usuario.util.TipoAcesso;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class Tela02Edit01Funcionario extends javax.swing.JDialog {
    
    // --- CAMPO PARA O CONTROLADOR E ID DO FUNCIONÁRIO A SER EDITADO ---
    private FuncionarioIController funcionarioController; // Referência ao controlador de Funcionário
    private Long funcionarioIdAtual; // Para guardar o ID do funcionário que está sendo editado

    /**
     * Creates new form Tela02Edit01Funcionario
     */
    public Tela02Edit01Funcionario(java.awt.Frame parent, boolean modal, Long idFuncionario, FuncionarioIController funcionarioController) { // <--- NOVO CONSTRUTOR
        super(parent, modal);
        initComponents();
        this.funcionarioController = funcionarioController;
        this.funcionarioIdAtual = idFuncionario; // Atribui o ID recebido

        this.setLocationRelativeTo(parent);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        // --- LÓGICA DE CARREGAMENTO DE DADOS ---
        if (this.funcionarioIdAtual != null) {
            lblPerfil.setText("FUNCIONÁRIO (EDIÇÃO)"); // Ajusta o título para edição
            jLabel1.setText("(modo edição)");
            carregarDadosFuncionarioParaEdicao(this.funcionarioIdAtual); // Carrega os dados para edição
        } else {
            lblPerfil.setText("FUNCIONÁRIO (NOVO)"); // Se for usado para novo (sem ID)
            jLabel1.setText("(novo cadastro)");
            // Limpar campos para novo cadastro (se o construtor for usado para isso)
            textNome.setText("");
            textCpf.setText("");
            textEmail.setText("");
            textTelefone.setText("");
            textEndereco.setText("");
            textCidade.setText("");
            textEstado.setText("");
            textCargo.setText("");
            textSalario.setText("");
        }
    }
    
    private void carregarDadosFuncionarioParaEdicao(Long id) {
        try {
            Optional<Funcionario> funcionarioOpt = funcionarioController.findById(id); // Busca o funcionário pelo ID

            if (funcionarioOpt.isPresent()) {
                Funcionario funcionario = funcionarioOpt.get();
                // Preenche os campos comuns de Usuario
                textNome.setText(funcionario.getNome());
                textCpf.setText(funcionario.getCpf());
                textEmail.setText(funcionario.getEmail());
                textTelefone.setText(funcionario.getTelefone());
                textEndereco.setText(funcionario.getEndereco());
                textCidade.setText(funcionario.getCidade());
                textEstado.setText(funcionario.getEstado());
                // Preenche os campos específicos de Funcionario
                textCargo.setText(funcionario.getCargo());
                textSalario.setText(String.valueOf(funcionario.getSalario())); // Converte Double para String
            } else {
                JOptionPane.showMessageDialog(this, "Funcionário com ID " + id + " não encontrado para edição.", "Erro", JOptionPane.ERROR_MESSAGE);
                this.dispose(); // Fecha a tela se não encontrar
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados do funcionário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            this.dispose(); // Fecha em caso de erro
        }
    }

    // botão de salvar
    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {
        // coleta os dados dos campos da tela
        String nome = textNome.getText();
        String cpf = textCpf.getText();
        String email = textEmail.getText();
        String telefone = textTelefone.getText();
        String endereco = textEndereco.getText();
        String cidade = textCidade.getText();
        String estado = textEstado.getText();
        String cargo = textCargo.getText();
        Double salario = null;
        try {
            if (!StringUtil.isNullOrEmpty(textSalario.getText())) {
                 salario = Double.parseDouble(textSalario.getText().replace(",", "."));
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Formato de salário inválido. Use números (ex: 4000,00).", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Funcionario funcionarioParaOperacao = new Funcionario();
        // Atributos herdados de Usuario
        funcionarioParaOperacao.setNome(nome);
        funcionarioParaOperacao.setCpf(cpf);
        funcionarioParaOperacao.setEmail(email);
        funcionarioParaOperacao.setTelefone(telefone);
        funcionarioParaOperacao.setEndereco(endereco);
        funcionarioParaOperacao.setCidade(cidade);
        funcionarioParaOperacao.setEstado(estado);
        funcionarioParaOperacao.setTipoAcesso(TipoAcesso.FUNCIONARIO); // Define o tipo de acesso

        // Atributos específicos de Funcionario
        funcionarioParaOperacao.setCargo(cargo);
        funcionarioParaOperacao.setSalario(salario);


        // chama o update
        try {
            if (funcionarioIdAtual != null) { // É uma edição
                funcionarioParaOperacao.setId(funcionarioIdAtual); // Define o ID para a atualização
                funcionarioController.update(funcionarioParaOperacao); // Chama o método update do Controller
                JOptionPane.showMessageDialog(this, "Funcionário atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else { 
                JOptionPane.showMessageDialog(this, "Esta tela é para edição. Use a tela de Adicionar para novos cadastros.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            this.dispose();

        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro na operação: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- MÉTODOS DE AÇÃO PARA O BOTÃO 'CANCELAR' ---
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Operação cancelada, nenhum dado salvo.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblEndereco = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        lblCidade = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        textEmail = new javax.swing.JTextField();
        lblNome = new javax.swing.JLabel();
        textTelefone = new javax.swing.JTextField();
        textEstado = new javax.swing.JTextField();
        textEndereco = new javax.swing.JTextField();
        lblCpf = new javax.swing.JLabel();
        textCidade = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        textNome = new javax.swing.JTextField();
        lblTelefone = new javax.swing.JLabel();
        textCpf = new javax.swing.JTextField();
        btnSalvar = new javax.swing.JButton();
        lblPerfil = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblCargo = new javax.swing.JLabel();
        textCargo = new javax.swing.JTextField();
        lblSalario = new javax.swing.JLabel();
        textSalario = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblEndereco.setText("Endereço:");

        btnCancelar.setText("Cancelar");

        lblCidade.setText("Cidade:");

        lblEstado.setText("Estado:");

        textEmail.setText("jTextField1");

        lblNome.setText("Nome:");

        textTelefone.setText("jTextField1");

        textEstado.setText("jTextField1");

        textEndereco.setText("jTextField1");

        lblCpf.setText("CPF:");

        textCidade.setText("jTextField1");

        lblEmail.setText("E-mail:");

        textNome.setText("jTextField1");

        lblTelefone.setText("Telefone:");

        textCpf.setText("jTextField1");

        btnSalvar.setText("Salvar");

        lblPerfil.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblPerfil.setText("FUNCIONÁRIO");

        jLabel1.setText("(modo edição)");

        lblCargo.setText("Cargo:");

        textCargo.setText("jTextField1");
        textCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textCargoActionPerformed(evt);
            }
        });

        lblSalario.setText("Salário");

        textSalario.setText("jTextField1");
        textSalario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSalarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblCpf, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblNome, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(lblEmail)
                            .addComponent(lblTelefone)
                            .addComponent(lblEndereco)
                            .addComponent(lblCidade)
                            .addComponent(lblEstado)
                            .addComponent(lblCargo)
                            .addComponent(lblSalario))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textSalario, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textNome, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(205, 205, 205)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(btnCancelar)
                        .addGap(47, 47, 47)
                        .addComponent(btnSalvar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(137, 137, 137)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPerfil)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNome)
                    .addComponent(textNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCpf)
                    .addComponent(textCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(textEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
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
                    .addComponent(lblCargo)
                    .addComponent(textCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSalario)
                    .addComponent(textSalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar)
                    .addComponent(btnSalvar))
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textCargoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textCargoActionPerformed

    private void textSalarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSalarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSalarioActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel1;
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
