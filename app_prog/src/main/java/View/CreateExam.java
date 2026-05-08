package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import CRUD.QuestionQuerys;
import Model.Pregunta;
import Model.PreguntaTest;

// Window for creating custom exams by selecting questions from the database
public class CreateExam extends JFrame {

	private JSpinner numberField = new JSpinner(new SpinnerNumberModel(3, 1, 20, 1));
	private JRadioButton testOption = new JRadioButton("Preguntas tipo TEST");
	private JRadioButton desarrolloOption = new JRadioButton("Preguntas tipo DESARROLLO");
	private JTextField addIdField = new JTextField(10);
	private JButton addById = new JButton("Añadir por ID");
	private JButton addRandom = new JButton("Añadir aleatorias");
	private JButton removeSelected = new JButton("Eliminar seleccionada");
	private JButton clearAll = new JButton("Limpiar todo");
	private JButton generar = new JButton("Generar examen");
	private JButton banco = new JButton("Banco de preguntas");
	private JButton volver = new JButton("Volver");

	// Table for selected questions
	private JTable selectedTable;
	private DefaultTableModel tableModel;
	private List<Pregunta> selectedQuestions = new ArrayList<>();

	public CreateExam() {
		UiUtils.setupFrame(this, "Crear examen", 900, 700);

		JPanel outerPanel = UiUtils.createAppPanel();
		JPanel panel = UiUtils.createCardPanel();
		panel.add(UiUtils.createTitle("Crear examen personalizado"), UiUtils.gbc(0, 0, 4));

		// Configuration section
		panel.add(new JLabel("Cantidad total deseada:"), UiUtils.gbc(0, 1, 1));
		panel.add(numberField, UiUtils.gbc(1, 1, 1));

		ButtonGroup typeGroup = new ButtonGroup();
		typeGroup.add(testOption);
		typeGroup.add(desarrolloOption);
		testOption.setSelected(true);

		UiUtils.styleRadioButton(testOption);
		UiUtils.styleRadioButton(desarrolloOption);

		panel.add(testOption, UiUtils.gbc(2, 1, 1));
		panel.add(desarrolloOption, UiUtils.gbc(3, 1, 1));

		// Add questions section
		panel.add(new JLabel("Añadir pregunta por ID:"), UiUtils.gbc(0, 2, 1));
		panel.add(addIdField, UiUtils.gbc(1, 2, 1));
		panel.add(addById, UiUtils.gbc(2, 2, 1));
		panel.add(addRandom, UiUtils.gbc(3, 2, 1));

		// Table management buttons
		panel.add(removeSelected, UiUtils.gbc(0, 3, 1));
		panel.add(clearAll, UiUtils.gbc(1, 3, 1));
		panel.add(generar, UiUtils.gbc(2, 3, 1));

		// Selected questions table
		String[] columnNames = { "ID", "Tipo", "Curso", "Tema", "Enunciado" };
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		selectedTable = new JTable(tableModel);
		selectedTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectedTable.setFillsViewportHeight(true);
		selectedTable.getTableHeader().setReorderingAllowed(false);

		// Configure column widths
		selectedTable.getColumnModel().getColumn(0).setPreferredWidth(50); // ID
		selectedTable.getColumnModel().getColumn(1).setPreferredWidth(80); // Tipo
		selectedTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Curso
		selectedTable.getColumnModel().getColumn(3).setPreferredWidth(120); // Tema
		selectedTable.getColumnModel().getColumn(4).setPreferredWidth(300); // Enunciado

		JScrollPane tableScrollPane = new JScrollPane(selectedTable);
		panel.add(tableScrollPane, UiUtils.gbcBoth(0, 4, 4));

		// Bottom buttons
		JPanel bottomPanel = UiUtils.createCardPanel();
		bottomPanel.add(banco, UiUtils.gbc(0, 0, 1));
		bottomPanel.add(volver, UiUtils.gbc(1, 0, 1));

		UiUtils.styleButton(addById);
		UiUtils.styleButton(addRandom);
		UiUtils.styleButton(removeSelected);
		UiUtils.styleButton(clearAll);
		UiUtils.styleButton(generar);
		UiUtils.styleButton(banco);
		UiUtils.styleButton(volver);

		outerPanel.add(panel, java.awt.BorderLayout.CENTER);
		outerPanel.add(bottomPanel, java.awt.BorderLayout.SOUTH);
		setContentPane(outerPanel);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Event listeners
		addById.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addQuestionById();
			}
		});

		addRandom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addRandomQuestions();
			}
		});

		removeSelected.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeSelectedQuestion();
			}
		});

		clearAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearAllQuestions();
			}
		});

		generar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generateExam();
			}
		});

		banco.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int maxQuestions = (Integer) numberField.getValue();
				boolean isTest = testOption.isSelected();
				Set<Integer> alreadySelectedIds = new HashSet<>();
				for (Pregunta p : selectedQuestions) {
					alreadySelectedIds.add(p.getId());
				}
				dispose();
				new SelectQuestionsForExam(CreateExam.this, maxQuestions, isTest, alreadySelectedIds);
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

	private void addQuestionById() {
		String idText = addIdField.getText().trim();
		if (idText.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Ingrese un ID de pregunta.", "Error", JOptionPane.ERROR_MESSAGE);
			addIdField.requestFocusInWindow();
			return;
		}

		try {
			int id = Integer.parseInt(idText);

			// Check if question already exists in selected list
			for (Pregunta p : selectedQuestions) {
				if (p.getId() == id) {
					JOptionPane.showMessageDialog(this, "Esta pregunta ya está en la lista.", "Duplicada",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
			}

			// Get question from database
			List<Pregunta> allQuestions = QuestionQuerys.getAllQuestions();
			Pregunta foundQuestion = null;
			for (Pregunta p : allQuestions) {
				if (p.getId() == id) {
					foundQuestion = p;
					break;
				}
			}

			if (foundQuestion == null) {
				JOptionPane.showMessageDialog(this, "No se encontró una pregunta con ese ID.", "No encontrada",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Add to selected questions
			selectedQuestions.add(foundQuestion);
			updateTable();
			addIdField.setText("");
			addIdField.requestFocusInWindow();

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El ID debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
			addIdField.requestFocusInWindow();
		}
	}

	private void addRandomQuestions() {
		int targetCount = (Integer) numberField.getValue();
		int currentCount = selectedQuestions.size();

		if (currentCount >= targetCount) {
			JOptionPane.showMessageDialog(this, "Ya tienes suficientes preguntas seleccionadas.", "Completo",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		int needed = targetCount - currentCount;

		// Get all available questions of the selected type
		List<Pregunta> allQuestions = QuestionQuerys.getAllQuestions();

		// Remove questions that are already selected
		Set<Integer> selectedIds = new HashSet<>();
		for (Pregunta p : selectedQuestions) {
			selectedIds.add(p.getId());
		}

		List<Pregunta> availableQuestions = new ArrayList<>();
		for (Pregunta p : allQuestions) {
			if (!selectedIds.contains(p.getId())) {
				availableQuestions.add(p);
			}
		}

		if (availableQuestions.size() < needed) {
			needed = availableQuestions.size();
			if (needed == 0) {
				JOptionPane.showMessageDialog(this, "No hay más preguntas disponibles de este tipo.", "Sin preguntas",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
		}

		// Shuffle and select random questions
		Collections.shuffle(availableQuestions);
		List<Pregunta> randomQuestions = availableQuestions.subList(0, needed);

		// Add to selected questions
		selectedQuestions.addAll(randomQuestions);
		updateTable();

		JOptionPane.showMessageDialog(this, "Se añadieron " + needed + " preguntas aleatorias.", "Éxito",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void removeSelectedQuestion() {
		int selectedRow = selectedTable.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Seleccione una pregunta para eliminar.", "Ninguna selección",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		selectedQuestions.remove(selectedRow);
		updateTable();
	}

	private void clearAllQuestions() {
		if (selectedQuestions.isEmpty()) {
			return;
		}

		int option = JOptionPane.showConfirmDialog(this,
				"¿Está seguro de que desea eliminar todas las preguntas seleccionadas?",
				"Confirmar", JOptionPane.YES_NO_OPTION);

		if (option == JOptionPane.YES_OPTION) {
			selectedQuestions.clear();
			updateTable();
		}
	}

	private void updateTable() {
		tableModel.setRowCount(0);
		for (Pregunta p : selectedQuestions) {
			Object[] row = {
					p.getId(),
					p instanceof PreguntaTest ? "Test" : "Desarrollo",
					p.getCurso(),
					p.getTema(),
					p.getEnunciado().length() > 50 ? p.getEnunciado().substring(0, 50) + "..." : p.getEnunciado()
			};
			tableModel.addRow(row);
		}
	}

	private void generateExam() {
		if (selectedQuestions.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No hay preguntas seleccionadas para el examen.", "Sin preguntas",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Shuffle the questions for the exam
		List<Pregunta> examQuestions = new ArrayList<>(selectedQuestions);
		Collections.shuffle(examQuestions);

		StringBuilder examText = new StringBuilder();
		examText.append("EXAMEN\n");
		examText.append("======\n\n");
		examText.append("Instrucciones: Responda todas las preguntas.\n\n");

		for (int i = 0; i < examQuestions.size(); i++) {
			Pregunta p = examQuestions.get(i);
			examText.append((i + 1)).append(". ");
			examText.append(p.getEnunciado()).append("\n");

			if (p instanceof PreguntaTest) {
				PreguntaTest pt = (PreguntaTest) p;
				List<String> opciones = pt.getOpciones();
				char optionLetter = 'A';
				for (String opcion : opciones) {
					examText.append("   ").append(optionLetter++).append(") ").append(opcion).append("\n");
				}
			} else {
				examText.append("   (Desarrollo)\n");
			}
			examText.append("\n");
		}

		// Show exam in dialog
		JTextArea examArea = new JTextArea(examText.toString(), 25, 60);
		examArea.setEditable(false);
		examArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
		JScrollPane scrollPane = new JScrollPane(examArea);

		JOptionPane.showMessageDialog(this, scrollPane, "Examen generado - Listo para alumnos",
				JOptionPane.INFORMATION_MESSAGE);
	}

	// Inner class for selecting questions from search results
	private static class SelectQuestionsForExam extends JFrame {
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
		private JButton seleccionar = new JButton("Seleccionar preguntas");
		private JButton volver = new JButton("Volver");

		private CreateExam parentExam;
		private int maxQuestions;
		private boolean isTestType;
		private Set<Integer> alreadySelectedIds;

		public SelectQuestionsForExam(CreateExam parent, int maxQuestions, boolean isTestType,
				Set<Integer> alreadySelectedIds) {
			this.parentExam = parent;
			this.maxQuestions = maxQuestions;
			this.isTestType = isTestType;
			this.alreadySelectedIds = alreadySelectedIds;

			UiUtils.setupFrame(this, "Seleccionar preguntas para examen", 600, 650);

			JPanel outerPanel = UiUtils.createAppPanel();
			JPanel panel = UiUtils.createCardPanel();
			panel.add(UiUtils.createTitle("Buscar preguntas para añadir al examen"), UiUtils.gbc(0, 0, 2));

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
			testOption.setSelected(isTestType);
			desarrolloOption.setSelected(!isTestType);

			UiUtils.styleRadioButton(testOption);
			UiUtils.styleRadioButton(desarrolloOption);

			panel.add(testOption, UiUtils.gbc(0, 10, 1));
			panel.add(desarrolloOption, UiUtils.gbc(1, 10, 1));

			JPanel buttonPanel = UiUtils.createCardPanel();
			buttonPanel.add(buscar, UiUtils.gbc(0, 0, 1));
			buttonPanel.add(mostrarTodas, UiUtils.gbc(1, 0, 1));
			buttonPanel.add(seleccionar, UiUtils.gbc(2, 0, 1));
			buttonPanel.add(volver, UiUtils.gbc(3, 0, 1));

			UiUtils.styleButton(buscar);
			UiUtils.styleButton(mostrarTodas);
			UiUtils.styleButton(seleccionar);
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

			seleccionar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					selectQuestions();
				}
			});

			volver.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
					parentExam.setVisible(true);
				}
			});
		}

		private void searchQuestions() {
			java.util.HashMap<String, String> filters = new java.util.HashMap<>();
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

			new SelectResultsWindow(results, filters, isTest, this);
		}

		private void showAllQuestions() {
			List<Pregunta> allQuestions = QuestionQuerys.getAllQuestions();
			if (allQuestions.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No hay preguntas en la base de datos.", "Sin resultados",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			new SelectResultsWindow(allQuestions, new java.util.HashMap<>(), testOption.isSelected(), this);
		}

		private void selectQuestions() {
			// This will be called from SelectResultsWindow
		}

		public void addSelectedQuestions(List<Pregunta> questionsToAdd) {
			int currentCount = parentExam.selectedQuestions.size();
			int availableSlots = maxQuestions - currentCount;
			int canAdd = Math.min(questionsToAdd.size(), availableSlots);

			if (canAdd == 0) {
				JOptionPane.showMessageDialog(this, "No se pueden añadir más preguntas. Ya tienes el máximo permitido.",
						"Límite alcanzado",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			if (canAdd < questionsToAdd.size()) {
				JOptionPane.showMessageDialog(this,
						"Solo se pudieron añadir " + canAdd + " preguntas de las " + questionsToAdd.size()
								+ " seleccionadas (límite alcanzado).",
						"Límite alcanzado", JOptionPane.WARNING_MESSAGE);
			}

			// Add questions to parent exam
			for (int i = 0; i < canAdd; i++) {
				parentExam.selectedQuestions.add(questionsToAdd.get(i));
			}

			parentExam.updateTable();
			dispose();
			parentExam.setVisible(true);

			JOptionPane.showMessageDialog(parentExam, "Se añadieron " + canAdd + " preguntas al examen.", "Éxito",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// Inner class for selecting from search results
	private static class SelectResultsWindow extends JFrame {
		private JTable resultsTable;
		private DefaultTableModel tableModel;
		private List<Pregunta> currentResults;
		private java.util.HashMap<String, String> currentFilters;
		private Boolean isTest;
		private SelectQuestionsForExam parentSelector;

		public SelectResultsWindow(List<Pregunta> results, java.util.HashMap<String, String> filters, boolean isTest,
				SelectQuestionsForExam parent) {
			currentResults = results;
			this.currentFilters = filters;
			this.isTest = isTest;
			this.parentSelector = parent;

			UiUtils.setupFrame(this, "Seleccionar preguntas - " + results.size() + " encontradas", 900, 600);

			JPanel panel = UiUtils.createCardPanel();
			String[] columnNames = { "ID", "Autor", "Curso", "Grupo", "Módulo", "RA", "Tema", "Fecha Creación",
					"Tipo" };
			tableModel = new DefaultTableModel(columnNames, 0) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return column == 9; // Only allow editing the checkbox column
				}
			};

			// Add checkbox column for selection
			tableModel.addColumn("Seleccionar");

			resultsTable = new JTable(tableModel);
			resultsTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			resultsTable.setFillsViewportHeight(true);
			resultsTable.getTableHeader().setReorderingAllowed(false);

			// Set checkbox renderer and editor for the last column
			resultsTable.getColumnModel().getColumn(9).setCellRenderer(resultsTable.getDefaultRenderer(Boolean.class));
			resultsTable.getColumnModel().getColumn(9).setCellEditor(resultsTable.getDefaultEditor(Boolean.class));

			// Configure column widths
			resultsTable.getColumnModel().getColumn(0).setPreferredWidth(50); // ID
			resultsTable.getColumnModel().getColumn(1).setPreferredWidth(100); // Autor
			resultsTable.getColumnModel().getColumn(2).setPreferredWidth(80); // Curso
			resultsTable.getColumnModel().getColumn(3).setPreferredWidth(80); // Grupo
			resultsTable.getColumnModel().getColumn(4).setPreferredWidth(80); // Módulo
			resultsTable.getColumnModel().getColumn(5).setPreferredWidth(50); // RA
			resultsTable.getColumnModel().getColumn(6).setPreferredWidth(100); // Tema
			resultsTable.getColumnModel().getColumn(7).setPreferredWidth(100); // Fecha
			resultsTable.getColumnModel().getColumn(8).setPreferredWidth(80); // Tipo
			resultsTable.getColumnModel().getColumn(9).setPreferredWidth(100); // Seleccionar

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
						p instanceof PreguntaTest ? "Test" : "Desarrollo",
						Boolean.FALSE // Checkbox default unchecked
				};
				tableModel.addRow(row);
			}

			JScrollPane scrollPane = new JScrollPane(resultsTable);
			panel.add(scrollPane, UiUtils.gbcBoth(0, 0, 1));

			JPanel actionPanel = UiUtils.createCardPanel();
			JButton seleccionar = new JButton("Añadir seleccionadas al examen");
			JButton cancelar = new JButton("Cancelar");
			UiUtils.styleButton(seleccionar);
			UiUtils.styleButton(cancelar);
			actionPanel.add(seleccionar, UiUtils.gbc(0, 0, 1));
			actionPanel.add(cancelar, UiUtils.gbc(1, 0, 1));

			seleccionar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					addSelectedToExam();
				}
			});

			cancelar.addActionListener(new ActionListener() {
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

		private void addSelectedToExam() {
			List<Pregunta> selectedQuestions = new ArrayList<>();

			// Check which rows are selected via checkbox
			for (int i = 0; i < tableModel.getRowCount(); i++) {
				Boolean isSelected = (Boolean) tableModel.getValueAt(i, 9);
				if (isSelected != null && isSelected) {
					// Check if already selected in parent exam
					int questionId = (Integer) tableModel.getValueAt(i, 0);
					if (!parentSelector.alreadySelectedIds.contains(questionId)) {
						selectedQuestions.add(currentResults.get(i));
					}
				}
			}

			if (selectedQuestions.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No hay preguntas seleccionadas o ya están en el examen.",
						"Ninguna selección",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			parentSelector.addSelectedQuestions(selectedQuestions);
			dispose();
		}
	}
}
