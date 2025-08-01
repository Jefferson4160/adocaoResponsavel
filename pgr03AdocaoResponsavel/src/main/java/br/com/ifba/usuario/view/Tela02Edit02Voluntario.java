/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package br.com.ifba.usuario.view;

import br.com.ifba.usuario.controller.VoluntarioIController;
import br.com.ifba.usuario.util.TipoAcesso;
import br.com.ifba.voluntario.entity.Voluntario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class Tela02Edit02Voluntario extends javax.swing.JDialog {
    
    private VoluntarioIController voluntarioController; // Referência ao controlador de Voluntário
    private Long voluntarioIdAtual;
    /**
     * Creates new form Tela02Edit02Voluntario
     */
    public Tela02Edit02Voluntario(java.awt.Frame parent, boolean modal, Long idVoluntario, VoluntarioIController voluntarioController) {
        super(parent, modal);
        initComponents();this.voluntarioController = voluntarioController;
        this.voluntarioIdAtual = idVoluntario; // Atribui o ID recebido

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
        if (this.voluntarioIdAtual != null) {
            lblPerfil.setText("VOLUNTÁRIO (EDIÇÃO)"); // Ajusta o título para edição
            jLabel1.setText("(modo edição)");
            carregarDadosVoluntarioParaEdicao(this.voluntarioIdAtual); // Carrega os dados para edição
        } 
    }

    /**
     * MÉTODO AUXILIAR: Carrega os dados de um voluntário para os campos da tela.
     */
    private void carregarDadosVoluntarioParaEdicao(Long id) {
        try {
            Optional<Voluntario> voluntarioOpt = voluntarioController.findById(id); // Busca o voluntário pelo ID

            if (voluntarioOpt.isPresent()) {
                Voluntario voluntario = voluntarioOpt.get();
                // Preenche os campos comuns de Usuario
                textNome.setText(voluntario.getNome());
                textCpf.setText(voluntario.getCpf());
                textEmail.setText(voluntario.getEmail());
                textTelefone.setText(voluntario.getTelefone());
                textEndereco.setText(voluntario.getEndereco());
                textCidade.setText(voluntario.getCidade());
                textEstado.setText(voluntario.getEstado());
                // Preenche os campos específicos de Voluntario
                textArea.setText(voluntario.getAreaAtuacao());
                textDissponibilidade.setText(voluntario.getDisponibilidade());
            } else {
                JOptionPane.showMessageDialog(this, "Voluntário com ID " + id + " não encontrado para edição.", "Erro", JOptionPane.ERROR_MESSAGE);
                this.dispose(); // Fecha a tela se não encontrar
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados do voluntário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            this.dispose(); // Fecha em caso de erro
        }
    }

    // --- MÉTODO DE AÇÃO PARA O BOTÃO 'SALVAR' ---
    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {
        // 1. Coletar os dados dos campos da tela
        String nome = textNome.getText();
        String cpf = textCpf.getText();
        String email = textEmail.getText();
        String telefone = textTelefone.getText();
        String endereco = textEndereco.getText();
        String cidade = textCidade.getText();
        String estado = textEstado.getText();
        String areaAtuacao = textArea.getText();
        String disponibilidade = textDissponibilidade.getText();


        Voluntario voluntarioParaOperacao = new Voluntario();
        // Atributos herdados de Usuario
        voluntarioParaOperacao.setNome(nome);
        voluntarioParaOperacao.setCpf(cpf);
        voluntarioParaOperacao.setEmail(email);
        voluntarioParaOperacao.setTelefone(telefone);
        voluntarioParaOperacao.setEndereco(endereco);
        voluntarioParaOperacao.setCidade(cidade);
        voluntarioParaOperacao.setEstado(estado);
        voluntarioParaOperacao.setTipoAcesso(TipoAcesso.VOLUNTARIO); // Define o tipo de acesso

        // Atributos específicos de Voluntario
        voluntarioParaOperacao.setAreaAtuacao(areaAtuacao);
        voluntarioParaOperacao.setDisponibilidade(disponibilidade);


        // 2. Chamar o método 'update' ou 'save' do Controller
        try {
            if (voluntarioIdAtual != null) { // É uma edição
                voluntarioParaOperacao.setId(voluntarioIdAtual); // Define o ID para a atualização
                voluntarioController.update(voluntarioParaOperacao); // Chama o método update do Controller
                JOptionPane.showMessageDialog(this, "Voluntário atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else { // Seria um novo cadastro, mas esta tela é para edição, idealmente.
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

        textCidade = new javax.swing.JTextField();
        lblEndereco = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblArea = new javax.swing.JLabel();
        textNome = new javax.swing.JTextField();
        textArea = new javax.swing.JTextField();
        lblTelefone = new javax.swing.JLabel();
        lblDisponibilidade = new javax.swing.JLabel();
        textCpf = new javax.swing.JTextField();
        textDissponibilidade = new javax.swing.JTextField();
        btnSalvar = new javax.swing.JButton();
        lblPerfil = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblCidade = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        textEmail = new javax.swing.JTextField();
        lblNome = new javax.swing.JLabel();
        textTelefone = new javax.swing.JTextField();
        textEstado = new javax.swing.JTextField();
        textEndereco = new javax.swing.JTextField();
        lblCpf = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        textCidade.setText("jTextField1");

        lblEndereco.setText("Endereço:");

        lblEmail.setText("E-mail:");

        lblArea.setText("Área de atuação");

        textNome.setText("jTextField1");

        textArea.setText("jTextField1");
        textArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textAreaActionPerformed(evt);
            }
        });

        lblTelefone.setText("Telefone:");

        lblDisponibilidade.setText("Disponibilidade");

        textCpf.setText("jTextField1");

        textDissponibilidade.setText("jTextField1");
        textDissponibilidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textDissponibilidadeActionPerformed(evt);
            }
        });

        btnSalvar.setText("Salvar");

        lblPerfil.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblPerfil.setText("VOLUNTÁRIO");

        btnCancelar.setText("Cancelar");

        jLabel1.setText("(modo edição)");

        lblCidade.setText("Cidade:");

        lblEstado.setText("Estado:");

        textEmail.setText("jTextField1");

        lblNome.setText("Nome:");

        textTelefone.setText("jTextField1");

        textEstado.setText("jTextField1");

        textEndereco.setText("jTextField1");

        lblCpf.setText("CPF:");

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
                            .addComponent(lblArea)
                            .addComponent(lblDisponibilidade))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textDissponibilidade, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textArea, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addGap(168, 168, 168)
                        .addComponent(btnCancelar)
                        .addGap(46, 46, 46)
                        .addComponent(btnSalvar)))
                .addContainerGap(23, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(149, 149, 149))
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
                    .addComponent(lblArea)
                    .addComponent(textArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDisponibilidade)
                    .addComponent(textDissponibilidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar)
                    .addComponent(btnSalvar))
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textAreaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textAreaActionPerformed

    private void textDissponibilidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textDissponibilidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textDissponibilidadeActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblArea;
    private javax.swing.JLabel lblCidade;
    private javax.swing.JLabel lblCpf;
    private javax.swing.JLabel lblDisponibilidade;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEndereco;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblPerfil;
    private javax.swing.JLabel lblTelefone;
    private javax.swing.JTextField textArea;
    private javax.swing.JTextField textCidade;
    private javax.swing.JTextField textCpf;
    private javax.swing.JTextField textDissponibilidade;
    private javax.swing.JTextField textEmail;
    private javax.swing.JTextField textEndereco;
    private javax.swing.JTextField textEstado;
    private javax.swing.JTextField textNome;
    private javax.swing.JTextField textTelefone;
    // End of variables declaration//GEN-END:variables
}
