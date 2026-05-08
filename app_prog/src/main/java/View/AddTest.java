package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import CRUD.QuestionQuerys;
import CRUD.UserQuerys;
import Model.PreguntaTest;

// Window for adding new test questions
public class AddTest extends JFrame {

	private JTextField enunciadoField = new JTextField(24);
	private JTextField option1Field = new JTextField(20);
	private JTextField option2Field = new JTextField(20);
	private JTextField option3Field = new JTextField(20);
	private JTextField option4Field = new JTextField(20);
	private JSpinner correctaSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 4, 1));
	private JButton guardar = new JButton("Guardar");
	private JButton volver = new JButton("Volver");

	public AddTest(PreguntaTest pregunta) {
		UiUtils.setupFrame(this, "Nueva pregunta tipo test", 500, 460);

		JPanel outerPanel = UiUtils.createAppPanel();
		JPanel panel = UiUtils.createCardPanel();
		panel.add(UiUtils.createTitle("Nueva pregunta tipo TEST"), UiUtils.gbc(0, 0, 2));

		panel.add(new JLabel("Enunciado:"), UiUtils.gbc(0, 1, 1));
		panel.add(enunciadoField, UiUtils.gbc(0, 2, 2));

		panel.add(new JLabel("Opcion 1:"), UiUtils.gbc(0, 3, 1));
		panel.add(option1Field, UiUtils.gbc(1, 3, 1));

		panel.add(new JLabel("Opcion 2:"), UiUtils.gbc(0, 4, 1));
		panel.add(option2Field, UiUtils.gbc(1, 4, 1));

		panel.add(new JLabel("Opcion 3:"), UiUtils.gbc(0, 5, 1));
		panel.add(option3Field, UiUtils.gbc(1, 5, 1));

		panel.add(new JLabel("Opcion 4:"), UiUtils.gbc(0, 6, 1));
		panel.add(option4Field, UiUtils.gbc(1, 6, 1));

		panel.add(new JLabel("Respuesta correcta (1-4):"), UiUtils.gbc(0, 7, 1));
		panel.add(correctaSpinner, UiUtils.gbc(1, 7, 1));

		panel.add(guardar, UiUtils.gbc(0, 8, 1));
		panel.add(volver, UiUtils.gbc(1, 8, 1));
		UiUtils.styleButton(guardar);
		UiUtils.styleButton(volver);

		outerPanel.add(panel, java.awt.BorderLayout.CENTER);
		setContentPane(outerPanel);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		guardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveQuestion(pregunta);
			}
		});

		volver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new NewQuest();
			}
		});
	}

	private void saveQuestion(PreguntaTest pregunta) {
		String enunciado = enunciadoField.getText().trim();
		List<String> opciones = Arrays.asList(option1Field.getText().trim(), option2Field.getText().trim(),
				option3Field.getText().trim(), option4Field.getText().trim());
		int correcta = (Integer) correctaSpinner.getValue();

		if (enunciado.isEmpty()) {
			UiUtils.showError(this, "Complete el enunciado de la pregunta.");
			enunciadoField.requestFocusInWindow();
			return;
		}

		for (int i = 0; i < opciones.size(); i++) {
			if (opciones.get(i).isEmpty()) {
				UiUtils.showError(this, "Complete la opcion " + (i + 1) + " de respuesta.");
				return;
			}
		}

		pregunta.setEnunciado(enunciado);
		pregunta.setFechaCreacion(LocalDate.now());
		pregunta.setOpciones(opciones);
		pregunta.setCorrecta(correcta);
		int preguntaId = QuestionQuerys.addTestQuest(pregunta);
		pregunta.setId(preguntaId);
		int id = UserQuerys.getUserId();
		UserQuerys.logOperation(id, "Nueva pregunta TEST", UserQuerys.getUserName(id) + "creo una nueva pregunta TEST");
		if (!pregunta.getPalabrasClave().isEmpty()) {
			UserQuerys.logOperation(id, "Agregar palabras clave",
					UserQuerys.getUserName(id) + "agrego palabras clave a la pregunta TEST con ID " + pregunta.getId());
		}
		UiUtils.showInfo(this, "Pregunta TEST creada correctamente.");
		dispose();
		new ChoiceWin();
	}
}
