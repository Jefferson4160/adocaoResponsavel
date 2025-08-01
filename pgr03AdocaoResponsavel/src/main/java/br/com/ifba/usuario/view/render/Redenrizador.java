/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.view.render;

import java.awt.Component;
import javax.swing.ImageIcon;      // Importe para usar ícones
import javax.swing.JLabel;         // JLabel é usado para exibir ícones
import javax.swing.JTable;
import javax.swing.SwingConstants; // Para centralizar o ícone
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author ADMIN
 */
public class Redenrizador extends JLabel implements TableCellRenderer{
    
    public Redenrizador(ImageIcon icon) {
        setIcon(icon); // Define o ícone a ser exibido por este renderizador
        setOpaque(true); // Garante que o fundo seja pintado corretamente
        setHorizontalAlignment(SwingConstants.CENTER); // Centraliza o ícone dentro da célula
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        // Define o fundo da célula com base na seleção (para que o ícone não se misture)
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }
        return this; // Retorna esta própria instância do JLabel (com a imagem) para ser desenhada
    }
}

