/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.campanha.view;

/**
 *
 * @author luis2
 */

import br.com.ifba.campanha.controller.ICampanhaController;
import br.com.ifba.campanha.domain.entity.Campanha;
import br.com.ifba.campanha.domain.entity.StatusCampanha;
import br.com.ifba.campanha.domain.entity.TipoCampanha;
import br.com.ifba.voluntario.entity.Voluntario;
import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Tela para gerenciamento de Campanhas.
 * @author luis
 */
@Component
public class TelaCampanha extends javax.swing.JFrame {

    @Autowired
    private ICampanhaController campanhaController;
    
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TelaCampanha() {
        initComponents();
    }

    @PostConstruct
    public void init() {
        this.setLocationRelativeTo(null);
        carregarTabelaCampanhas();
    }
    
    private void carregarTabelaCampanhas() {
        DefaultTableModel model = (DefaultTableModel) tblCampanhas.getModel();
        model.setRowCount(0);
        
        List<Campanha> campanhas = campanhaController.findAllCampanhas();
        
        for (Campanha campanha : campanhas) {
            model.addRow(new Object[]{
                campanha.getId(),
                campanha.getNome(),
                campanha.getTipo(),
                campanha.getStatus(),
                campanha.getDataInicio() != null ? campanha.getDataInicio().format(DATE_FORMATTER) : "",
                campanha.getDataFim() != null ? campanha.getDataFim().format(DATE_FORMATTER) : ""
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCampanhas = new javax.swing.JTable();
        btnAdicionar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        btnDetalhes = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerenciamento de Campanhas e Eventos");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setText("Painel de Campanhas e Eventos");

        tblCampanhas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID", "Nome", "Tipo", "Status", "Início", "Fim"
            }
        ) {
            boolean[] canEdit = new boolean [] { false, false, false, false, false, false };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblCampanhas);

        btnAdicionar.setText("Adicionar Campanha");
        btnAdicionar.addActionListener(evt -> btnAdicionarActionPerformed(evt));

        btnEditar.setText("Editar Campanha");
        btnEditar.addActionListener(evt -> btnEditarActionPerformed(evt));

        btnRemover.setText("Remover Campanha");
        btnRemover.addActionListener(evt -> btnRemoverActionPerformed(evt));

        btnDetalhes.setText("Ver Detalhes");
        btnDetalhes.addActionListener(evt -> btnDetalhesActionPerformed(evt));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAdicionar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRemover, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDetalhes, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblTitulo)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDetalhes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>                        

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {
        // Campos de entrada para a nova campanha
        JTextField nomeField = new JTextField();
        JTextArea descField = new JTextArea(3, 20);
        JTextField dataInicioField = new JTextField("dd/mm/aaaa");
        JTextField dataFimField = new JTextField("dd/mm/aaaa");
        JComboBox<TipoCampanha> tipoBox = new JComboBox<>(TipoCampanha.values());
        JTextField metaField = new JTextField("0.00");

        Object[] campos = {
            "Nome:", nomeField,
            "Descrição:", new JScrollPane(descField),
            "Data de Início:", dataInicioField,
            "Data de Fim:", dataFimField,
            "Tipo:", tipoBox,
            "Meta (R$ ou Itens):", metaField
        };

        int result = JOptionPane.showConfirmDialog(this, campos, "Adicionar Nova Campanha", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Campanha campanha = new Campanha();
                campanha.setNome(nomeField.getText());
                campanha.setDescricao(descField.getText());
                campanha.setDataInicio(LocalDate.parse(dataInicioField.getText(), DATE_FORMATTER));
                campanha.setDataFim(LocalDate.parse(dataFimField.getText(), DATE_FORMATTER));
                campanha.setTipo((TipoCampanha) tipoBox.getSelectedItem());
                campanha.setMeta(Double.parseDouble(metaField.getText().replace(",", ".")));
                campanha.setStatus(StatusCampanha.PLANEJADA);
                campanha.setArrecadado(0);

                campanhaController.saveCampanha(campanha);
                JOptionPane.showMessageDialog(this, "Campanha salva com sucesso!");
                carregarTabelaCampanhas();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar campanha: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = tblCampanhas.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma campanha para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) tblCampanhas.getValueAt(selectedRow, 0);
        Campanha campanha = campanhaController.findById(id).orElse(null);

        if (campanha == null) {
            JOptionPane.showMessageDialog(this, "Campanha não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField nomeField = new JTextField(campanha.getNome());
        JTextArea descField = new JTextArea(campanha.getDescricao(), 3, 20);
        JTextField dataInicioField = new JTextField(campanha.getDataInicio().format(DATE_FORMATTER));
        JTextField dataFimField = new JTextField(campanha.getDataFim().format(DATE_FORMATTER));
        JComboBox<TipoCampanha> tipoBox = new JComboBox<>(TipoCampanha.values());
        tipoBox.setSelectedItem(campanha.getTipo());
        JComboBox<StatusCampanha> statusBox = new JComboBox<>(StatusCampanha.values());
        statusBox.setSelectedItem(campanha.getStatus());
        JTextField metaField = new JTextField(String.valueOf(campanha.getMeta()));
        JTextField arrecadadoField = new JTextField(String.valueOf(campanha.getArrecadado()));

        Object[] campos = {
            "Nome:", nomeField,
            "Descrição:", new JScrollPane(descField),
            "Data de Início:", dataInicioField,
            "Data de Fim:", dataFimField,
            "Tipo:", tipoBox,
            "Status:", statusBox,
            "Meta:", metaField,
            "Arrecadado:", arrecadadoField
        };

        int result = JOptionPane.showConfirmDialog(this, campos, "Editar Campanha", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                campanha.setNome(nomeField.getText());
                campanha.setDescricao(descField.getText());
                campanha.setDataInicio(LocalDate.parse(dataInicioField.getText(), DATE_FORMATTER));
                campanha.setDataFim(LocalDate.parse(dataFimField.getText(), DATE_FORMATTER));
                campanha.setTipo((TipoCampanha) tipoBox.getSelectedItem());
                campanha.setStatus((StatusCampanha) statusBox.getSelectedItem());
                campanha.setMeta(Double.parseDouble(metaField.getText().replace(",", ".")));
                campanha.setArrecadado(Double.parseDouble(arrecadadoField.getText().replace(",", ".")));

                campanhaController.updateCampanha(campanha);
                JOptionPane.showMessageDialog(this, "Campanha atualizada com sucesso!");
                carregarTabelaCampanhas();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar campanha: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = tblCampanhas.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma campanha para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) tblCampanhas.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover a campanha ID " + id + "?", "Confirmar Remoção", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                campanhaController.deleteCampanha(id);
                JOptionPane.showMessageDialog(this, "Campanha removida com sucesso!");
                carregarTabelaCampanhas();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao remover campanha: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void btnDetalhesActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = tblCampanhas.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma campanha para ver os detalhes.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) tblCampanhas.getValueAt(selectedRow, 0);
        Campanha campanha = campanhaController.findById(id).orElse(null);

        if (campanha != null) {
            String voluntarios = "Nenhum";
            if (campanha.getVoluntariosResponsaveis() != null && !campanha.getVoluntariosResponsaveis().isEmpty()) {
                voluntarios = campanha.getVoluntariosResponsaveis().stream()
                                     .map(Voluntario::getNome)
                                     .collect(Collectors.joining(", "));
            }
            
            String detalhes = String.format(
                "Nome: %s\n" +
                "Descrição: %s\n" +
                "Período: %s a %s\n" +
                "Tipo: %s\n" +
                "Status: %s\n" +
                "Meta: R$ %.2f\n" +
                "Arrecadado: R$ %.2f\n" +
                "Voluntários: %s",
                campanha.getNome(), campanha.getDescricao(),
                campanha.getDataInicio().format(DATE_FORMATTER), campanha.getDataFim().format(DATE_FORMATTER),
                campanha.getTipo(), campanha.getStatus(),
                campanha.getMeta(), campanha.getArrecadado(),
                voluntarios
            );
            JOptionPane.showMessageDialog(this, detalhes, "Detalhes da Campanha", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnDetalhes;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tblCampanhas;
    // End of variables declaration                   
}