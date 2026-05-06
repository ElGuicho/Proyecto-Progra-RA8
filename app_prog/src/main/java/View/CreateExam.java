package View;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

import CRUD.ExamQuerys;

public class CreateExam extends JFrame {

    private JSpinner numberField = new JSpinner(new SpinnerNumberModel(3, 1, 20, 1));
    private JRadioButton testOption = new JRadioButton("Preguntas tipo TEST");
    private JRadioButton desarrolloOption = new JRadioButton("Preguntas tipo DESARROLLO");
    private JTextArea previewArea = new JTextArea(14, 36);
    private JButton generar = new JButton("Generar examen");
    private JButton volver = new JButton("Volver");

    public CreateExam() {
        UiUtils.setupFrame(this, "Crear examen", 640, 560);

        JPanel outerPanel = UiUtils.createAppPanel();
        JPanel panel = UiUtils.createCardPanel();
        panel.add(UiUtils.createTitle("Crear examen"), UiUtils.gbc(0, 0, 2));

        panel.add(new JLabel("Cantidad de preguntas:"), UiUtils.gbc(0, 1, 1));
        panel.add(numberField, UiUtils.gbc(1, 1, 1));

        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(testOption);
        typeGroup.add(desarrolloOption);
        testOption.setSelected(true);

        UiUtils.styleRadioButton(testOption);
        UiUtils.styleRadioButton(desarrolloOption);

        panel.add(testOption, UiUtils.gbc(0, 2, 1));
        panel.add(desarrolloOption, UiUtils.gbc(1, 2, 1));

        panel.add(generar, UiUtils.gbc(0, 3, 1));
        panel.add(volver, UiUtils.gbc(1, 3, 1));

        previewArea.setEditable(false);
        UiUtils.styleTextArea(previewArea);
        panel.add(new JScrollPane(previewArea), UiUtils.gbc(0, 4, 2));

        UiUtils.styleButton(generar);
        UiUtils.styleButton(volver);

        outerPanel.add(panel, java.awt.BorderLayout.CENTER);
        setContentPane(outerPanel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        generar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateExam();
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

    private void generateExam() {
        int count = (Integer) numberField.getValue();
        boolean isTest = testOption.isSelected();
        String summary = ExamQuerys.examRndQuestsSummary(count, new Integer[0], isTest);
        previewArea.setText(summary == null || summary.isEmpty() ? "No hay preguntas disponibles." : summary);
    }
}
