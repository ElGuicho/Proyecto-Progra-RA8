package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import CRUD.QuestionQuerys;
import CRUD.UserQuerys;
import Model.Pregunta;
import Model.PreguntaDesarrollo;
import Model.PreguntaTest;

public class ModifyQuest extends JFrame {

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
	private JButton mostrarTodas = new JButton("Mostrar Todas");
	private JButton volver = new JButton("Volver");

	public ModifyQuest() {
		UiUtils.setupFrame(this, "Modificar pregunta", 600, 700);

		JPanel outerPanel = UiUtils.createAppPanel();
		JPanel panel = UiUtils.createCardPanel();
		panel.add(UiUtils.createTitle("Buscar y modificar preguntas"), UiUtils.gbc(0, 0, 2));

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

		JPanel buttonPanel = UiUtils.createCardPanel();
		buttonPanel.add(buscar, UiUtils.gbc(0, 0, 1));
		buttonPanel.add(mostrarTodas, UiUtils.gbc(1, 0, 1));
		buttonPanel.add(volver, UiUtils.gbc(2, 0, 1));

		UiUtils.styleButton(buscar);
		UiUtils.styleButton(mostrarTodas);
		UiUtils.styleButton(volver);

		outerPanel.add(panel, java.awt.BorderLayout.NORTH);
		outerPanel.add(buttonPanel, java.awt.BorderLayout.SOUTH);
		setContentPane(outerPanel);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		buscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchQuestions();
			}
		});

		mostrarTodas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAllQuestions();
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

		new ModifyResultsWindow(results, filters, isTest);
	}

	private void showAllQuestions() {
		List<Pregunta> allQuestions = QuestionQuerys.getAllQuestions();
		if (allQuestions.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No hay preguntas en la base de datos.", "Sin resultados",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		new ModifyResultsWindow(allQuestions, new HashMap<>(), null);
	}

	private class ModifyResultsWindow extends JFrame {
		private JTable resultsTable;
		private DefaultTableModel tableModel;
		private List<Pregunta> currentResults;
		private HashMap<String, String> currentFilters;
		private Boolean isTest;

		public ModifyResultsWindow(List<Pregunta> results, HashMap<String, String> filters, Boolean isTest) {
			currentResults = results;
			this.currentFilters = filters;
			this.isTest = isTest;
			UiUtils.setupFrame(this, "Resultados - Seleccione para modificar", 900, 600);

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
			resultsTable.setFillsViewportHeight(true);
			resultsTable.getTableHeader().setReorderingAllowed(false);

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
							showModifyDialog(selectedRow);
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
			JButton cerrar = new JButton("Cerrar");
			UiUtils.styleButton(cerrar);
			actionPanel.add(cerrar, UiUtils.gbc(0, 0, 1));

			cerrar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});

			JPanel outerPanel = UiUtils.createAppPanel();
			outerPanel.add(panel, java.awt.BorderLayout.CENTER);
			outerPanel.add(actionPanel, java.awt.BorderLayout.SOUTH);
			setContentPane(outerPanel);
			setVisible(true);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}

		private void showModifyDialog(int rowIndex) {
			if (currentResults == null || rowIndex < 0 || rowIndex >= currentResults.size()) {
				return;
			}
			Pregunta p = currentResults.get(rowIndex);
			new ModifyQuestionDialog(p, this);
		}
	}

	private class ModifyQuestionDialog extends JFrame {
		private Pregunta question;
		private ModifyResultsWindow parentWindow;
		private JTextField cursoField, grupoField, moduloField, raField, temaField, enunciadoField, respuestaField;
		private JTextField opcion1Field, opcion2Field, opcion3Field, opcion4Field, correctaField, palabrasField;
		private JButton guardar, cancelar;

		public ModifyQuestionDialog(Pregunta question, ModifyResultsWindow parentWindow) {
			this.question = question;
			this.parentWindow = parentWindow;

			String title = question instanceof PreguntaTest ? "Modificar Pregunta Test"
					: "Modificar Pregunta Desarrollo";
			UiUtils.setupFrame(this, title, 1000, question instanceof PreguntaTest ? 550 : 550);

			JPanel panel = UiUtils.createCardPanel();
			int row = 0;

			// Campos no editables
			panel.add(new JLabel("ID: " + question.getId()), UiUtils.gbc(0, row++, 2));
			panel.add(new JLabel("Autor: " + question.getAutor()), UiUtils.gbc(0, row++, 2));
			panel.add(new JLabel("Fecha: " + question.getFechaCreacion()), UiUtils.gbc(0, row++, 2));

			// Campos editables
			cursoField = new JTextField(question.getCurso(), 20);
			grupoField = new JTextField(question.getGrupo(), 20);
			moduloField = new JTextField(question.getModulo(), 20);
			raField = new JTextField(question.getRa(), 20);
			temaField = new JTextField(question.getTema(), 20);
			enunciadoField = new JTextField(question.getEnunciado(), 20);

			List<String> keywords = QuestionQuerys.getKeyWords(question.getId());
			palabrasField = new JTextField(String.join(", ", keywords), 20);

			panel.add(new JLabel("Curso:"), UiUtils.gbc(0, row, 1));
			panel.add(cursoField, UiUtils.gbc(1, row, 1));

			panel.add(new JLabel("Grupo:"), UiUtils.gbc(2, row, 1));
			panel.add(grupoField, UiUtils.gbc(3, row++, 1));

			panel.add(new JLabel("Módulo:"), UiUtils.gbc(0, row, 1));
			panel.add(moduloField, UiUtils.gbc(1, row, 1));

			panel.add(new JLabel("RA:"), UiUtils.gbc(2, row, 1));
			panel.add(raField, UiUtils.gbc(3, row++, 1));

			panel.add(new JLabel("Tema:"), UiUtils.gbc(0, row, 1));
			panel.add(temaField, UiUtils.gbc(1, row, 1));

			panel.add(new JLabel("Enunciado:"), UiUtils.gbc(2, row, 1));
			panel.add(enunciadoField, UiUtils.gbc(3, row++, 1));

			if (question instanceof PreguntaTest) {
				PreguntaTest pt = (PreguntaTest) question;
				opcion1Field = new JTextField(pt.getOpciones().get(0), 20);
				opcion2Field = new JTextField(pt.getOpciones().get(1), 20);
				opcion3Field = new JTextField(pt.getOpciones().get(2), 20);
				opcion4Field = new JTextField(pt.getOpciones().get(3), 20);
				correctaField = new JTextField(String.valueOf(pt.getCorrecta()), 20);

				panel.add(new JLabel("Opción 1:"), UiUtils.gbc(0, row, 1));
				panel.add(opcion1Field, UiUtils.gbc(1, row, 1));

				panel.add(new JLabel("Opción 2:"), UiUtils.gbc(2, row, 1));
				panel.add(opcion2Field, UiUtils.gbc(3, row++, 1));

				panel.add(new JLabel("Opción 3:"), UiUtils.gbc(0, row, 1));
				panel.add(opcion3Field, UiUtils.gbc(1, row, 1));

				panel.add(new JLabel("Opción 4:"), UiUtils.gbc(2, row, 1));
				panel.add(opcion4Field, UiUtils.gbc(3, row++, 1));

				panel.add(new JLabel("Correcta (1-4):"), UiUtils.gbc(0, row, 1));
				panel.add(correctaField, UiUtils.gbc(1, row, 1));
			} else {
				PreguntaDesarrollo pd = (PreguntaDesarrollo) question;
				respuestaField = new JTextField(pd.getRespuestaModelo(), 20);

				panel.add(new JLabel("Respuesta Modelo:"), UiUtils.gbc(0, row, 1));
				panel.add(respuestaField, UiUtils.gbc(1, row, 1));
			}

			panel.add(new JLabel("Palabras Clave:"), UiUtils.gbc(2, row, 1));
			panel.add(palabrasField, UiUtils.gbc(3, row++, 1));

			JPanel buttonPanel = UiUtils.createCardPanel();
			guardar = new JButton("Guardar");
			cancelar = new JButton("Cancelar");
			UiUtils.styleButton(guardar);
			UiUtils.styleButton(cancelar);

			buttonPanel.add(guardar, UiUtils.gbc(0, 0, 1));
			buttonPanel.add(cancelar, UiUtils.gbc(1, 0, 1));

			JPanel outerPanel = UiUtils.createAppPanel();
			outerPanel.add(panel, java.awt.BorderLayout.CENTER);
			outerPanel.add(buttonPanel, java.awt.BorderLayout.SOUTH);
			setContentPane(outerPanel);
			setVisible(true);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			guardar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					saveChanges();
				}
			});

			cancelar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}

		private void saveChanges() {
			try {
				String curso = cursoField.getText().trim();
				String grupo = grupoField.getText().trim();
				String modulo = moduloField.getText().trim();
				String ra = raField.getText().trim();
				String tema = temaField.getText().trim();
				String enunciado = enunciadoField.getText().trim();

				if (curso.isEmpty() || grupo.isEmpty() || modulo.isEmpty() || ra.isEmpty() ||
						tema.isEmpty() || enunciado.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				List<String> palabrasClave = new ArrayList<>();
				if (!palabrasField.getText().trim().isEmpty()) {
					String[] keywords = palabrasField.getText().trim().split(",");
					for (String kw : keywords) {
						if (!kw.trim().isEmpty()) {
							palabrasClave.add(kw.trim());
						}
					}
				}

				boolean success;
				if (question instanceof PreguntaTest) {
					String opcion1 = opcion1Field.getText().trim();
					String opcion2 = opcion2Field.getText().trim();
					String opcion3 = opcion3Field.getText().trim();
					String opcion4 = opcion4Field.getText().trim();
					int correcta = Integer.parseInt(correctaField.getText().trim());

					if (opcion1.isEmpty() || opcion2.isEmpty() || opcion3.isEmpty() || opcion4.isEmpty() ||
							correcta < 1 || correcta > 4) {
						JOptionPane.showMessageDialog(this,
								"Todas las opciones son obligatorias y la correcta debe ser 1-4.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					List<String> opciones = new ArrayList<>();
					opciones.add(opcion1);
					opciones.add(opcion2);
					opciones.add(opcion3);
					opciones.add(opcion4);

					success = QuestionQuerys.updateTestQuestion(question.getId(), curso, grupo, modulo, ra, tema,
							enunciado, opciones, correcta, palabrasClave);
				} else {
					String respuesta = respuestaField.getText().trim();
					if (respuesta.isEmpty()) {
						JOptionPane.showMessageDialog(this, "La respuesta modelo es obligatoria.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					success = QuestionQuerys.updateDevelopmentQuestion(question.getId(), curso, grupo, modulo, ra, tema,
							enunciado, respuesta, palabrasClave);
				}

				if (success) {
					JOptionPane.showMessageDialog(this, "Pregunta modificada correctamente.", "Éxito",
							JOptionPane.INFORMATION_MESSAGE);
					UserQuerys.logOperation(UserQuerys.getUserId(), "Modificar pregunta",
							UserQuerys.getUserName(UserQuerys.getUserId()) + " modificó la pregunta con ID " + question.getId());
					parentWindow.dispose();
					dispose();
					// Refresh the main window
				} else {
					JOptionPane.showMessageDialog(this, "Error al modificar la pregunta.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "La opción correcta debe ser un número.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
