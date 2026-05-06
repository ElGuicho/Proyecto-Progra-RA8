package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class UiUtils {

    static {
        setApplicationStyle();
    }

    public static void setApplicationStyle() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Dejar el estilo por defecto si Nimbus no está disponible.
        }
    }

    public static void setupFrame(JFrame frame, String title, int width, int height) {
        frame.setTitle(title);
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static GridBagConstraints gbc(int x, int y, int width) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = width;
        constraints.insets = new Insets(8, 8, 8, 8);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        return constraints;
    }

    public static JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new java.awt.GridBagLayout());
        panel.setBorder(new EmptyBorder(14, 14, 14, 14));
        panel.setBackground(Color.WHITE);
        panel.setOpaque(true);
        return panel;
    }

    public static void styleButton(JButton button) {
        button.setOpaque(true);
        button.setBackground(new Color(38, 110, 218));
        button.setForeground(Color.WHITE);
        button.setBorder(new EmptyBorder(8, 12, 8, 12));
        button.setFont(button.getFont().deriveFont(Font.BOLD, 13f));
    }

    public static void showInfo(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Informacion", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static JLabel createTitle(String text) {
        var label = new JLabel(text, JLabel.CENTER);
        label.setFont(label.getFont().deriveFont(18f).deriveFont(java.awt.Font.BOLD));
        return label;
    }
}
