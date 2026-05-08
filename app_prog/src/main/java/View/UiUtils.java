package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

// class for interface strucutre and styling
public class UiUtils {

	// sets app style when the class is called
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

	// sets up the main frame
	public static void setupFrame(JFrame frame, String title, int width, int height) {
		frame.setTitle(title);
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	// enables the modification of the structure for the app panels
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

	// enables the modification of the structure for the app panels with both horizontal and vertical filling
	public static GridBagConstraints gbcBoth(int x, int y, int width) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = width;
		constraints.insets = new Insets(8, 8, 8, 8);
		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		return constraints;
	}

	// creates the outer panel
	public static JPanel createAppPanel() {
		JPanel panel = new JPanel(new java.awt.BorderLayout());
		panel.setBackground(new Color(240, 244, 250));
		panel.setBorder(new EmptyBorder(16, 16, 16, 16));
		return panel;
	}

	// creates the inner panel with card layout
	public static JPanel createCardPanel() {
		JPanel panel = new JPanel(new java.awt.GridBagLayout());
		panel.setBackground(Color.WHITE);
		panel.setOpaque(true);
		panel.setBorder(new javax.swing.border.CompoundBorder(
				new javax.swing.border.LineBorder(new Color(220, 220, 220), 1, true),
				new EmptyBorder(20, 20, 20, 20)));
		return panel;
	}

	// applies styling to the buttons
	public static void styleButton(JButton button) {
		button.setOpaque(true);
		button.setBackground(new Color(38, 110, 218));
		button.setForeground(Color.WHITE);
		button.setBorder(new EmptyBorder(10, 14, 10, 14));
		button.setFont(button.getFont().deriveFont(Font.BOLD, 14f));
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button.setFocusPainted(false);
		button.setPreferredSize(new Dimension(280, 42));
	}

	// applies styling to the text areas
	public static void styleTextArea(JTextArea area) {
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setFont(new Font("SansSerif", Font.PLAIN, 13));
		area.setBorder(new EmptyBorder(10, 10, 10, 10));
		area.setBackground(new Color(250, 252, 255));
	}

	// applies styling to the radio buttons
	public static void styleRadioButton(JRadioButton radioButton) {
		radioButton.setBackground(new Color(240, 244, 250));
		radioButton.setFont(radioButton.getFont().deriveFont(Font.PLAIN, 13f));
	}

	public static void showInfo(Component parent, String message) {
		JOptionPane.showMessageDialog(parent, message, "Informacion", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void showError(Component parent, String message) {
		JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	// creates the title labels
	public static JLabel createTitle(String text) {
		var label = new JLabel(text, JLabel.CENTER);
		label.setFont(label.getFont().deriveFont(18f).deriveFont(java.awt.Font.BOLD));
		return label;
	}
}
