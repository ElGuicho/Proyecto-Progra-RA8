package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import CRUD.QuestionQuerys;
import Model.Pregunta;
import Model.PreguntaDesarrollo;
import Model.PreguntaTest;

public class SearchQuest extends JFrame {

	private JTextField idField = new JTextField(14);
	private JTextField authorField = new JTextField(14);
	private JTextField cursoField = new JTextField(14);
	private JTextField grupoField = new JTextField(14);
	private JTextField moduloField = new JTextField(14);
	private JTextField raField = new JTextField(14);
	private JTextField temaField = new JTextField(14);
	private JTextField fechaField = new JTextField(14);
	private JTextField palabrasField = new JTextField(14);
	private JRadioButton testOption = new JRadioButton("Test");
	private JRadioButton desarrolloOption = new JRadioButton("Desarrollo");
	private JButton buscar = new JButton("Buscar");
	private JButton volver = new JButton("Volver");

	public SearchQuest() {
		UiUtils.setupFrame(this, "Buscar pregunta", 600, 620);

		JPanel outerPanel = UiUtils.createAppPanel();
		JPanel panel = UiUtils.createCardPanel();
		panel.add(UiUtils.createTitle("Buscar preguntas"), UiUtils.gbc(0, 0, 2));

		panel.add(new JLabel("ID:"), UiUtils.gbc(0, 1, 1));
		panel.add(idField, UiUtils.gbc(1, 1, 1));

		panel.add(new JLabel("Autor:"), UiUtils.gbc(0, 2, 1));
		panel.add(authorField, UiUtils.gbc(1, 2, 1));

		panel.add(new JLabel("Curso:"), UiUtils.gbc(0, 3, 1));
		panel.add(cursoField, UiUtils.gbc(1, 3, 1));

		panel.add(new JLabel("Grupo:"), UiUtils.gbc(0, 4, 1));
		panel.add(grupoField, UiUtils.gbc(1, 4, 1));

		panel.add(new JLabel("Módulo:"), UiUtils.gbc(0, 5, 1));
		panel.add(moduloField, UiUtils.gbc(1, 5, 1));

		panel.add(new JLabel("RA:"), UiUtils.gbc(0, 6, 1));
		panel.add(raField, UiUtils.gbc(1, 6, 1));

		panel.add(new JLabel("Tema:"), UiUtils.gbc(0, 7, 1));
		panel.add(temaField, UiUtils.gbc(1, 7, 1));

		panel.add(new JLabel("Palabras Clave:"), UiUtils.gbc(0, 8, 1));
		panel.add(palabrasField, UiUtils.gbc(1, 8, 1));

		panel.add(new JLabel("Fecha Creación:"), UiUtils.gbc(0, 9, 1));
		panel.add(fechaField, UiUtils.gbc(1, 9, 1));

		ButtonGroup typeGroup = new ButtonGroup();
		typeGroup.add(testOption);
		typeGroup.add(desarrolloOption);
		testOption.setSelected(true);

		UiUtils.styleRadioButton(testOption);
		UiUtils.styleRadioButton(desarrolloOption);

		panel.add(testOption, UiUtils.gbc(0, 10, 1));
		panel.add(desarrolloOption, UiUtils.gbc(1, 10, 1));

		panel.add(buscar, UiUtils.gbc(0, 11, 1));
		panel.add(volver, UiUtils.gbc(1, 11, 1));

		UiUtils.styleButton(buscar);
		UiUtils.styleButton(volver);

		outerPanel.add(panel, java.awt.BorderLayout.CENTER);
		setContentPane(outerPanel);
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
		if (!cursoField.getText().trim().isEmpty()) {
			filters.put("curso", cursoField.getText().trim());
		}
		if (!grupoField.getText().trim().isEmpty()) {
			filters.put("grupo", grupoField.getText().trim());
		}
		if (!moduloField.getText().trim().isEmpty()) {
			filters.put("modulo", moduloField.getText().trim());
		}
		if (!raField.getText().trim().isEmpty()) {
			filters.put("ra", raField.getText().trim());
		}
		if (!temaField.getText().trim().isEmpty()) {
			filters.put("tema", temaField.getText().trim());
		}
		if (!palabrasField.getText().trim().isEmpty()) {
			filters.put("palabras_clave", palabrasField.getText());
		}
		if (!fechaField.getText().trim().isEmpty()) {
			filters.put("fecha_creacion", fechaField.getText().trim());
		}

		boolean isTest = testOption.isSelected();

		List<Pregunta> results = QuestionQuerys.searchQuestions(filters, isTest);

		if (results.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No se encontraron preguntas con esos filtros.", "Sin resultados",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		new ResultsWindow(results, filters, isTest);
	}

	private class ResultsWindow extends JFrame {
		private JTable resultsTable;
		private DefaultTableModel tableModel;
		private List<Pregunta> currentResults;
		private HashMap<String, String> currentFilters;
		private boolean isTest;

		public ResultsWindow(List<Pregunta> results, HashMap<String, String> filters, boolean isTest) {
			currentResults = results;
			this.currentFilters = filters;
			this.isTest = isTest;
			UiUtils.setupFrame(this, "Resultados de Búsqueda", 800, 600);

			JPanel panel = UiUtils.createCardPanel();
			String[] columnNames = { "ID", "Autor", "Curso", "Grupo", "Módulo", "RA", "Tema", "Fecha Creación",
					"Tipo" };
			tableModel = new DefaultTableModel(columnNames, 0) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			resultsTable = new JTable(tableModel);
			resultsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			resultsTable.setFillsViewportHeight(true); // Asegura que la tabla llene el viewport
			resultsTable.getTableHeader().setReorderingAllowed(false); // Evita reordenar columnas

			// Configurar anchos de columna
			resultsTable.getColumnModel().getColumn(0).setPreferredWidth(50); // ID
			resultsTable.getColumnModel().getColumn(1).setPreferredWidth(100); // Autor
			resultsTable.getColumnModel().getColumn(2).setPreferredWidth(80); // Curso
			resultsTable.getColumnModel().getColumn(3).setPreferredWidth(80); // Grupo
			resultsTable.getColumnModel().getColumn(4).setPreferredWidth(80); // Módulo
			resultsTable.getColumnModel().getColumn(5).setPreferredWidth(50); // RA
			resultsTable.getColumnModel().getColumn(6).setPreferredWidth(100); // Tema
			resultsTable.getColumnModel().getColumn(7).setPreferredWidth(100); // Fecha
			resultsTable.getColumnModel().getColumn(8).setPreferredWidth(80); // Tipo
			resultsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					if (!e.getValueIsAdjusting()) {
						int selectedRow = resultsTable.getSelectedRow();
						if (selectedRow >= 0) {
							showQuestionDetails(selectedRow);
						}
					}
				}
			});

			// Populate table
			for (Pregunta p : results) {
				Object[] row = {
						p.getId(),
						p.getAutor(),
						p.getCurso(),
						p.getGrupo(),
						p.getModulo(),
						p.getRa(),
						p.getTema(),
						p.getFechaCreacion().toString(),
						p instanceof PreguntaTest ? "Test" : "Desarrollo"
				};
				tableModel.addRow(row);
			}

			JScrollPane scrollPane = new JScrollPane(resultsTable);
			panel.add(scrollPane, UiUtils.gbcBoth(0, 0, 1));

			JPanel actionPanel = UiUtils.createCardPanel();
			JButton generarExamen = new JButton("Generar examen");
			UiUtils.styleButton(generarExamen);
			actionPanel.add(generarExamen, UiUtils.gbc(0, 0, 1));

			generarExamen.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					generateExamFromList();
				}
			});

			JPanel outerPanel = UiUtils.createAppPanel();
			outerPanel.add(panel, java.awt.BorderLayout.CENTER);
			outerPanel.add(actionPanel, java.awt.BorderLayout.SOUTH);
			setContentPane(outerPanel);
			setVisible(true);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}

		private void showQuestionDetails(int rowIndex) {
			if (currentResults == null || rowIndex < 0 || rowIndex >= currentResults.size()) {
				return;
			}
			Pregunta p = currentResults.get(rowIndex);
			List<String> keywords = QuestionQuerys.getKeyWords(p.getId());
			StringBuilder details = new StringBuilder();
			details.append("ID: ").append(p.getId()).append("\n");
			details.append("Autor: ").append(p.getAutor()).append("\n");
			details.append("Curso: ").append(p.getCurso()).append("\n");
			details.append("Grupo: ").append(p.getGrupo()).append("\n");
			details.append("Módulo: ").append(p.getModulo()).append("\n");
			details.append("RA: ").append(p.getRa()).append("\n");
			details.append("Tema: ").append(p.getTema()).append("\n");
			details.append("Enunciado: ").append(p.getEnunciado()).append("\n");
			details.append("Fecha Creación: ").append(p.getFechaCreacion()).append("\n");
			details.append("Palabras Clave: ").append(keywords.toString()).append("\n");
			if (p instanceof PreguntaTest) {
				PreguntaTest pt = (PreguntaTest) p;
				details.append("Tipo: Test\n");
				details.append("Opciones: ").append(pt.getOpciones()).append("\n");
				details.append("Correcta: ").append(pt.getCorrecta()).append("\n");
			} else {
				PreguntaDesarrollo pd = (PreguntaDesarrollo) p;
				details.append("Tipo: Desarrollo\n");
				details.append("Respuesta Modelo: ").append(pd.getRespuestaModelo()).append("\n");
			}

			javax.swing.JOptionPane.showMessageDialog(this, details.toString(), "Detalles de la Pregunta",
					javax.swing.JOptionPane.INFORMATION_MESSAGE);
		}

		private void generateExamFromList() {
			String input = JOptionPane.showInputDialog(this, "Número de preguntas para el examen:",
					"Generar examen", JOptionPane.PLAIN_MESSAGE);
			if (input == null || input.trim().isEmpty()) {
				return;
			}

			int count;
			try {
				count = Integer.parseInt(input.trim());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Ingrese un número válido.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (count < 1) {
				JOptionPane.showMessageDialog(this, "El número debe ser mayor que 0.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			int available = currentResults.size();
			if (available == 0) {
				JOptionPane.showMessageDialog(this, "No hay preguntas en el listado.", "Sin resultados",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			if (count > available) {
				int option = JOptionPane.showConfirmDialog(this,
						"Solo hay " + available + " preguntas en el listado. ¿Generar con esa cantidad?",
						"Cantidad ajustada", JOptionPane.YES_NO_OPTION);
				if (option != JOptionPane.YES_OPTION) {
					return;
				}
				count = available;
			}

			List<Pregunta> examQuestions = new ArrayList<>(currentResults);
			Collections.shuffle(examQuestions);
			examQuestions = examQuestions.subList(0, count);

			StringBuilder summary = new StringBuilder();
			for (int i = 0; i < examQuestions.size(); i++) {
				Pregunta p = examQuestions.get(i);
				summary.append(i + 1).append(". ");
				summary.append("Curso: ").append(p.getCurso()).append(" | RA: ").append(p.getRa())
						.append(" | Tema: ").append(p.getTema()).append("\n");
				summary.append(p.getEnunciado()).append("\n");
				if (p instanceof PreguntaTest) {
					PreguntaTest pt = (PreguntaTest) p;
					summary.append("  A: ").append(pt.getOpciones().get(0)).append("\n");
					summary.append("  B: ").append(pt.getOpciones().get(1)).append("\n");
					summary.append("  C: ").append(pt.getOpciones().get(2)).append("\n");
					summary.append("  D: ").append(pt.getOpciones().get(3)).append("\n");
					summary.append("  Correcta: ").append(pt.getCorrecta()).append("\n");
				} else {
					PreguntaDesarrollo pd = (PreguntaDesarrollo) p;
					summary.append("  Respuesta modelo: ").append(pd.getRespuestaModelo()).append("\n");
				}
				summary.append("---------------------------------------------------\n");
			}

			JTextArea preview = new JTextArea(summary.toString(), 24, 60);
			preview.setEditable(false);
			JScrollPane scrollPane = new JScrollPane(preview);
			JOptionPane.showMessageDialog(this, scrollPane, "Examen generado desde el banco",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
