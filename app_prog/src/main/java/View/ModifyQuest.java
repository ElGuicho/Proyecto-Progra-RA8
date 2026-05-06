package View;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import CRUD.QuestionQuerys;

public class ModifyQuest extends JFrame {

    private JTextField idField = new JTextField(18);
    private JTextField nuevoField = new JTextField(18);
    private JButton modificar = new JButton("Modificar");
    private JButton volver = new JButton("Volver");

    public ModifyQuest() {
        UiUtils.setupFrame(this, "Modificar pregunta", 460, 260);

        JPanel outerPanel = UiUtils.createAppPanel();
        JPanel panel = UiUtils.createCardPanel();
        panel.add(UiUtils.createTitle("Modificar pregunta"), UiUtils.gbc(0, 0, 2));

        panel.add(new JLabel("ID de la pregunta:"), UiUtils.gbc(0, 1, 1));
        panel.add(idField, UiUtils.gbc(1, 1, 1));

        panel.add(new JLabel("Nuevo enunciado:"), UiUtils.gbc(0, 2, 1));
        panel.add(nuevoField, UiUtils.gbc(1, 2, 1));

        panel.add(modificar, UiUtils.gbc(0, 3, 1));
        panel.add(volver, UiUtils.gbc(1, 3, 1));

        UiUtils.styleButton(modificar);
        UiUtils.styleButton(volver);

        outerPanel.add(panel, java.awt.BorderLayout.CENTER);
        setContentPane(outerPanel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyQuestion();
            }
        });

        volver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ChoiceWin();
            }
        });
    }

    private void modifyQuestion() {
        String idText = idField.getText().trim();
        String nuevoEnunciado = nuevoField.getText().trim();

        if (idText.isEmpty()) {
            UiUtils.showError(this, "Ingrese el ID de la pregunta.");
            idField.requestFocusInWindow();
            return;
        }

        if (nuevoEnunciado.isEmpty()) {
            UiUtils.showError(this, "Ingrese un nuevo enunciado.");
            nuevoField.requestFocusInWindow();
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            if (QuestionQuerys.updateQuestionEnunciado(id, nuevoEnunciado)) {
                UiUtils.showInfo(this, "Pregunta modificada correctamente.");
                dispose();
                new ChoiceWin();
            } else {
                UiUtils.showError(this, "No se encontr� una pregunta con ese ID.");
            }
        } catch (NumberFormatException ex) {
            UiUtils.showError(this, "El ID debe ser un n�mero v�lido.");
            idField.requestFocusInWindow();
        }
    }
}
