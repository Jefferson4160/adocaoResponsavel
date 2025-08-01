/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package br.com.ifba.usuario.view;

import br.com.ifba.adotante.entity.Adotante;
import br.com.ifba.usuario.controller.AdotanteIController;
import br.com.ifba.usuario.util.TipoAcesso;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class Tela02Edit03Adotante extends javax.swing.JDialog {
    
    private AdotanteIController adotanteController; 
    private Long adotanteIdAtual;
    
    /**
     * Creates new form Tela02Edit03Adotante
     */
    public Tela02Edit03Adotante(java.awt.Frame parent, boolean modal, Long idAdotante, AdotanteIController adotanteController) {
        super(parent, modal);
        initComponents();
        this.adotanteController = adotanteController;
        this.adotanteIdAtual = idAdotante; // Atribui o ID recebido

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
        if (this.adotanteIdAtual != null) {
            lblPerfil.setText("ADOTANTE (EDIÇÃO)"); // Ajusta o título para edição
            jLabel1.setText("(modo edição)");
            carregarDadosAdotanteParaEdicao(this.adotanteIdAtual); // Carrega os dados para edição
        } else {
            lblPerfil.setText("ADOTANTE (NOVO)"); // Se for usado para novo (sem ID)
            jLabel1.setText("(novo cadastro)");
            // Limpar campos para novo cadastro
            textNome.setText("");
            textCpf.setText("");
            textEmail.setText("");
            textTelefone.setText("");
            textEndereco.setText("");
            textCidade.setText("");
            textEstado.setText("");
            
        }
    }

    // MÉTODO AUXILIAR
    
    private void carregarDadosAdotanteParaEdicao(Long id) {
        try {
            Optional<Adotante> adotanteOpt = adotanteController.findById(id); // Busca o adotante pelo ID

            if (adotanteOpt.isPresent()) {
                Adotante adotante = adotanteOpt.get();
                // Preenche os campos comuns de Usuario
                textNome.setText(adotante.getNome());
                textCpf.setText(adotante.getCpf());
                textEmail.setText(adotante.getEmail());
                textTelefone.setText(adotante.getTelefone());
                textEndereco.setText(adotante.getEndereco());
                textCidade.setText(adotante.getCidade());
                textEstado.setText(adotante.getEstado());
                
            } else {
                JOptionPane.showMessageDialog(this, "Adotante com ID " + id + " não encontrado para edição.", "Erro", JOptionPane.ERROR_MESSAGE);
                this.dispose(); // Fecha a tela se não encontrar
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados do adotante: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            this.dispose(); // Fecha em caso de erro
        }
    }

    // faço a atualização
    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {
        // pego os dados dos campos da tela
        String nome = textNome.getText();
        String cpf = textCpf.getText();
        String email = textEmail.getText();
        String telefone = textTelefone.getText();
        String endereco = textEndereco.getText();
        String cidade = textCidade.getText();
        String estado = textEstado.getText();
        

        Adotante adotanteParaOperacao = new Adotante();
        // Atributos herdados de Usuario
        adotanteParaOperacao.setNome(nome);
        adotanteParaOperacao.setCpf(cpf);
        adotanteParaOperacao.setEmail(email);
        adotanteParaOperacao.setTelefone(telefone);
        adotanteParaOperacao.setEndereco(endereco);
        adotanteParaOperacao.setCidade(cidade);
        adotanteParaOperacao.setEstado(estado);
        adotanteParaOperacao.setTipoAcesso(TipoAcesso.ADOTANTE); // Define o tipo de acesso

        
        // chamo o update
        try {
            if (adotanteIdAtual != null) { // É uma edição
                adotanteParaOperacao.setId(adotanteIdAtual); // Define o ID para a atualização
                adotanteController.update(adotanteParaOperacao); // Chama o método update do Controller
                JOptionPane.showMessageDialog(this, "Adotante atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else { 
                JOptionPane.showMessageDialog(this, "Esta tela é para edição. Use a tela de Adicionar para novos cadastros.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            this.dispose();

        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro na operação: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

 
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

        lblEstado = new javax.swing.JLabel();
        textEmail = new javax.swing.JTextField();
        lblTelefone = new javax.swing.JLabel();
        lblNome = new javax.swing.JLabel();
        textTelefone = new javax.swing.JTextField();
        textCpf = new javax.swing.JTextField();
        textEstado = new javax.swing.JTextField();
        textEndereco = new javax.swing.JTextField();
        btnSalvar = new javax.swing.JButton();
        lblCpf = new javax.swing.JLabel();
        textCidade = new javax.swing.JTextField();
        lblPerfil = new javax.swing.JLabel();
        lblEndereco = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        lblEmail = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblCidade = new javax.swing.JLabel();
        textNome = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblEstado.setText("Estado:");

        textEmail.setText("jTextField1");

        lblTelefone.setText("Telefone:");

        lblNome.setText("Nome:");

        textTelefone.setText("jTextField1");

        textCpf.setText("jTextField1");

        textEstado.setText("jTextField1");

        textEndereco.setText("jTextField1");

        btnSalvar.setText("Salvar");

        lblCpf.setText("CPF:");

        textCidade.setText("jTextField1");

        lblPerfil.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblPerfil.setText("ADOTANTE");

        lblEndereco.setText("Endereço:");

        btnCancelar.setText("Cancelar");

        lblEmail.setText("E-mail:");

        jLabel1.setText("(modo edição)");

        lblCidade.setText("Cidade:");

        textNome.setText("jTextField1");

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
                            .addComponent(lblEstado))
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textNome, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(211, 211, 211)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addComponent(btnCancelar)
                        .addGap(46, 46, 46)
                        .addComponent(btnSalvar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(121, 121, 121)))
                .addContainerGap(25, Short.MAX_VALUE))
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
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar)
                    .addComponent(btnSalvar))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblCidade;
    private javax.swing.JLabel lblCpf;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEndereco;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblPerfil;
    private javax.swing.JLabel lblTelefone;
    private javax.swing.JTextField textCidade;
    private javax.swing.JTextField textCpf;
    private javax.swing.JTextField textEmail;
    private javax.swing.JTextField textEndereco;
    private javax.swing.JTextField textEstado;
    private javax.swing.JTextField textNome;
    private javax.swing.JTextField textTelefone;
    // End of variables declaration//GEN-END:variables
}
