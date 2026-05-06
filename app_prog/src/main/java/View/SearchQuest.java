package View;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import CRUD.QuestionQuerys;

public class SearchQuest extends JFrame {

    private JTextField idField = new JTextField(14);
    private JTextField authorField = new JTextField(14);
    private JTextField temaField = new JTextField(14);
    private JRadioButton testOption = new JRadioButton("Test");
    private JRadioButton desarrolloOption = new JRadioButton("Desarrollo");
    private JTextArea resultsArea = new JTextArea(12, 30);
    private JButton buscar = new JButton("Buscar");
    private JButton volver = new JButton("Volver");

    public SearchQuest() {
        UiUtils.setupFrame(this, "Buscar pregunta", 520, 500);

        JPanel panel = UiUtils.createPanel();
        panel.add(UiUtils.createTitle("Buscar preguntas"), UiUtils.gbc(0, 0, 2));

        panel.add(new JLabel("ID:"), UiUtils.gbc(0, 1, 1));
        panel.add(idField, UiUtils.gbc(1, 1, 1));

        panel.add(new JLabel("Autor:"), UiUtils.gbc(0, 2, 1));
        panel.add(authorField, UiUtils.gbc(1, 2, 1));

        panel.add(new JLabel("Tema:"), UiUtils.gbc(0, 3, 1));
        panel.add(temaField, UiUtils.gbc(1, 3, 1));

        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(testOption);
        typeGroup.add(desarrolloOption);
        testOption.setSelected(true);

        panel.add(testOption, UiUtils.gbc(0, 4, 1));
        panel.add(desarrolloOption, UiUtils.gbc(1, 4, 1));

        panel.add(buscar, UiUtils.gbc(0, 5, 1));
        panel.add(volver, UiUtils.gbc(1, 5, 1));

        resultsArea.setEditable(false);
        resultsArea.setLineWrap(true);
        resultsArea.setWrapStyleWord(true);
        resultsArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));

        panel.add(new JScrollPane(resultsArea), UiUtils.gbc(0, 6, 2));
        UiUtils.styleButton(buscar);
        UiUtils.styleButton(volver);

        setContentPane(panel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchQuestions();
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

    private void searchQuestions() {
        HashMap<String, String> filters = new HashMap<>();
        if (!idField.getText().trim().isEmpty()) {
            filters.put("id", idField.getText().trim());
        }
        if (!authorField.getText().trim().isEmpty()) {
            filters.put("autor", authorField.getText().trim());
        }
        if (!temaField.getText().trim().isEmpty()) {
            filters.put("tema", temaField.getText().trim());
        }

        boolean isTest = testOption.isSelected();
        String result = QuestionQuerys.searchQuestions(filters, isTest);
        if (result.isEmpty()) {
            resultsArea.setText("No se encontraron resultados.");
        } else {
            resultsArea.setText(result);
        }
    }
}
