package CRUD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import Model.Pregunta;
import Model.PreguntaDesarrollo;
import Model.PreguntaTest;

public class QuestionQuerys {
	private static String db_url = "jdbc:mariadb://localhost:3306/examquest_db";
	private static String db_user = "root";
	private static String db_pwd = "Admin1234";

	public static int addTextQuest(PreguntaDesarrollo p) {
		String preguntaQuery = "INSERT INTO pregunta (autor, curso, grupo, modulo, ra, tema, enunciado, fecha_creacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		String desarrolloQuery = "INSERT INTO pregunta_desarrollo (pregunta_id, respuesta_modelo) VALUES (?, ?)";
		String keywordQuery = "INSERT INTO palabra_clave (pregunta_id, palabra) VALUES (?, ?)";
		Connection conn = null;
		int preguntaId = 0;
		try {
			// Validate respuesta_modelo
			if (p.getRespuestaModelo() == null || p.getRespuestaModelo().trim().isEmpty()) {
				throw new IllegalArgumentException("La respuesta modelo no puede ser vacía");
			}

			conn = DriverManager.getConnection(db_url, db_user, db_pwd);
			conn.setAutoCommit(false);
			try (PreparedStatement preguntaStmt = conn.prepareStatement(preguntaQuery, Statement.RETURN_GENERATED_KEYS);
					PreparedStatement desarrolloStmt = conn.prepareStatement(desarrolloQuery);
					PreparedStatement keywordStmt = conn.prepareStatement(keywordQuery)) {
				preguntaStmt.setString(1, p.getAutor());
				preguntaStmt.setString(2, p.getCurso());
				preguntaStmt.setString(3, p.getGrupo());
				preguntaStmt.setString(4, p.getModulo());
				preguntaStmt.setString(5, p.getRa());
				preguntaStmt.setString(6, p.getTema());
				preguntaStmt.setString(7, p.getEnunciado());
				preguntaStmt.setString(8, p.getFechaCreacion().toString());
				preguntaStmt.executeUpdate();
				try (ResultSet rs = preguntaStmt.getGeneratedKeys()) {
					if (rs.next()) {
						preguntaId = rs.getInt(1);
						System.out.println("Pregunta creada con ID: " + preguntaId);
						desarrolloStmt.setInt(1, preguntaId);
						desarrolloStmt.setString(2, p.getRespuestaModelo().trim());
						System.out.println("Insertando DESARROLLO: preguntaId=" + preguntaId);
						desarrolloStmt.executeUpdate();
						System.out.println("DESARROLLO insertado correctamente");
						// Insert keywords
						for (String kw : p.getPalabrasClave()) {
							keywordStmt.setInt(1, preguntaId);
							keywordStmt.setString(2, kw);
							keywordStmt.addBatch();
						}
						keywordStmt.executeBatch();
					} else {
						throw new SQLException("No se pudo obtener el ID de la pregunta generada");
					}
				}
			}
			conn.commit();
		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return preguntaId;
	}

	public static int addTestQuest(PreguntaTest p) {
		String preguntaQuery = "INSERT INTO pregunta (autor, curso, grupo, modulo, ra, tema, enunciado, fecha_creacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		String testQuery = "INSERT INTO pregunta_test (pregunta_id, opcion1, opcion2, opcion3, opcion4, correcta) VALUES (?, ?, ?, ?, ?, ?)";
		String keywordQuery = "INSERT INTO palabra_clave (pregunta_id, palabra) VALUES (?, ?)";
		Connection conn = null;
		int preguntaId = 0;
		try {
			// Validate options before proceeding
			List<String> opciones = p.getOpciones();
			if (opciones == null || opciones.size() < 4) {
				throw new IllegalArgumentException("Opciones inválidas: se requieren 4 opciones");
			}
			for (int i = 0; i < 4; i++) {
				if (opciones.get(i) == null || opciones.get(i).trim().isEmpty()) {
					throw new IllegalArgumentException("Opción " + (i + 1) + " no puede ser vacía");
				}
			}
			if (p.getCorrecta() < 1 || p.getCorrecta() > 4) {
				throw new IllegalArgumentException("Opción correcta debe estar entre 1 y 4");
			}

			conn = DriverManager.getConnection(db_url, db_user, db_pwd);
			conn.setAutoCommit(false);
			try (PreparedStatement preguntaStmt = conn.prepareStatement(preguntaQuery, Statement.RETURN_GENERATED_KEYS);
					PreparedStatement testStmt = conn.prepareStatement(testQuery);
					PreparedStatement keywordStmt = conn.prepareStatement(keywordQuery)) {
				preguntaStmt.setString(1, p.getAutor());
				preguntaStmt.setString(2, p.getCurso());
				preguntaStmt.setString(3, p.getGrupo());
				preguntaStmt.setString(4, p.getModulo());
				preguntaStmt.setString(5, p.getRa());
				preguntaStmt.setString(6, p.getTema());
				preguntaStmt.setString(7, p.getEnunciado());
				preguntaStmt.setString(8, p.getFechaCreacion().toString());
				preguntaStmt.executeUpdate();
				try (ResultSet rs = preguntaStmt.getGeneratedKeys()) {
					if (rs.next()) {
						preguntaId = rs.getInt(1);
						System.out.println("Pregunta creada con ID: " + preguntaId);
						testStmt.setInt(1, preguntaId);
						testStmt.setString(2, opciones.get(0).trim());
						testStmt.setString(3, opciones.get(1).trim());
						testStmt.setString(4, opciones.get(2).trim());
						testStmt.setString(5, opciones.get(3).trim());
						testStmt.setInt(6, p.getCorrecta());
						System.out
								.println("Insertando TEST: preguntaId=" + preguntaId + ", correcta=" + p.getCorrecta());
						testStmt.executeUpdate();
						System.out.println("TEST insertado correctamente");
						// Insert keywords
						for (String kw : p.getPalabrasClave()) {
							keywordStmt.setInt(1, preguntaId);
							keywordStmt.setString(2, kw);
							keywordStmt.addBatch();
						}
						keywordStmt.executeBatch();
					} else {
						throw new SQLException("No se pudo obtener el ID de la pregunta generada");
					}
				}
			}
			conn.commit();
		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			System.err.println("Error al crear pregunta TEST: " + e.getMessage());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.err.println("Validación fallida: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return preguntaId;
	}

	public static List<String> getKeyWords(int preguntaId) {
		String query = "SELECT palabra FROM palabra_clave WHERE pregunta_id = ?";
		List<String> keywords = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(db_url, db_user, db_pwd);
				PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, preguntaId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					keywords.add(rs.getString("palabra"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return keywords;
	}

	public static boolean rmQuest(int id) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(db_url, db_user, db_pwd);
			conn.setAutoCommit(false);

			// Delete keywords first
			String deleteKeywords = "DELETE FROM palabra_clave WHERE pregunta_id = ?";
			try (PreparedStatement stmt = conn.prepareStatement(deleteKeywords)) {
				stmt.setInt(1, id);
				stmt.executeUpdate();
			}

			// Delete from pregunta_test or pregunta_desarrollo
			String deleteTestQuest = "DELETE FROM pregunta_test WHERE pregunta_id = ?";
			String deleteDevQuest = "DELETE FROM pregunta_desarrollo WHERE pregunta_id = ?";
			try (PreparedStatement stmt = conn.prepareStatement(deleteTestQuest)) {
				stmt.setInt(1, id);
				stmt.executeUpdate();
			}
			try (PreparedStatement stmt = conn.prepareStatement(deleteDevQuest)) {
				stmt.setInt(1, id);
				stmt.executeUpdate();
			}

			// Finally delete from pregunta
			String deleteQuestion = "DELETE FROM pregunta WHERE id = ?";
			try (PreparedStatement stmt = conn.prepareStatement(deleteQuestion)) {
				stmt.setInt(1, id);
				int result = stmt.executeUpdate();
				if (result == 0) {
					conn.rollback();
					return false;
				}
			}

			conn.commit();
			return true;
		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static boolean updateQuestionEnunciado(int id, String enunciado) {
		String query = "UPDATE pregunta SET enunciado = ? WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(db_url, db_user, db_pwd);
				PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, enunciado);
			stmt.setInt(2, id);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean updateTestQuestion(int id, String curso, String grupo, String modulo, String ra, String tema,
			String enunciado, List<String> opciones, int correcta, List<String> palabrasClave) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(db_url, db_user, db_pwd);
			conn.setAutoCommit(false);

			// Update main question table
			String updatePregunta = "UPDATE pregunta SET curso = ?, grupo = ?, modulo = ?, ra = ?, tema = ?, enunciado = ? WHERE id = ?";
			try (PreparedStatement stmt = conn.prepareStatement(updatePregunta)) {
				stmt.setString(1, curso);
				stmt.setString(2, grupo);
				stmt.setString(3, modulo);
				stmt.setString(4, ra);
				stmt.setString(5, tema);
				stmt.setString(6, enunciado);
				stmt.setInt(7, id);
				if (stmt.executeUpdate() == 0) {
					conn.rollback();
					return false;
				}
			}

			// Update test question table
			String updateTest = "UPDATE pregunta_test SET opcion1 = ?, opcion2 = ?, opcion3 = ?, opcion4 = ?, correcta = ? WHERE pregunta_id = ?";
			try (PreparedStatement stmt = conn.prepareStatement(updateTest)) {
				stmt.setString(1, opciones.get(0));
				stmt.setString(2, opciones.get(1));
				stmt.setString(3, opciones.get(2));
				stmt.setString(4, opciones.get(3));
				stmt.setInt(5, correcta);
				stmt.setInt(6, id);
				if (stmt.executeUpdate() == 0) {
					conn.rollback();
					return false;
				}
			}

			// Update keywords
			updateKeywords(conn, id, palabrasClave);

			conn.commit();
			return true;
		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static boolean updateDevelopmentQuestion(int id, String curso, String grupo, String modulo, String ra,
			String tema,
			String enunciado, String respuestaModelo, List<String> palabrasClave) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(db_url, db_user, db_pwd);
			conn.setAutoCommit(false);

			// Update main question table
			String updatePregunta = "UPDATE pregunta SET curso = ?, grupo = ?, modulo = ?, ra = ?, tema = ?, enunciado = ? WHERE id = ?";
			try (PreparedStatement stmt = conn.prepareStatement(updatePregunta)) {
				stmt.setString(1, curso);
				stmt.setString(2, grupo);
				stmt.setString(3, modulo);
				stmt.setString(4, ra);
				stmt.setString(5, tema);
				stmt.setString(6, enunciado);
				stmt.setInt(7, id);
				if (stmt.executeUpdate() == 0) {
					conn.rollback();
					return false;
				}
			}

			// Update development question table
			String updateDev = "UPDATE pregunta_desarrollo SET respuesta_modelo = ? WHERE pregunta_id = ?";
			try (PreparedStatement stmt = conn.prepareStatement(updateDev)) {
				stmt.setString(1, respuestaModelo);
				stmt.setInt(2, id);
				if (stmt.executeUpdate() == 0) {
					conn.rollback();
					return false;
				}
			}

			// Update keywords
			updateKeywords(conn, id, palabrasClave);

			conn.commit();
			return true;
		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			e.printStackTrace();
			return false;
		} finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void updateKeywords(Connection conn, int preguntaId, List<String> palabrasClave)
			throws SQLException {
		// Delete existing keywords
		String deleteKeywords = "DELETE FROM palabra_clave WHERE pregunta_id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(deleteKeywords)) {
			stmt.setInt(1, preguntaId);
			stmt.executeUpdate();
		}

		// Insert new keywords
		if (palabrasClave != null && !palabrasClave.isEmpty()) {
			String insertKeyword = "INSERT INTO palabra_clave (pregunta_id, palabra) VALUES (?, ?)";
			try (PreparedStatement stmt = conn.prepareStatement(insertKeyword)) {
				for (String keyword : palabrasClave) {
					if (keyword != null && !keyword.trim().isEmpty()) {
						stmt.setInt(1, preguntaId);
						stmt.setString(2, keyword.trim());
						stmt.executeUpdate();
					}
				}
			}
		}
	}

	public static List<Pregunta> getAllQuestions() {
		List<Pregunta> allQuestions = new ArrayList<>();
		// Get test questions
		allQuestions.addAll(searchQuestions(new HashMap<>(), true));
		// Get development questions
		allQuestions.addAll(searchQuestions(new HashMap<>(), false));
		return allQuestions;
	}

	public static List<Pregunta> searchQuestions(HashMap<String, String> filtros, boolean isTest) {
		StringBuilder query = new StringBuilder();
		String table = isTest ? "pregunta_test" : "pregunta_desarrollo";
		String alias = isTest ? "pt" : "pd";
		query.append(
				"SELECT DISTINCT p.id, p.autor, p.curso, p.grupo, p.modulo, p.ra, p.tema, p.enunciado, p.fecha_creacion");
		if (isTest) {
			query.append(", pt.opcion1, pt.opcion2, pt.opcion3, pt.opcion4, pt.correcta");
		} else {
			query.append(", pd.respuesta_modelo");
		}
		query.append(" FROM pregunta p JOIN ").append(table).append(" ").append(alias).append(" ON p.id = ")
				.append(alias).append(".pregunta_id");

		List<String> keywordList = null;
		if (filtros.containsKey("palabras_clave") && !filtros.get("palabras_clave").trim().isEmpty()) {
			String keywordsStr = filtros.get("palabras_clave").trim();
			keywordList = Arrays.asList(keywordsStr.split(","));
			// Remove from filtros to handle separately
			filtros.remove("palabras_clave");
		}

		if (!filtros.isEmpty()) {
			query.append(" WHERE ");
			for (String key : filtros.keySet()) {
				query.append("p.").append(key).append(" = ? AND ");
			}
			query.setLength(query.length() - 5);
		}

		if (keywordList != null && !keywordList.isEmpty()) {
			if (!filtros.isEmpty()) {
				query.append(" AND ");
			} else {
				query.append(" WHERE ");
			}
			query.append("EXISTS (SELECT 1 FROM palabra_clave pc WHERE pc.pregunta_id = p.id AND pc.palabra IN (");
			for (int i = 0; i < keywordList.size(); i++) {
				query.append("?");
				if (i < keywordList.size() - 1) {
					query.append(",");
				}
			}
			query.append("))");
		}

		List<Pregunta> results = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(db_url, db_user, db_pwd);
				PreparedStatement stmt = conn.prepareStatement(query.toString())) {
			int index = 1;
			for (String key : filtros.keySet()) {
				stmt.setString(index++, filtros.get(key));
			}
			if (keywordList != null && !keywordList.isEmpty()) {
				for (String kw : keywordList) {
					stmt.setString(index++, kw.trim());
				}
			}
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int id = rs.getInt("id");
					String autor = rs.getString("autor");
					String curso = rs.getString("curso");
					String grupo = rs.getString("grupo");
					String modulo = rs.getString("modulo");
					String ra = rs.getString("ra");
					String tema = rs.getString("tema");
					String enunciado = rs.getString("enunciado");
					LocalDate fechaCreacion = rs.getDate("fecha_creacion").toLocalDate();
					List<String> palabrasClave = getKeyWords(id);

					if (isTest) {
						List<String> opciones = new ArrayList<>();
						opciones.add(rs.getString("opcion1"));
						opciones.add(rs.getString("opcion2"));
						opciones.add(rs.getString("opcion3"));
						opciones.add(rs.getString("opcion4"));
						int correcta = rs.getInt("correcta");
						PreguntaTest pt = new PreguntaTest(id, autor, curso, grupo, modulo, ra, tema, enunciado,
								fechaCreacion, palabrasClave, opciones, correcta);
						results.add(pt);
					} else {
						String respuestaModelo = rs.getString("respuesta_modelo");
						PreguntaDesarrollo pd = new PreguntaDesarrollo(id, autor, curso, grupo, modulo, ra, tema,
								enunciado, fechaCreacion, palabrasClave, respuestaModelo);
						results.add(pd);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
}
