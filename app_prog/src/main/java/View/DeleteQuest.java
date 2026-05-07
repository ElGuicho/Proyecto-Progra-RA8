package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import Model.PreguntaTest;

public class DeleteQuest extends JFrame {

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

	public DeleteQuest() {
		UiUtils.setupFrame(this, "Eliminar pregunta", 600, 700);

		JPanel outerPanel = UiUtils.createAppPanel();
		JPanel panel = UiUtils.createCardPanel();
		panel.add(UiUtils.createTitle("Buscar y eliminar preguntas"), UiUtils.gbc(0, 0, 2));

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

		new DeleteResultsWindow(results, filters, isTest);
	}

	private void showAllQuestions() {
		List<Pregunta> allQuestions = QuestionQuerys.getAllQuestions();
		if (allQuestions.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No hay preguntas en la base de datos.", "Sin resultados",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		new DeleteResultsWindow(allQuestions, new HashMap<>(), null);
	}

	private class DeleteResultsWindow extends JFrame {
		private JTable resultsTable;
		private DefaultTableModel tableModel;
		private List<Pregunta> currentResults;
		private HashMap<String, String> currentFilters;
		private Boolean isTest;

		public DeleteResultsWindow(List<Pregunta> results, HashMap<String, String> filters, Boolean isTest) {
			currentResults = results;
			this.currentFilters = filters;
			this.isTest = isTest;
			UiUtils.setupFrame(this, "Resultados - Seleccione para eliminar", 900, 600);

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
							confirmDelete(selectedRow);
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

		private void confirmDelete(int rowIndex) {
			if (currentResults == null || rowIndex < 0 || rowIndex >= currentResults.size()) {
				return;
			}
			Pregunta p = currentResults.get(rowIndex);

			int option = JOptionPane.showConfirmDialog(this,
					"¿Está seguro de que desea eliminar la pregunta con ID " + p.getId() + "?\n\n" +
							"Enunciado: " + p.getEnunciado() + "\n" +
							"Esta acción no se puede deshacer.",
					"Confirmar eliminación",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);

			if (option == JOptionPane.YES_OPTION) {
				deleteQuestion(p.getId());
			}
		}

		private void deleteQuestion(int id) {
			if (QuestionQuerys.rmQuest(id)) {
				JOptionPane.showMessageDialog(this, "Pregunta eliminada correctamente.", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
				UserQuerys.logOperation(UserQuerys.getUserId(), "Eliminar pregunta",
						UserQuerys.getUserName(UserQuerys.getUserId()) + " eliminó la pregunta con ID " + id);
				dispose();
				// Refresh the main window
			} else {
				JOptionPane.showMessageDialog(this, "Error al eliminar la pregunta.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
