package View;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import CRUD.QuestionQuerys;
import CRUD.UserQuerys;
import Model.PreguntaDesarrollo;

public class AddText extends JFrame {

	private JTextField enunciadoField = new JTextField(24);
	private JTextArea respuestaArea = new JTextArea(6, 24);
	private JButton guardar = new JButton("Guardar");
	private JButton volver = new JButton("Volver");

	public AddText(PreguntaDesarrollo pregunta) {
		UiUtils.setupFrame(this, "Nueva pregunta de desarrollo", 520, 420);

		JPanel outerPanel = UiUtils.createAppPanel();
		JPanel panel = UiUtils.createCardPanel();
		panel.add(UiUtils.createTitle("Nueva pregunta de desarrollo"), UiUtils.gbc(0, 0, 2));

		panel.add(new JLabel("Enunciado:"), UiUtils.gbc(0, 1, 1));
		panel.add(enunciadoField, UiUtils.gbc(0, 2, 2));

		panel.add(new JLabel("Respuesta modelo:"), UiUtils.gbc(0, 3, 1));
		panel.add(new JScrollPane(respuestaArea), UiUtils.gbc(0, 4, 2));

		panel.add(guardar, UiUtils.gbc(0, 5, 1));
		panel.add(volver, UiUtils.gbc(1, 5, 1));

		UiUtils.styleButton(guardar);
		UiUtils.styleButton(volver);
		UiUtils.styleTextArea(respuestaArea);

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

	private void saveQuestion(PreguntaDesarrollo pregunta) {
		String enunciado = enunciadoField.getText().trim();
		String respuesta = respuestaArea.getText().trim();

		if (enunciado.isEmpty()) {
			UiUtils.showError(this, "Complete el enunciado de la pregunta.");
			enunciadoField.requestFocusInWindow();
			return;
		}

		if (respuesta.isEmpty()) {
			UiUtils.showError(this, "Complete la respuesta modelo.");
			respuestaArea.requestFocusInWindow();
			return;
		}

		pregunta.setFechaCreacion(LocalDate.now());
		pregunta.setEnunciado(enunciado);
		pregunta.setRespuestaModelo(respuesta);
		int preguntaId = QuestionQuerys.addTextQuest(pregunta);
		pregunta.setId(preguntaId);
		int id = UserQuerys.getUserId();
		UserQuerys.logOperation(id, "Nueva pregunta DESARROLLO",
				UserQuerys.getUserName(id) + "creo una nueva pregunta DESARROLLO");
		if (!pregunta.getPalabrasClave().isEmpty()) {
			UserQuerys.logOperation(id, "Agregar palabras clave", UserQuerys.getUserName(id)
					+ "agrego palabras clave a la pregunta DESARROLLO con ID " + pregunta.getId());
		}
		UiUtils.showInfo(this, "Pregunta de desarrollo creada correctamente.");
		dispose();
		new ChoiceWin();
	}
}
