package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import CRUD.UserQuerys;
import Model.PreguntaDesarrollo;
import Model.PreguntaTest;

public class NewQuest extends JFrame {

    private JRadioButton testOption = new JRadioButton("Pregunta tipo TEST");
    private JRadioButton desarrolloOption = new JRadioButton("Pregunta tipo DESARROLLO");
	private JTextField curso = new JTextField(20);
	private JTextField grupo = new JTextField(20);
	private JTextField modulo = new JTextField(20);
	private JTextField ra = new JTextField(20);
	private JTextField tema = new JTextField(20);
	private JTextField palabrasClave = new JTextField(20);
    private JButton continuar = new JButton("Continuar");
    private JButton volver = new JButton("Volver");

    public NewQuest() {
        UiUtils.setupFrame(this, "Tipo de pregunta", 460, 560);

        JPanel outerPanel = UiUtils.createAppPanel();
        JPanel panel = UiUtils.createCardPanel();
        panel.add(UiUtils.createTitle("Selecciona el tipo de pregunta"), UiUtils.gbc(0, 0, 2));

        ButtonGroup group = new ButtonGroup();
        group.add(testOption);
        group.add(desarrolloOption);
        testOption.setSelected(true);

        UiUtils.styleRadioButton(testOption);
        UiUtils.styleRadioButton(desarrolloOption);

        panel.add(testOption, UiUtils.gbc(0, 1, 1));
        panel.add(desarrolloOption, UiUtils.gbc(1, 1, 1));
		panel.add(new JLabel("Curso:"), UiUtils.gbc(0, 2, 1));
		panel.add(curso, UiUtils.gbc(1, 2, 1));
		panel.add(new JLabel("Grupo:"), UiUtils.gbc(0, 3, 1));
		panel.add(grupo, UiUtils.gbc(1, 3, 1));
		panel.add(new JLabel("Módulo:"), UiUtils.gbc(0, 4, 1));
		panel.add(modulo, UiUtils.gbc(1, 4, 1));
		panel.add(new JLabel("RA:"), UiUtils.gbc(0, 5, 1));
		panel.add(ra, UiUtils.gbc(1, 5, 1));
		panel.add(new JLabel("Tema:"), UiUtils.gbc(0, 6, 1));
		panel.add(tema, UiUtils.gbc(1, 6, 1));
		panel.add(new JLabel("Palabras clave (Ej: objetos,clases,herencia...):"), UiUtils.gbc(0, 7, 2));
		panel.add(palabrasClave, UiUtils.gbc(0, 8, 2));
        panel.add(continuar, UiUtils.gbc(0, 9, 2));
		panel.add(volver, UiUtils.gbc(0, 10, 2));

        UiUtils.styleButton(continuar);
		UiUtils.styleButton(volver);

        outerPanel.add(panel, java.awt.BorderLayout.CENTER);
        setContentPane(outerPanel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        continuar.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				int id = UserQuerys.getUserId();

                if (testOption.isSelected()) {
                    dispose();
                    new AddTest(new PreguntaTest(id, UserQuerys.getUserName(id), curso.getText(), grupo.getText(),
						modulo.getText(), ra.getText(), tema.getText(), List.of(palabrasClave.getText().split(","))));
                } else if (desarrolloOption.isSelected()) {
                    dispose();
                    new AddText(new PreguntaDesarrollo(id, UserQuerys.getUserName(id), curso.getText(), grupo.getText(),
						modulo.getText(), ra.getText(), tema.getText(), List.of(palabrasClave.getText().split(","))));
                } else {
                    UiUtils.showError(NewQuest.this, "Selecciona un tipo de pregunta.");
                }
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
}
