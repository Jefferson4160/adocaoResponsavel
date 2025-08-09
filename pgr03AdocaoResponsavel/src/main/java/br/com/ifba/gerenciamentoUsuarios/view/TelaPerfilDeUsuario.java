/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package br.com.ifba.gerenciamentoUsuarios.view;

import br.com.ifba.infrastructure.util.StringUtil;
import br.com.ifba.perfilDeUsuario.controller.PerfilDeUsuarioIController;
import br.com.ifba.perfilDeUsuario.entity.PerfilDeUsuario;
import br.com.ifba.usuario.view.render.Redenrizador;
import jakarta.annotation.PostConstruct;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jefferson S
 */

public class TelaPerfilDeUsuario extends javax.swing.JDialog {

    private final PerfilDeUsuarioIController perfilDeUsuarioController;
    private DefaultTableModel modeloTabelaPerfis;
    
    private ImageIcon iconeLixeira;
    private ImageIcon iconeLapis;

    public TelaPerfilDeUsuario(JFrame parent, boolean modal, PerfilDeUsuarioIController perfilDeUsuarioController) {
        super(parent, modal);
        this.perfilDeUsuarioController = perfilDeUsuarioController;
        initComponents();
        init();
    }
    
    @PostConstruct
    public void init() {
        this.setLocationRelativeTo(null);
        carregarIcones();
        configurarTabela();
        carregarPerfisNaTabela();
        configurarAcoes();
    }
    
    private void carregarIcones() {
        try {
            iconeLixeira = new ImageIcon(getClass().getResource("/images/icons8-lixeira-25.png"));
            iconeLapis = new ImageIcon(getClass().getResource("/images/icons8-editar-25.png"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar ícones: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void configurarTabela() {
        String[] nomeColunas = {"ID", "Nome do Perfil", "Editar", "Remover"};
        modeloTabelaPerfis = new DefaultTableModel(nomeColunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return getColumnName(column).equals("Editar") || getColumnName(column).equals("Remover");
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2 || columnIndex == 3) {
                    return ImageIcon.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };
        jTable1.setModel(modeloTabelaPerfis);

        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel columnModel = jTable1.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(350);
        columnModel.getColumn(2).setPreferredWidth(70);
        columnModel.getColumn(3).setPreferredWidth(70);
        jTable1.setRowHeight(30);

        if (iconeLapis != null) {
            columnModel.getColumn(2).setCellRenderer(new Redenrizador(iconeLapis));
        }
        if (iconeLixeira != null) {
            columnModel.getColumn(3).setCellRenderer(new Redenrizador(iconeLixeira));
        }
    }
    
    private void carregarPerfisNaTabela() {
        modeloTabelaPerfis.setRowCount(0);
        try {
            List<PerfilDeUsuario> perfis = perfilDeUsuarioController.buscarTodosPerfis();
            for (PerfilDeUsuario perfil : perfis) {
                modeloTabelaPerfis.addRow(new Object[]{
                    perfil.getId(),
                    perfil.getNomeDoPerfil(),
                    null,
                    null
                });
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar perfis: " + e.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void configurarAcoes() {
        btnNovoPerfil.addActionListener(this::adicionarNovoPerfil);
        
        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = jTable1.columnAtPoint(e.getPoint());
                int row = jTable1.rowAtPoint(e.getPoint());

                if (row >= 0 && column >= 0) {
                    Long perfilId = (Long) modeloTabelaPerfis.getValueAt(row, 0);
                    String clickedColumnName = jTable1.getColumnName(column);

                    if (clickedColumnName.equals("Remover")) {
                        removerPerfil(perfilId);
                    } else if (clickedColumnName.equals("Editar")) {
                        String nomeAtual = (String) modeloTabelaPerfis.getValueAt(row, 1);
                        editarPerfil(perfilId, nomeAtual);
                    }
                }
            }
        });
        // --- LÓGICA DO CAMPO DE BUSCA ---
        Timer searchTimer = new Timer(300, e -> realizarBuscaPerfis());
        searchTimer.setRepeats(false);

        textBuscar.addActionListener(e -> realizarBuscaPerfis());
        textBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (textBuscar.getText().equals("Buscar...")) {
                    textBuscar.setText("");
                }
            }
        });
        textBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { searchTimer.restart(); }
            @Override public void removeUpdate(DocumentEvent e) { searchTimer.restart(); }
            @Override public void changedUpdate(DocumentEvent e) {}
        });
    }

    private void adicionarNovoPerfil(ActionEvent evt) {
        String nome = textNovoPerfil.getText().trim();
        if (StringUtil.isNullOrEmpty(nome) || nome.equals("Novo perfil...")) {
            JOptionPane.showMessageDialog(this, "O nome do perfil não pode ser vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            PerfilDeUsuario novoPerfil = new PerfilDeUsuario();
            novoPerfil.setNomeDoPerfil(nome);
            perfilDeUsuarioController.salvarPerfil(novoPerfil);
            carregarPerfisNaTabela();
            textNovoPerfil.setText("");
            JOptionPane.showMessageDialog(this, "Perfil adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar perfil: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void removerPerfil(Long perfilId) {
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este perfil?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                perfilDeUsuarioController.deletarPerfil(perfilId);
                carregarPerfisNaTabela();
                JOptionPane.showMessageDialog(this, "Perfil excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir perfil: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void editarPerfil(Long perfilId, String nomeAtual) {
        String novoNome = JOptionPane.showInputDialog(this, "Editar nome do perfil:", nomeAtual);
        if (novoNome != null && !StringUtil.isNullOrEmpty(novoNome.trim()) && !novoNome.trim().equals(nomeAtual)) {
            try {
                Optional<PerfilDeUsuario> perfilOpt = perfilDeUsuarioController.buscarPerfilPorId(perfilId);
                if (perfilOpt.isPresent()) {
                    PerfilDeUsuario perfil = perfilOpt.get();
                    perfil.setNomeDoPerfil(novoNome.trim());
                    perfilDeUsuarioController.atualizarPerfil(perfil);
                    carregarPerfisNaTabela();
                    JOptionPane.showMessageDialog(this, "Perfil atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(this, "Erro ao editar perfil: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void realizarBuscaPerfis() {
        String termoBusca = textBuscar.getText().trim();
        modeloTabelaPerfis.setRowCount(0);

        if (StringUtil.isNullOrEmpty(termoBusca) || termoBusca.equals("Buscar...")) {
            carregarPerfisNaTabela();
            return;
        }

        try {
            List<PerfilDeUsuario> perfisEncontrados = perfilDeUsuarioController.buscarPorNome(termoBusca);
            if (perfisEncontrados != null && !perfisEncontrados.isEmpty()) {
                for (PerfilDeUsuario perfil : perfisEncontrados) {
                    modeloTabelaPerfis.addRow(new Object[]{
                        perfil.getId(),
                        perfil.getNomeDoPerfil(),
                        null,
                        null
                    });
                }
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum perfil encontrado com o nome '" + termoBusca + "'.", "Busca por Nome", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao realizar a busca: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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

        lblTitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        textBuscar = new javax.swing.JTextField();
        textNovoPerfil = new javax.swing.JTextField();
        btnNovoPerfil = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTitulo.setText("Perfis de Usuarios");

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

        textBuscar.setText("Buscar...");

        textNovoPerfil.setText("Novo perfil...");

        btnNovoPerfil.setText("Adcionar novo perfil");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(233, 233, 233)
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textNovoPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(btnNovoPerfil)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addGap(18, 18, 18)
                .addComponent(textBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(textNovoPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNovoPerfil)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNovoPerfil;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField textBuscar;
    private javax.swing.JTextField textNovoPerfil;
    // End of variables declaration//GEN-END:variables
}
