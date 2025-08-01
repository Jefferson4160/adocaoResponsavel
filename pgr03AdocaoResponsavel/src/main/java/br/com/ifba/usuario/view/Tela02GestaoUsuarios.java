/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.ifba.usuario.view;

import static br.com.ifba.Pgr03AdocaoResponsavelApplication.main;
import br.com.ifba.adotante.entity.Adotante;
import br.com.ifba.app.view.Main;
import br.com.ifba.funcionario.entity.Funcionario;
import br.com.ifba.infrastructure.util.StringUtil;
import br.com.ifba.usuario.controller.AdotanteIController;
import br.com.ifba.usuario.controller.FuncionarioIController;
import br.com.ifba.usuario.controller.UsuarioIController;
import br.com.ifba.usuario.controller.VoluntarioIController;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.util.TipoAcesso;
import br.com.ifba.usuario.view.render.Redenrizador;
import br.com.ifba.voluntario.entity.Voluntario;
import jakarta.annotation.PostConstruct;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
 * @author ADMIN
 */
@Component
public class Tela02GestaoUsuarios extends javax.swing.JFrame {
    private boolean fecharViaBotaoInicio = false;
    @Autowired
    private UsuarioIController usuarioController;
    @Lazy
    @Autowired
    private Tela01IdentificacaoUsuario tela01IdentificacaoUsuario; 
    @Lazy
    @Autowired
    private Main main;
    
    @Autowired
    private FuncionarioIController funcionarioController;
    @Autowired
    private VoluntarioIController voluntarioController;
    @Autowired
    private AdotanteIController adotanteController;
    
    private DefaultTableModel modeloTabelaUsuarios;
    
    
    /**
     * Creates new form TelaFuncionarioVoluntario
     */
    public Tela02GestaoUsuarios() {
        initComponents();
    }
    
    @PostConstruct 
    public void init() {
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        
        // Adiciona um WindowListener para mostrar a tela anterior (Identificação) quando esta for fechada.
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (!fecharViaBotaoInicio) {
                    if (tela01IdentificacaoUsuario != null) {
                        tela01IdentificacaoUsuario.setVisible(true);
                        System.out.println("Tela de Gestão de Usuários fechada. Tela de Identificação visível novamente.");
                    }
                }
            }
        });
        
        // --- Configuração da Tabela de Usuários ---
        String[] nomeColunas = {"ID", "Nome", "CPF", "Email", "Tipo Acesso", "Remover", "Editar"};
        modeloTabelaUsuarios = new DefaultTableModel(nomeColunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                String columnName = getColumnName(column);
                return columnName.equals("Remover") || columnName.equals("Editar");
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 5 || columnIndex == 6) { // Índices para Remover e Editar
                    return ImageIcon.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };
        jTable1.setModel(modeloTabelaUsuarios);

        // Configuração de larguras das colunas
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel columnModel = jTable1.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50); // ID
        columnModel.getColumn(1).setPreferredWidth(150); // Nome
        columnModel.getColumn(2).setPreferredWidth(100); // CPF
        columnModel.getColumn(3).setPreferredWidth(150); // Email
        columnModel.getColumn(4).setPreferredWidth(100); // Tipo Acesso
        columnModel.getColumn(5).setPreferredWidth(70); // Remover
        columnModel.getColumn(6).setPreferredWidth(70); // Editar

        // Carregamento de Ícones e Aplicação de Renderizadores
        ImageIcon iconeLixeira = new ImageIcon(getClass().getResource("/images/icons8-lixeira-25.png"));
        ImageIcon iconeLapis = new ImageIcon(getClass().getResource("/images/icons8-editar-25.png"));
        columnModel.getColumn(5).setCellRenderer(new Redenrizador(iconeLixeira)); // Coluna Remover
        columnModel.getColumn(6).setCellRenderer(new Redenrizador(iconeLapis)); // Coluna Editar
        jTable1.setRowHeight(30);

        // editar e remover
        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = jTable1.columnAtPoint(e.getPoint());
                int row = jTable1.rowAtPoint(e.getPoint());

                if (row >= 0 && column >= 0) {
                    Long usuarioId = (Long) modeloTabelaUsuarios.getValueAt(row, 0); // Pega o ID
                    String tipoAcessoStr = (String) modeloTabelaUsuarios.getValueAt(row, 4); // Pega o Tipo Acesso como String (coluna 4)
                    TipoAcesso tipoAcesso = TipoAcesso.valueOf(tipoAcessoStr); // Converte para o enum

                    String clickedColumnName = jTable1.getColumnName(column);

                    if (clickedColumnName.equals("Remover")) {
                        int confirm = JOptionPane.showConfirmDialog(Tela02GestaoUsuarios.this,
                                "Tem certeza que deseja excluir o usuário de ID " + usuarioId + " (" + tipoAcessoStr + ")?",
                                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            try {
                                
                                // Busca o usuário completo e específico pelo ID
                                Usuario usuarioParaRemover = null;
                                switch (tipoAcesso) {
                                    case FUNCIONARIO:
                                        Optional<Funcionario> funcOpt = funcionarioController.findById(usuarioId);
                                        if(funcOpt.isPresent()) usuarioParaRemover = funcOpt.get();
                                        break;
                                    case ADOTANTE:
                                        Optional<Adotante> adotanteOpt = adotanteController.findById(usuarioId);
                                        if(adotanteOpt.isPresent()) usuarioParaRemover = adotanteOpt.get();
                                        break;
                                    case VOLUNTARIO:
                                        Optional<Voluntario> volOpt = voluntarioController.findById(usuarioId);
                                        if(volOpt.isPresent()) usuarioParaRemover = volOpt.get();
                                        break;
                                    default:
                                        
                                        break;
                                }

                                if (usuarioParaRemover == null) {
                                    JOptionPane.showMessageDialog(rootPane, "Usuário não encontrado para exclusão (ID: " + usuarioId + ").");
                                    return;
                                }

                                // chamo o método delete
                                switch (tipoAcesso) {
                                    case FUNCIONARIO:
                                        funcionarioController.delete(usuarioId);
                                        break;
                                    case ADOTANTE:
                                        adotanteController.delete(usuarioId);
                                        break;
                                    case VOLUNTARIO:
                                        voluntarioController.delete(usuarioId);
                                        break;
                                    default:
                                        
                                        break;
                                }

                                modeloTabelaUsuarios.removeRow(row); // Remove visualmente da tabela
                                JOptionPane.showMessageDialog(rootPane, "Usuário excluído com sucesso!");
                            } catch (RuntimeException ex) {
                                JOptionPane.showMessageDialog(rootPane, "Erro ao excluir usuário: " + ex.getMessage());
                            }
                        }
                    } else if (clickedColumnName.equals("Editar")) {
                        // Lógica de edição
                        if (tipoAcesso == TipoAcesso.FUNCIONARIO) {
                            try {
                                // busca o funcionário completo pelo ID
                                Optional<Funcionario> funcOpt = funcionarioController.findById(usuarioId);
                                if (funcOpt.isPresent()) {
                                    Funcionario funcionarioParaEdicao = funcOpt.get();

                                    // Abrimos a tela de edição, passando o funcionário e o controlador
                                    Tela02Edit01Funcionario telaEdicaoFunc = new Tela02Edit01Funcionario(
                                            Tela02GestaoUsuarios.this, // Tela pai
                                            true, // Modal
                                            funcionarioParaEdicao.getId(), // ID do funcionário
                                            funcionarioController // Controlador específico
                                    );
                                    telaEdicaoFunc.setVisible(true); // Abre o diálogo

                                    // 3. Recarrega a tabela após o fechamento do diálogo de edição
                                    carregarUsuariosNaTabela();
                                } else {
                                    JOptionPane.showMessageDialog(rootPane, "Funcionário com ID " + usuarioId + " não encontrado para edição.", "Erro", JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (RuntimeException ex) {
                                JOptionPane.showMessageDialog(rootPane, "Erro ao abrir tela de edição de funcionário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                        } else if (tipoAcesso == TipoAcesso.VOLUNTARIO) { 
                            try {
                                Optional<Voluntario> volOpt = voluntarioController.findById(usuarioId);
                                if (volOpt.isPresent()) {
                                    Voluntario voluntarioParaEdicao = volOpt.get();

                                    Tela02Edit02Voluntario telaEdicaoVol = new Tela02Edit02Voluntario(
                                            Tela02GestaoUsuarios.this, true, voluntarioParaEdicao.getId(), voluntarioController
                                    );
                                    telaEdicaoVol.setVisible(true);
                                    carregarUsuariosNaTabela(); // Recarrega a tabela após edição
                                } else {
                                    JOptionPane.showMessageDialog(rootPane, "Voluntário com ID " + usuarioId + " não encontrado para edição.", "Erro", JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (RuntimeException ex) {
                                JOptionPane.showMessageDialog(rootPane, "Erro ao abrir tela de edição de voluntário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                        }else if (tipoAcesso == TipoAcesso.ADOTANTE) {
                            try {
                                Optional<Adotante> adotanteOpt = adotanteController.findById(usuarioId);
                                if (adotanteOpt.isPresent()) {
                                    Adotante adotanteParaEdicao = adotanteOpt.get();

                                    Tela02Edit03Adotante telaEdicaoAdotante = new Tela02Edit03Adotante(
                                            Tela02GestaoUsuarios.this, true, adotanteParaEdicao.getId(), adotanteController
                                    );
                                    telaEdicaoAdotante.setVisible(true);
                                    carregarUsuariosNaTabela(); // Recarrega a tabela após edição
                                } else {
                                    JOptionPane.showMessageDialog(rootPane, "Adotante com ID " + usuarioId + " não encontrado para edição.", "Erro", JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (RuntimeException ex) {
                                JOptionPane.showMessageDialog(rootPane, "Erro ao abrir tela de edição de adotante: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        // caso apareça algum outro tipo de usuário não previsto
                        else {
                            JOptionPane.showMessageDialog(rootPane, "Tipo de usuário não suportado para edição: " + tipoAcessoStr, "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        
        // --- ADICIONAR DocumentListener para a busca em tempo real ---
        Timer searchTimer = new Timer(300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarBuscaUsuarios();
            }
        });
        searchTimer.setRepeats(false);

        if (textBuscar != null) {
            textBuscar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    realizarBuscaUsuarios();
                }
            });
            textBuscar.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (textBuscar.getText().equals("Buscar...")) {
                        textBuscar.setText("");
                    }
                }
            });

            textBuscar.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    searchTimer.restart();
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    searchTimer.restart();
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                }
            });
        }
        
        carregarUsuariosNaTabela();
        btnInicio.addActionListener(evt -> voltarParaMain());
        
        
    }
    
    
    
    // Método para voltar para a tela principal
    private void voltarParaMain() {
        fecharViaBotaoInicio = true;
        this.dispose();
        main.setVisible(true);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textBuscar = new javax.swing.JTextField();
        btnAdcionar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnInicio = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textBuscar.setText("Buscar...");

        btnAdcionar.setText("ADICIONAR");
        btnAdcionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdcionarActionPerformed(evt);
            }
        });

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

        btnInicio.setText("INICIO");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 734, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnInicio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAdcionar, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(textBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdcionar)
                    .addComponent(btnInicio))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdcionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdcionarActionPerformed
        String[] opcoes = {"FUNCIONARIO", "VOLUNTARIO", "ADOTANTE"};
    
        String tipoSelecionado = (String) JOptionPane.showInputDialog(
            this,
            "Escolha o tipo de acesso do novo usuário:",
            "Adicionar Usuário",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opcoes,
            opcoes[0]
        );

        if (tipoSelecionado == null) {
            // Usuário cancelou a seleção, não faz nada
            return;
        }

        switch (tipoSelecionado) {
            case "FUNCIONARIO":
                Tela02Add01Funcionario telaAdicionarFunc = new Tela02Add01Funcionario(Tela02GestaoUsuarios.this, true, funcionarioController); // <--- ALTERADO AQUI!
                telaAdicionarFunc.setVisible(true);
                break;
            case "VOLUNTARIO":
                Tela02Add02Voluntario telaAddVoluntario = new Tela02Add02Voluntario(this, true, voluntarioController);
                telaAddVoluntario.setVisible(true);             
                break;
            case "ADOTANTE":
                Tela02Add03Adotante telaAddAdotante = new Tela02Add03Adotante(this, true, adotanteController);
                telaAddAdotante.setVisible(true);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Tipo inválido selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
                break;
        }
        carregarUsuariosNaTabela();
    }//GEN-LAST:event_btnAdcionarActionPerformed

   // --- MÉTODOS AUXILIARES ---
    private void carregarUsuariosNaTabela() {
        modeloTabelaUsuarios.setRowCount(0);

        try {
            List<Usuario> usuarios = usuarioController.findAll();
            if (usuarios != null && !usuarios.isEmpty()) {
                for (Usuario usuario : usuarios) {
                    modeloTabelaUsuarios.addRow(new Object[]{
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getCpf(),
                        usuario.getEmail(),
                        usuario.getTipoAcesso().toString(), // Convertendo Enum para String para exibição
                        "Remover",
                        "Editar"
                    });
                }
            } else {
                System.out.println("Nenhum usuário encontrado no banco de dados.");
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar usuários: " + e.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void realizarBuscaUsuarios() {
        String termoBusca = textBuscar.getText();
        modeloTabelaUsuarios.setRowCount(0);

        if (StringUtil.isNullOrEmpty(termoBusca) || termoBusca.equals("Buscar...")) {
            carregarUsuariosNaTabela();
            return;
        }

        try {
            if (termoBusca.matches("\\d+")) { // Tenta buscar por CPF (se o termo for um número)
                Optional<Usuario> usuarioOpt = usuarioController.findByCpf(termoBusca);
                if (usuarioOpt.isPresent()) {
                    Usuario usuario = usuarioOpt.get();
                    modeloTabelaUsuarios.addRow(new Object[]{
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getCpf(),
                        usuario.getEmail(),
                        usuario.getTipoAcesso().toString(),
                        "Remover",
                        "Editar"
                    });
                    System.out.println("Usuário encontrado por CPF: " + usuario.getNome());
                } else {
                    JOptionPane.showMessageDialog(this, "Usuário com CPF " + termoBusca + " não encontrado.", "Busca por CPF", JOptionPane.INFORMATION_MESSAGE);
                }
            } else { // Se não é um número, tenta buscar por nome
                List<Usuario> usuariosEncontrados = usuarioController.findByNomeContaining(termoBusca);
                if (usuariosEncontrados != null && !usuariosEncontrados.isEmpty()) {
                    for (Usuario usuario : usuariosEncontrados) {
                        modeloTabelaUsuarios.addRow(new Object[]{
                            usuario.getId(),
                            usuario.getNome(),
                            usuario.getCpf(),
                            usuario.getEmail(),
                            usuario.getTipoAcesso().toString(),
                            "Remover",
                            "Editar"
                        });
                    }
                    System.out.println(usuariosEncontrados.size() + " usuários encontrados por nome.");
                } else {
                    JOptionPane.showMessageDialog(this, "Nenhum usuário encontrado com o nome '" + termoBusca + "'.", "Busca por Nome", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao realizar a busca: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdcionar;
    private javax.swing.JButton btnInicio;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField textBuscar;
    // End of variables declaration//GEN-END:variables
}
