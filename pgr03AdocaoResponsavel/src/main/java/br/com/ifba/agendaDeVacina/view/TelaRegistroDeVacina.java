/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package br.com.ifba.agendaDeVacina.view;

import br.com.ifba.agendaDeVacina.controller.AgendaDeVacinaIController;
import br.com.ifba.agendaDeVacina.entity.AgendaDeVacina;
import br.com.ifba.animal.entity.Animal;
import br.com.ifba.usuario.entity.Usuario; 
import br.com.ifba.infrastructure.util.StringUtil;
import br.com.ifba.usuario.controller.UsuarioIController;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame; 
import java.awt.event.ActionEvent;
import java.awt.Frame;
import java.util.List;
import java.util.Optional;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author ADMIN
 */
public class TelaRegistroDeVacina extends javax.swing.JDialog {
    
    private final AgendaDeVacinaIController agendaDeVacinaController;
    private final UsuarioIController usuarioController;
    private final Animal animal;
    private Long idVacinaParaEdicao;
    private List<Usuario> listaUsuarios;
    
    // Construtor completo
    public TelaRegistroDeVacina(Frame parent, boolean modal,
                                Animal animal,
                                Long idVacinaParaEdicao,
                                AgendaDeVacinaIController controller,
                                UsuarioIController usuarioController) {
        super(parent, modal);
        this.animal = animal;
        this.idVacinaParaEdicao = idVacinaParaEdicao;
        this.agendaDeVacinaController = controller;
        this.usuarioController = usuarioController;
        initComponents();
        init();
    }
    
    private void init() {
        this.setLocationRelativeTo(null);
        preencherDadosAnimal();
        carregarUsuarios();
        
        if (idVacinaParaEdicao != null) {
            lblTituloLista.setText("Editar Vacina");
            btnRegistrar.setText("Salvar");
            carregarDadosDaVacinaParaEdicao();
        }
        
        btnRegistrar.addActionListener(this::registrarVacina);
        btnCancelar.addActionListener(e -> dispose());
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (getParent() instanceof TelaAgendaDeVacinas) {
                    ((TelaAgendaDeVacinas) getParent()).carregarVacinasNaTabela(animal.getId());
                }
            }
        });
    }

    private void carregarUsuarios() {
        try {
            listaUsuarios = usuarioController.buscarTodosUsuarios();
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            for (Usuario usuario : listaUsuarios) {
                model.addElement(usuario.getNome());
            }
            boxUsuarios.setModel(model);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar lista de usuários: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void preencherDadosAnimal() {
        if (animal != null) {
            textNome.setText(animal.getNome());
            textEspecie.setText(animal.getEspecie());
            textIdade.setText(String.valueOf(animal.getIdade()));
        }
    }
    
    private void carregarDadosDaVacinaParaEdicao() {
        try {
            Optional<AgendaDeVacina> vacinaOpt = agendaDeVacinaController.buscarAgendaDeVacinaPorId(idVacinaParaEdicao);
            if (vacinaOpt.isPresent()) {
                textVacina.setText(vacinaOpt.get().getNomeVacina());
                Usuario usuarioResp = vacinaOpt.get().getUsuario();
                boxUsuarios.setSelectedItem(usuarioResp.getNome());
            } else {
                JOptionPane.showMessageDialog(this, "Vacina não encontrada para edição.", "Erro", JOptionPane.ERROR_MESSAGE);
                dispose();
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados da vacina: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }
    
    private void registrarVacina(ActionEvent evt) {
        try {
            String nomeVacina = textVacina.getText().trim();
            if (StringUtil.isNullOrEmpty(nomeVacina)) {
                JOptionPane.showMessageDialog(this, "O nome da vacina não pode ser vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario usuarioSelecionado = null;
            String nomeUsuarioSelecionado = (String) boxUsuarios.getSelectedItem();
            if (nomeUsuarioSelecionado != null) {
                for (Usuario u : listaUsuarios) {
                    if (u.getNome().equals(nomeUsuarioSelecionado)) {
                        usuarioSelecionado = u;
                        break;
                    }
                }
            }
            if (usuarioSelecionado == null) {
                throw new RuntimeException("Usuário responsável não selecionado.");
            }
            
            AgendaDeVacina vacina;
            if (idVacinaParaEdicao != null) {
                Optional<AgendaDeVacina> vacinaExistenteOpt = agendaDeVacinaController.buscarAgendaDeVacinaPorId(idVacinaParaEdicao);
                if (vacinaExistenteOpt.isPresent()) {
                    vacina = vacinaExistenteOpt.get();
                } else {
                    throw new RuntimeException("Vacina não encontrada para atualização.");
                }
            } else {
                vacina = new AgendaDeVacina();
            }
            
            vacina.setNomeVacina(nomeVacina);
            vacina.setAnimal(animal);
            vacina.setUsuario(usuarioSelecionado);
            vacina.setDataAplicacao(LocalDate.now());
            
            if (idVacinaParaEdicao != null) {
                agendaDeVacinaController.atualizarAgendaDeVacina(vacina);
            } else {
                agendaDeVacinaController.salvarAgendaDeVacina(vacina);
            }

            JOptionPane.showMessageDialog(this, "Vacina registrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro ao registrar vacina: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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

        lblTituloLista = new javax.swing.JLabel();
        lblNome = new javax.swing.JLabel();
        lblEspecie = new javax.swing.JLabel();
        lblIdade = new javax.swing.JLabel();
        lblAnimal = new javax.swing.JLabel();
        lblVacina = new javax.swing.JLabel();
        textVacina = new javax.swing.JTextField();
        textNome = new javax.swing.JTextField();
        textEspecie = new javax.swing.JTextField();
        textIdade = new javax.swing.JTextField();
        btnRegistrar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblIdade1 = new javax.swing.JLabel();
        boxUsuarios = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblTituloLista.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTituloLista.setText("Registro de Vacina");

        lblNome.setText("Nome:");

        lblEspecie.setText("Especie:");

        lblIdade.setText("Idade:");

        lblAnimal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblAnimal.setText("Animal");

        lblVacina.setText("Nome da Vacina:");

        textVacina.setText("Nome...");

        textNome.setEditable(false);
        textNome.setText("jTextField1");

        textEspecie.setEditable(false);
        textEspecie.setText("jTextField1");

        textIdade.setEditable(false);
        textIdade.setText("jTextField1");

        btnRegistrar.setText("Registrar");

        btnCancelar.setText("Cancelar");

        lblIdade1.setText("Usuario:");

        boxUsuarios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblTituloLista)
                .addGap(137, 137, 137))
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(textVacina, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnRegistrar)
                        .addGap(61, 61, 61)
                        .addComponent(btnCancelar)
                        .addGap(64, 64, 64))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblIdade1)
                            .addGap(18, 18, 18)
                            .addComponent(boxUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblNome)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(textNome, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblAnimal)
                            .addComponent(lblVacina)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEspecie)
                                    .addComponent(lblIdade))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(textIdade, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(textEspecie, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloLista)
                .addGap(11, 11, 11)
                .addComponent(lblAnimal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNome)
                    .addComponent(textNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEspecie)
                    .addComponent(textEspecie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIdade)
                    .addComponent(textIdade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIdade1)
                    .addComponent(boxUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblVacina)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textVacina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrar)
                    .addComponent(btnCancelar))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> boxUsuarios;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JLabel lblAnimal;
    private javax.swing.JLabel lblEspecie;
    private javax.swing.JLabel lblIdade;
    private javax.swing.JLabel lblIdade1;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblTituloLista;
    private javax.swing.JLabel lblVacina;
    private javax.swing.JTextField textEspecie;
    private javax.swing.JTextField textIdade;
    private javax.swing.JTextField textNome;
    private javax.swing.JTextField textVacina;
    // End of variables declaration//GEN-END:variables
}
