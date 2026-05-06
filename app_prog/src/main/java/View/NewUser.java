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

public class NewUser extends JFrame {

    private JTextField userField = new JTextField(18);
    private JPasswordField passwordField = new JPasswordField(18);
    private JButton register = new JButton("Registrar");
    private JButton cancel = new JButton("Cancelar");

    public NewUser() {
        UiUtils.setupFrame(this, "Nuevo usuario", 480, 300);

        JPanel outerPanel = UiUtils.createAppPanel();
        JPanel panel = UiUtils.createCardPanel();
        JLabel title = UiUtils.createTitle("Registrar nuevo usuario");
        panel.add(title, UiUtils.gbc(0, 0, 2));

        panel.add(new JLabel("Usuario:"), UiUtils.gbc(0, 1, 1));
        panel.add(userField, UiUtils.gbc(1, 1, 1));

        panel.add(new JLabel("Contrasena:"), UiUtils.gbc(0, 2, 1));
        panel.add(passwordField, UiUtils.gbc(1, 2, 1));

        JPanel actions = new JPanel(new GridLayout(1, 2, 10, 0));
        actions.setOpaque(false);
        actions.add(cancel);
        actions.add(register);
        UiUtils.styleButton(cancel);
        UiUtils.styleButton(register);
        GridBagConstraints actionConstraints = UiUtils.gbc(0, 3, 2);
        actionConstraints.fill = GridBagConstraints.NONE;
        panel.add(actions, actionConstraints);

        outerPanel.add(panel, java.awt.BorderLayout.CENTER);
        setContentPane(outerPanel);
        getRootPane().setDefaultButton(register);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount();
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new UserPwd();
            }
        });
    }

    private void createAccount() {
        String username = userField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty()) {
            UiUtils.showError(this, "Ingrese un nombre de usuario.");
            userField.requestFocusInWindow();
            return;
        }

        if (password.isEmpty()) {
            UiUtils.showError(this, "Ingrese una contrase�a.");
            passwordField.requestFocusInWindow();
            return;
        }

        if (UserQuerys.userExists(username)) {
            UiUtils.showError(this, "Nombre de usuario ya registrado.");
            userField.requestFocusInWindow();
            return;
        }

        if (!verifyPwd(password)) {
            passwordField.requestFocusInWindow();
            return;
        }

        UserQuerys.createUser(username, password);
		UserQuerys.logOperation(UserQuerys.getUserId(username), "Nuevo Usuario", username + " registrado");
        UiUtils.showInfo(this, "Usuario registrado correctamente.");
        dispose();
        new UserPwd();
    }

    private boolean verifyPwd(String passwd) {
        boolean hasSpecial = passwd.matches(".*[!\"#$%&'()*+,\\-./:;<=>?@\\[\\]\\^_`{|}~].*");
        boolean hasLower = passwd.matches(".*[a-z].*");
        boolean hasUpper = passwd.matches(".*[A-Z].*");
        boolean hasDigit = passwd.matches(".*[0-9].*");

        if (passwd.length() < 12) {
            UiUtils.showError(this, "La contrase�a debe tener 12 o m�s caracteres.");
            return false;
        }
        if (!hasLower) {
            UiUtils.showError(this, "La contrase�a debe contener min�sculas.");
            return false;
        }
        if (!hasUpper) {
            UiUtils.showError(this, "La contrase�a debe contener may�sculas.");
            return false;
        }
        if (!hasDigit) {
            UiUtils.showError(this, "La contrase�a debe contener n�meros.");
            return false;
        }
        if (!hasSpecial) {
            UiUtils.showError(this, "La contrase�a debe contener caracteres especiales.");
            return false;
        }
        return true;
    }
}
