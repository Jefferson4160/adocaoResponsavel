/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.ifba.agendaDeVacina.view;

import br.com.ifba.agendaDeVacina.controller.AgendaDeVacinaIController;
import br.com.ifba.agendaDeVacina.entity.AgendaDeVacina;
import br.com.ifba.animal.controller.AnimalIController;
import br.com.ifba.animal.entity.Animal;
import br.com.ifba.usuario.controller.UsuarioIController;
import br.com.ifba.usuario.entity.Usuario; 
import br.com.ifba.usuario.view.render.Redenrizador;
import jakarta.annotation.PostConstruct;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ADMIN
 */
@Component
public class TelaAgendaDeVacinas extends javax.swing.JFrame {

    @Autowired
    private AnimalIController animalController;
    @Autowired
    private AgendaDeVacinaIController agendaDeVacinaController;
    @Autowired
    private UsuarioIController usuarioController;

    private DefaultTableModel modeloTabelaAnimais;
    private DefaultTableModel modeloTabelaVacinas;
    
    private ImageIcon iconeAdicionar;
    private ImageIcon iconeDetalhes;
    
    private Long animalIdSelecionado;
    
    public TelaAgendaDeVacinas() {
        initComponents();
    }
    
    @PostConstruct
    public void init() {
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        carregarIcones();
        configurarTabelaAnimais();
        configurarTabelaVacinas();
        carregarAnimaisNaTabela();
        configurarAcoes();
    }
    
    private void carregarIcones() {
        try {
            iconeAdicionar = new ImageIcon(getClass().getResource("/images/icons8-adicionar-25.png"));
            iconeDetalhes = new ImageIcon(getClass().getResource("/images/icons8-detalhes-25.png"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar ícones: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void configurarTabelaAnimais() {
        String[] nomeColunas = {"ID", "Nome", "Espécie", "Registrar Vacina", "Detalhes"};
        modeloTabelaAnimais = new DefaultTableModel(nomeColunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return getColumnName(column).equals("Registrar Vacina") || getColumnName(column).equals("Detalhes");
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 3 || columnIndex == 4) {
                    return ImageIcon.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };
        tableListaAnimais.setModel(modeloTabelaAnimais);

        tableListaAnimais.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel columnModel = tableListaAnimais.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(100);
        tableListaAnimais.setRowHeight(30);

        if (iconeAdicionar != null) {
            columnModel.getColumn(3).setCellRenderer(new Redenrizador(iconeAdicionar));
        }
        if (iconeDetalhes != null) {
            columnModel.getColumn(4).setCellRenderer(new Redenrizador(iconeDetalhes));
        }
    }
    
    private void configurarTabelaVacinas() {
        String[] nomeColunas = {"Vacina", "Usuário Responsável", "Data"};
        modeloTabelaVacinas = new DefaultTableModel(nomeColunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tableListaVacinas.setModel(modeloTabelaVacinas);
        
        tableListaVacinas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel columnModel = tableListaVacinas.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(80);
    }
    
    private void carregarAnimaisNaTabela() {
        modeloTabelaAnimais.setRowCount(0);
        try {
            List<Animal> animais = animalController.findAll();
            for (Animal animal : animais) {
                modeloTabelaAnimais.addRow(new Object[]{
                    animal.getId(),
                    animal.getNome(),
                    animal.getEspecie(),
                    null,
                    null
                });
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar animais: " + e.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void carregarVacinasNaTabela(Long idAnimal) {
        modeloTabelaVacinas.setRowCount(0);
        if (idAnimal == null) return;
        
        try {
            List<AgendaDeVacina> vacinas = agendaDeVacinaController.buscarAgendasDeVacinaPorAnimalId(idAnimal);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (AgendaDeVacina vacina : vacinas) {
                modeloTabelaVacinas.addRow(new Object[]{
                    vacina.getNomeVacina(),
                    vacina.getUsuario().getNome(),
                    vacina.getDataAplicacao().format(formatter)
                });
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar vacinas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void configurarAcoes() {
        tableListaAnimais.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = tableListaAnimais.columnAtPoint(e.getPoint());
                int row = tableListaAnimais.rowAtPoint(e.getPoint());

                if (row >= 0 && column >= 0) {
                    Long animalId = (Long) modeloTabelaAnimais.getValueAt(row, 0);
                    String clickedColumnName = tableListaAnimais.getColumnName(column);

                    if (clickedColumnName.equals("Registrar Vacina")) {
                        abrirTelaCadastroVacina(animalId);
                    } else if (clickedColumnName.equals("Detalhes")) {
                        exibirDetalhesAnimal(animalId);
                    }
                }
            }
        });

        
    }

    private void abrirTelaCadastroVacina(Long animalId) {
        try {
            Animal animal = animalController.findById(animalId);
            if (animal != null) {
                Usuario usuarioLogado = usuarioController.buscarUsuarioPorId(1L).orElse(null);

                if (usuarioLogado == null) {
                    JOptionPane.showMessageDialog(this, "Usuário responsável não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                TelaRegistroDeVacina tela = new TelaRegistroDeVacina(this, true, 
                                                                      animal, 
                                                                      null, // <-- null no lugar do ID
                                                                      agendaDeVacinaController,
                                                                      usuarioController);
                // Adiciona o WindowListener
                tela.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        carregarVacinasNaTabela(animal.getId());
                    }
                });
                tela.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Animal não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro ao abrir tela: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void exibirDetalhesAnimal(Long animalId) {
        try {
            Animal animal = animalController.findById(animalId);
            if (animal != null) {
                animalIdSelecionado = animal.getId();
                
                textNome.setText(animal.getNome());
                textEspecie.setText(animal.getEspecie());
                textIdade.setText(String.valueOf(animal.getIdade()));
                
                carregarVacinasNaTabela(animalId);
            } else {
                JOptionPane.showMessageDialog(this, "Animal não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar detalhes do animal: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tableListaVacinas = new javax.swing.JTable();
        lblTituloLista = new javax.swing.JLabel();
        lblTituloDetalhes = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableListaAnimais = new javax.swing.JTable();
        lblNome = new javax.swing.JLabel();
        lblEspecie = new javax.swing.JLabel();
        lblIdade = new javax.swing.JLabel();
        textNome = new javax.swing.JTextField();
        textEspecie = new javax.swing.JTextField();
        textIdade = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tableListaVacinas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tableListaVacinas);

        lblTituloLista.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTituloLista.setText("Lista de animais");

        lblTituloDetalhes.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTituloDetalhes.setText("Detalhes do animal");

        tableListaAnimais.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tableListaAnimais);

        lblNome.setText("Nome");

        lblEspecie.setText("Especie");

        lblIdade.setText("Idade");

        textNome.setEditable(false);
        textNome.setText("...");

        textEspecie.setEditable(false);
        textEspecie.setText("...");

        textIdade.setEditable(false);
        textIdade.setText("...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTituloLista)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblIdade, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblEspecie)
                                .addGap(298, 298, 298)))
                        .addGap(82, 82, 82))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblNome)
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textEspecie, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textNome, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textIdade, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblTituloDetalhes))
                        .addGap(11, 11, 11))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(lblTituloLista))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblTituloDetalhes)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNome)
                            .addComponent(textNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEspecie)
                            .addComponent(textEspecie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblIdade)
                            .addComponent(textIdade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaAgendaDeVacinas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaAgendaDeVacinas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaAgendaDeVacinas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaAgendaDeVacinas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaAgendaDeVacinas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblEspecie;
    private javax.swing.JLabel lblIdade;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblTituloDetalhes;
    private javax.swing.JLabel lblTituloLista;
    private javax.swing.JTable tableListaAnimais;
    private javax.swing.JTable tableListaVacinas;
    private javax.swing.JTextField textEspecie;
    private javax.swing.JTextField textIdade;
    private javax.swing.JTextField textNome;
    // End of variables declaration//GEN-END:variables
}
