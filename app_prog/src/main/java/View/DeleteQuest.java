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

public class DeleteQuest extends JFrame {

    private JTextField idField = new JTextField(18);
    private JButton eliminar = new JButton("Eliminar");
    private JButton volver = new JButton("Volver");

    public DeleteQuest() {
        UiUtils.setupFrame(this, "Eliminar pregunta", 380, 220);

        JPanel panel = UiUtils.createPanel();
        panel.add(UiUtils.createTitle("Eliminar pregunta"), UiUtils.gbc(0, 0, 2));

        panel.add(new JLabel("ID de la pregunta:"), UiUtils.gbc(0, 1, 1));
        panel.add(idField, UiUtils.gbc(1, 1, 1));

        panel.add(eliminar, UiUtils.gbc(0, 2, 1));
        panel.add(volver, UiUtils.gbc(1, 2, 1));

        UiUtils.styleButton(eliminar);
        UiUtils.styleButton(volver);

        setContentPane(panel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteQuestion();
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

    private void deleteQuestion() {
        String idText = idField.getText().trim();

        if (idText.isEmpty()) {
            UiUtils.showError(this, "Ingrese el ID de la pregunta a eliminar.");
            idField.requestFocusInWindow();
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            QuestionQuerys.rmQuest(id);
            UiUtils.showInfo(this, "Pregunta eliminada correctamente.");
            dispose();
            new ChoiceWin();
        } catch (NumberFormatException ex) {
            UiUtils.showError(this, "El ID debe ser un n�mero v�lido.");
            idField.requestFocusInWindow();
        }
    }
}
