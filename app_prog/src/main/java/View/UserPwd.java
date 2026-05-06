package View;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import CRUD.UserQuerys;

public class UserPwd extends JFrame {

	private JTextField userField = new JTextField(18);
	private JPasswordField passwordField = new JPasswordField(18);
	private JButton confirm = new JButton("Iniciar sesion");
	private JButton cancel = new JButton("Cancelar");
	private JButton register = new JButton("Registrarse");

	public UserPwd() {
		UiUtils.setupFrame(this, "Iniciar sesion", 480, 320);

		JPanel outerPanel = UiUtils.createAppPanel();
		JPanel panel = UiUtils.createCardPanel();
		JLabel title = UiUtils.createTitle("Bienvenido a ExamQuest");
		GridBagConstraints titleConstraints = UiUtils.gbc(0, 0, 2);
		panel.add(title, titleConstraints);

		panel.add(new JLabel("Usuario:"), UiUtils.gbc(0, 1, 1));
		panel.add(userField, UiUtils.gbc(1, 1, 1));

		panel.add(new JLabel("Contrasena:"), UiUtils.gbc(0, 2, 1));
		panel.add(passwordField, UiUtils.gbc(1, 2, 1));

		JPanel buttons = new JPanel(new GridLayout(1, 3, 10, 0));
		buttons.setOpaque(false);
		buttons.add(cancel);
		buttons.add(confirm);
		buttons.add(register);

		UiUtils.styleButton(cancel);
		UiUtils.styleButton(confirm);
		UiUtils.styleButton(register);

		GridBagConstraints buttonConstraints = UiUtils.gbc(0, 3, 2);
		buttonConstraints.fill = GridBagConstraints.NONE;
		panel.add(buttons, buttonConstraints);

		outerPanel.add(panel, java.awt.BorderLayout.CENTER);
		setContentPane(outerPanel);
		getRootPane().setDefaultButton(confirm);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});

		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new NewUser();
			}
		});
	}

	public static void main(String[] args) {
		new UserPwd();
	}

	private void login() {
		String username = userField.getText().trim();
		String password = new String(passwordField.getPassword());

		if (username.isEmpty()) {
			UiUtils.showError(this, "Ingrese su nombre de usuario.");
			userField.requestFocusInWindow();
			return;
		}

		if (password.isEmpty()) {
			UiUtils.showError(this, "Ingrese su contrasena.");
			passwordField.requestFocusInWindow();
			return;
		}

		if (UserQuerys.authenticateUser(username, password)) {
			UserQuerys.logOperation(UserQuerys.getUserId(username), "Inicio de sesion", username + " inicio sesion");
			dispose();
			new ChoiceWin();
		} else {
			UiUtils.showError(this, "Usuario o contrasena incorrectos.");
			userField.requestFocusInWindow();
		}
	}
}
