/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.ifba.gerenciamentoUsuarios.view;

import br.com.ifba.perfilDeUsuario.controller.PerfilDeUsuarioIController;
import br.com.ifba.usuario.controller.UsuarioIController;

import br.com.ifba.pessoa.controller.PessoaIController;
import br.com.ifba.pessoa.entity.Pessoa;
import br.com.ifba.usuario.controller.AdotanteIController;
import br.com.ifba.usuario.controller.FuncionarioIController;
import br.com.ifba.usuario.controller.VoluntarioIController;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.view.render.Redenrizador;
import jakarta.annotation.PostConstruct;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jefferson S
 */
@Component
public class GerenciamentoUsuariosTela01 extends javax.swing.JFrame {

    
    @Lazy
    @Autowired 
    private PerfilDeUsuarioIController perfilDeUsuarioController;
    
    @Autowired
    private PessoaIController pessoaController; 
    @Autowired
    private UsuarioIController usuarioController;
    @Autowired
    private FuncionarioIController funcionarioController;
    @Autowired
    private VoluntarioIController voluntarioController;
    @Autowired
    private AdotanteIController adotanteController;
    

    private DefaultTableModel modeloTabelaUsuarios;

    public GerenciamentoUsuariosTela01() {
        initComponents();
    }

    @PostConstruct
    public void init() {
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        // --- Configuração da Tabela ---
        String[] nomeColunas = {"ID", "Nome", "Perfil de usuário", "Telefone", "Editar", "Remover"};
        modeloTabelaUsuarios = new DefaultTableModel(nomeColunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return getColumnName(column).equals("Editar") || getColumnName(column).equals("Remover");
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4 || columnIndex == 5) { // Editar e Remover
                    return ImageIcon.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };
        jTable1.setModel(modeloTabelaUsuarios);

        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel columnModel = jTable1.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(250); // Nome
        columnModel.getColumn(2).setPreferredWidth(150); // Tipo de usuário
        columnModel.getColumn(3).setPreferredWidth(150); // Telefone
        columnModel.getColumn(4).setPreferredWidth(70);  // Editar
        columnModel.getColumn(5).setPreferredWidth(70);  // Remover
        jTable1.setRowHeight(30);

        // --- Configuração dos Renderizadores e Ícones ---
        ImageIcon iconeLixeira = new ImageIcon(getClass().getResource("/images/icons8-lixeira-25.png"));
        ImageIcon iconeLapis = new ImageIcon(getClass().getResource("/images/icons8-editar-25.png"));
        columnModel.getColumn(4).setCellRenderer(new Redenrizador(iconeLapis));
        columnModel.getColumn(5).setCellRenderer(new Redenrizador(iconeLixeira));

        // --- AÇÃO DE CLIQUE NA TABELA ---
        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = jTable1.columnAtPoint(e.getPoint());
                int row = jTable1.rowAtPoint(e.getPoint());

                if (row >= 0 && column >= 0) {
                    Long pessoaId = (Long) modeloTabelaUsuarios.getValueAt(row, 0); // a primeira coluna (agora oculta) tem o ID
                    String clickedColumnName = jTable1.getColumnName(column);

                    if (clickedColumnName.equals("Remover")) {
                        removerUsuario(pessoaId);
                    } else if (clickedColumnName.equals("Editar")) {
                        abrirTelaEdicao(pessoaId);
                    }
                }
            }
        });
        btnPerfis.addActionListener(e -> {
            // A lógica de abrir a tela de perfis agora é aqui
            TelaPerfilDeUsuario telaPerfis = new TelaPerfilDeUsuario(this, true, perfilDeUsuarioController);
            telaPerfis.setVisible(true);
        });
        
        btnNovoUsuario.addActionListener(e -> {
            try {
                int numPerfis = perfilDeUsuarioController.buscarTodosPerfis().size();
                if (numPerfis == 0) {
                    JOptionPane.showMessageDialog(this, "Não há perfis de usuário cadastrados...", "Erro", JOptionPane.WARNING_MESSAGE);
                } else {
                    NovoUsuario novoUsuarioTela = new NovoUsuario(
                        this, true, 
                        adotanteController, 
                        funcionarioController, 
                        voluntarioController, 
                        perfilDeUsuarioController,
                        pessoaController,
                        usuarioController
                    );
                    novoUsuarioTela.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                            carregarUsuariosNaTabela(); // Recarrega a tabela principal
                        }
                    });
                    novoUsuarioTela.setVisible(true);
                }
            } catch (RuntimeException ex) {
                 JOptionPane.showMessageDialog(this, "Erro ao verificar perfis: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // --- Lógica de busca e carregamento inicial ---
        adicionarListenersDeBusca();
        carregarUsuariosNaTabela();
    }

    // --- MÉTODOS AUXILIARES ---

    private void carregarUsuariosNaTabela() {
        modeloTabelaUsuarios.setRowCount(0);
        try {
            // Buscamos todas as pessoas usando o controlador correto
            List<Usuario> todosUsuarios = usuarioController.buscarTodosUsuarios();
            for (Usuario usuario : todosUsuarios) {
                // Aqui, precisamos identificar o tipo de usuário e o seu perfil
                String tipoUsuario = "Sem Perfil";
                if (usuario.getPerfilDeUsuario() != null) {
                    tipoUsuario = usuario.getPerfilDeUsuario().getNomeDoPerfil();
                }
                
                modeloTabelaUsuarios.addRow(new Object[]{
                    usuario.getId(),
                    usuario.getNome(), // Acessamos o nome diretamente do objeto Usuario (herança)
                    tipoUsuario,
                    usuario.getTelefone(), // Acessamos o telefone diretamente (herança)
                    null, // Coluna Editar
                    null  // Coluna Remover
                });
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar usuários: " + e.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void realizarBuscaUsuarios() {
       String termoBusca = jTextField1.getText().trim();
        modeloTabelaUsuarios.setRowCount(0);

        if (termoBusca.isEmpty() || termoBusca.equals("Busca por nome ou cpf")) {
            carregarUsuariosNaTabela();
            return;
        }

        try {
            List<Usuario> usuariosEncontrados;

            // Verifica se o termo de busca parece ser um CPF (somente números)
            if (termoBusca.matches("\\d+")) {
                Optional<Usuario> usuarioOpt = usuarioController.buscarUsuarioPorCpf(termoBusca);
                if (usuarioOpt.isPresent()) {
                    usuariosEncontrados = List.of(usuarioOpt.get());
                } else {
                    usuariosEncontrados = List.of();
                }
            } else {
                // Se não for CPF, busca por nome
                usuariosEncontrados = usuarioController.buscarUsuarioPorNome(termoBusca);
            }

            if (!usuariosEncontrados.isEmpty()) {
                for (Usuario usuario : usuariosEncontrados) {
                    String tipoUsuario = "Sem Perfil";
                    if (usuario.getPerfilDeUsuario() != null) {
                        tipoUsuario = usuario.getPerfilDeUsuario().getNomeDoPerfil();
                    }

                    modeloTabelaUsuarios.addRow(new Object[]{
                        usuario.getId(),
                        usuario.getNome(),
                        tipoUsuario,
                        usuario.getTelefone(),
                        null,
                        null
                    });
                }
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum usuário encontrado.", "Busca", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao realizar a busca: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void removerUsuario(Long usuarioId) {
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este usuário? Esta ação é irreversível.", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Chama o método para deletar o usuário
                usuarioController.deletarUsuario(usuarioId);
                carregarUsuariosNaTabela();
                JOptionPane.showMessageDialog(this, "Usuário excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir usuário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void abrirTelaEdicao(Long usuarioId) {
        try {
            Optional<Usuario> usuarioParaEditarOpt = usuarioController.buscarUsuarioPorId(usuarioId);

            if (usuarioParaEditarOpt.isPresent()) {
                Usuario usuarioParaEditar = usuarioParaEditarOpt.get();

                TelaEdicaoUsuario telaEdicao = new TelaEdicaoUsuario(this, true,
                    usuarioParaEditar,
                    perfilDeUsuarioController,
                    adotanteController,
                    funcionarioController,
                    voluntarioController,
                    usuarioController
                );
                telaEdicao.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        carregarUsuariosNaTabela(); // Recarrega a tabela principal após a edição
                    }
                });
                telaEdicao.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Usuário não encontrado para edição.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados do usuário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarListenersDeBusca() {
        Timer searchTimer = new Timer(300, e -> realizarBuscaUsuarios());
        searchTimer.setRepeats(false);

        jTextField1.addActionListener(evt -> realizarBuscaUsuarios());
        jTextField1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (jTextField1.getText().equals("Busca por nome ou cpf")) {
                    jTextField1.setText("");
                }
            }
        });

        jTextField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { searchTimer.restart(); }
            @Override
            public void removeUpdate(DocumentEvent e) { searchTimer.restart(); }
            @Override
            public void changedUpdate(DocumentEvent e) { }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnNovoUsuario = new javax.swing.JToggleButton();
        btnPerfis = new javax.swing.JToggleButton();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnNovoUsuario.setText("Novo Usuário");
        btnNovoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoUsuarioActionPerformed(evt);
            }
        });

        btnPerfis.setText("Perfis de Usuário");
        btnPerfis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPerfisActionPerformed(evt);
            }
        });

        jTextField1.setText("Busca por nome ou cpf");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 202, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnPerfis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNovoUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50))
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 737, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovoUsuario)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnPerfis)
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void btnNovoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoUsuarioActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btnNovoUsuarioActionPerformed

    private void btnPerfisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerfisActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btnPerfisActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnNovoUsuario;
    private javax.swing.JToggleButton btnPerfis;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
