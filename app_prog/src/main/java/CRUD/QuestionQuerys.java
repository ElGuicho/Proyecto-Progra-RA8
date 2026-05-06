package CRUD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
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
						desarrolloStmt.setInt(1, preguntaId);
						desarrolloStmt.setString(2, p.getRespuestaModelo());
						desarrolloStmt.executeUpdate();
						// Insert keywords
						for (String kw : p.getPalabrasClave()) {
							keywordStmt.setInt(1, preguntaId);
							keywordStmt.setString(2, kw);
							keywordStmt.addBatch();
						}
						keywordStmt.executeBatch();
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
						testStmt.setInt(1, preguntaId);
						List<String> opciones = p.getOpciones();
						testStmt.setString(2, opciones.get(0));
						testStmt.setString(3, opciones.get(1));
						testStmt.setString(4, opciones.get(2));
						testStmt.setString(5, opciones.get(3));
						testStmt.setInt(6, p.getCorrecta());
						testStmt.executeUpdate();
						// Insert keywords
						for (String kw : p.getPalabrasClave()) {
							keywordStmt.setInt(1, preguntaId);
							keywordStmt.setString(2, kw);
							keywordStmt.addBatch();
						}
						keywordStmt.executeBatch();
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

	public static void rmQuest(int id) {
		String query = "DELETE FROM pregunta WHERE id = ?";
		try (Connection conn = DriverManager.getConnection(db_url, db_user, db_pwd);
				PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
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

	public static List<Pregunta> searchQuestions(HashMap<String, String> filtros, boolean isTest) {
		StringBuilder query = new StringBuilder();
		String table = isTest ? "pregunta_test" : "pregunta_desarrollo";
		String alias = isTest ? "pt" : "pd";
		query.append("SELECT p.id, p.autor, p.curso, p.grupo, p.modulo, p.ra, p.tema, p.enunciado, p.fecha_creacion");
		if (isTest) {
			query.append(", pt.opcion1, pt.opcion2, pt.opcion3, pt.opcion4, pt.correcta");
		} else {
			query.append(", pd.respuesta_modelo");
		}
		query.append(" FROM pregunta p JOIN ").append(table).append(" ").append(alias).append(" ON p.id = ")
				.append(alias).append(".pregunta_id");

		if (!filtros.isEmpty()) {
			query.append(" WHERE ");
			for (String key : filtros.keySet()) {
				query.append(key).append(" = ? AND ");
			}
			query.setLength(query.length() - 5);
		}

		List<Pregunta> results = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(db_url, db_user, db_pwd);
				PreparedStatement stmt = conn.prepareStatement(query.toString())) {
			int index = 1;
			for (String key : filtros.keySet()) {
				stmt.setString(index++, filtros.get(key));
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
