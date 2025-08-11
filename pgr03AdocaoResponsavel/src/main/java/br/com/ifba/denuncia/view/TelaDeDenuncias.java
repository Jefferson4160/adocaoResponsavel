/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.denuncia.view;

/**
 *
 * @author luis2
 */

import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.controller.UsuarioIController;
import java.util.Optional;
import br.com.ifba.adotante.entity.Adotante;
import br.com.ifba.denuncia.controller.IDenunciaController;
import br.com.ifba.denuncia.domain.entity.Denuncia;
import br.com.ifba.denuncia.domain.entity.StatusDenuncia;
import br.com.ifba.usuario.controller.AdotanteIController;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TelaDeDenuncias extends javax.swing.JFrame {

    @Autowired
    private IDenunciaController denunciaController;
    @Autowired
    private AdotanteIController adotanteController;
    @Autowired
    private UsuarioIController usuarioController;
    
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public TelaDeDenuncias() {
        initComponents();
    }

    @PostConstruct
    public void init() {
        this.setLocationRelativeTo(null);
        carregarTabelaAdotantesComDenuncia();
        
        // Adiciona um listener para a tabela de adotantes
        tblAdotantes.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tblAdotantes.getSelectedRow() != -1) {
                int selectedRow = tblAdotantes.getSelectedRow();
                Long adotanteId = (Long) tblAdotantes.getValueAt(selectedRow, 0);
                carregarTabelaDenuncias(adotanteId);
            }
        });
    }
    
    private void carregarTabelaAdotantesComDenuncia() {
        DefaultTableModel model = (DefaultTableModel) tblAdotantes.getModel();
        model.setRowCount(0);

        List<Adotante> adotantes = adotanteController.buscarAdotantesComDenuncias();
        
        for (Adotante adotante : adotantes) {
            model.addRow(new Object[]{
                adotante.getId(),
                adotante.getNome(),
                adotante.getCpf()
            });
        }
    }
    
    private void carregarTabelaDenuncias(Long adotanteId) {
        DefaultTableModel model = (DefaultTableModel) tblDenuncias.getModel();
        model.setRowCount(0);
        
        List<Denuncia> denuncias = denunciaController.findByAdotanteId(adotanteId);
        
        for(Denuncia denuncia : denuncias) {
            model.addRow(new Object[]{
                denuncia.getId(),
                denuncia.getDataDenuncia().format(FORMATTER),
                denuncia.getStatus().toString()
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblAdotantes = new javax.swing.JTable();
        lblTitulo = new javax.swing.JLabel();
        lblAdotantes = new javax.swing.JLabel();
        lblDenuncias = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDenuncias = new javax.swing.JTable();
        btnAdicionar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnDetalhes = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gerenciamento de Denúncias");

        tblAdotantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] { "ID Adotante", "Nome", "CPF" }
        ) {
            boolean[] canEdit = new boolean [] { false, false, false };
            public boolean isCellEditable(int rowIndex, int columnIndex) { return canEdit [columnIndex]; }
        });
        jScrollPane1.setViewportView(tblAdotantes);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18));
        lblTitulo.setText("Painel de Controle de Denúncias");

        lblAdotantes.setFont(new java.awt.Font("Segoe UI", 1, 14));
        lblAdotantes.setText("Adotantes com Denúncias");

        lblDenuncias.setFont(new java.awt.Font("Segoe UI", 1, 14));
        lblDenuncias.setText("Denúncias do Adotante Selecionado");

        tblDenuncias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] { "ID Denúncia", "Data", "Status" }
        ) {
            boolean[] canEdit = new boolean [] { false, false, false };
            public boolean isCellEditable(int rowIndex, int columnIndex) { return canEdit [columnIndex]; }
        });
        jScrollPane2.setViewportView(tblDenuncias);

        btnAdicionar.setText("Nova Denúncia");
        btnAdicionar.addActionListener(evt -> btnAdicionarActionPerformed(evt));

        btnEditar.setText("Editar Status");
        btnEditar.addActionListener(evt -> btnEditarActionPerformed(evt));

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
                        .addComponent(lblAdotantes)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTitulo, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDenuncias)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnAdicionar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnEditar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnDetalhes)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(lblTitulo)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAdotantes)
                    .addComponent(lblDenuncias))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdicionar)
                            .addComponent(btnEditar)
                            .addComponent(btnDetalhes))))
                .addGap(20, 20, 20))
        );
        pack();
    }// </editor-fold>                        

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {                                             
        String cpfAdotante = JOptionPane.showInputDialog(this, "Digite o CPF do adotante a ser denunciado:", "Nova Denúncia", JOptionPane.PLAIN_MESSAGE);
        if (cpfAdotante == null || cpfAdotante.trim().isEmpty()) {
            return;
        }

        // CORREÇÃO: Usamos o usuarioController, que tem o método correto para buscar por CPF.
        Optional<Usuario> usuarioOpt = usuarioController.buscarUsuarioPorCpf(cpfAdotante);

        // Verificamos se o usuário existe e se ele é realmente um Adotante.
        if (usuarioOpt.isEmpty() || !(usuarioOpt.get() instanceof Adotante)) {
            JOptionPane.showMessageDialog(this, "Nenhum adotante encontrado com o CPF informado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Se tudo estiver correto, fazemos o cast e continuamos.
        Adotante adotanteDenunciado = (Adotante) usuarioOpt.get();
        
        JTextArea txtDescricao = new JTextArea(5, 30);
        int result = JOptionPane.showConfirmDialog(this, new JScrollPane(txtDescricao), "Digite a descrição da denúncia:", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            String descricao = txtDescricao.getText();
            if (descricao.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "A descrição não pode estar vazia.", "Erro", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Denuncia novaDenuncia = new Denuncia();
            novaDenuncia.setDenunciado(adotanteDenunciado);
            novaDenuncia.setDescricao(descricao);
            novaDenuncia.setDataDenuncia(LocalDateTime.now());
            novaDenuncia.setStatus(StatusDenuncia.EM_ANALISE);
            
            denunciaController.saveDenuncia(novaDenuncia);
            JOptionPane.showMessageDialog(this, "Denúncia registrada com sucesso!");
            
            carregarTabelaAdotantesComDenuncia();
            ((DefaultTableModel) tblDenuncias.getModel()).setRowCount(0);
        }
    }

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = tblDenuncias.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma denúncia na tabela para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long denunciaId = (Long) tblDenuncias.getValueAt(selectedRow, 0);
        Denuncia denuncia = denunciaController.findById(denunciaId);

        JComboBox<StatusDenuncia> comboStatus = new JComboBox<>(StatusDenuncia.values());
        comboStatus.setSelectedItem(denuncia.getStatus());
        
        int result = JOptionPane.showConfirmDialog(this, comboStatus, "Selecione o novo status:", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            denuncia.setStatus((StatusDenuncia) comboStatus.getSelectedItem());
            denunciaController.updateDenuncia(denuncia);
            JOptionPane.showMessageDialog(this, "Status da denúncia atualizado com sucesso!");
            carregarTabelaDenuncias(denuncia.getDenunciado().getId());
        }
    }

    private void btnDetalhesActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = tblDenuncias.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma denúncia para ver os detalhes.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long denunciaId = (Long) tblDenuncias.getValueAt(selectedRow, 0);
        Denuncia denuncia = denunciaController.findById(denunciaId);
        
        JOptionPane.showMessageDialog(this, denuncia.getDescricao(), "Detalhes da Denúncia #" + denunciaId, JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Variables declaration - do not modify                     
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnDetalhes;
    private javax.swing.JButton btnEditar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAdotantes;
    private javax.swing.JLabel lblDenuncias;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tblAdotantes;
    private javax.swing.JTable tblDenuncias;
    // End of variables declaration                   
}