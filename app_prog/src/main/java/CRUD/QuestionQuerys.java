package CRUD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import Model.PreguntaDesarrollo;
import Model.PreguntaTest;

public class QuestionQuerys {
	private static String db_url = "jdbc:mariadb://localhost:3306/examquest_db";
	private static String db_user = "root";
	private static String db_pwd = "Admin1234";

	public static void addTextQuest(PreguntaDesarrollo p) {
		try (Connection conn = DriverManager.getConnection(db_url, db_user, db_pwd); Statement stmt = conn.createStatement()) {
			stmt.executeUpdate("INSERT INTO pregunta (autor, curso, grupo, modulo, ra, tema, enunciado, fecha_creacion)"
				+ " VALUES ('" + p.getAutor() + "', '" + p.getCurso() + "', '" + p.getGrupo() + "', '" + p.getModulo()
				+ "', '" + p.getRa() + "', '" + p.getTema() + "', '" + p.getEnunciado() + "', '" + p.getFechaCreacion().toString() + "');");
			
			ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID();");
			int preguntaId = 0;
			if (rs.next()) {
				preguntaId = rs.getInt(1);
			}
			rs.close();
			stmt.executeUpdate("INSERT INTO pregunta_desarrollo (pregunta_id, respuesta_modelo)"
				+ " VALUES (" + preguntaId + ", '" + p.getRespuestaModelo() + "');");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addTestQuest(PreguntaTest p) {
		try (Connection conn = DriverManager.getConnection(db_url, db_user, db_pwd); Statement stmt = conn.createStatement()) {
			stmt.executeUpdate("INSERT INTO pregunta (autor, curso, grupo, modulo, ra, tema, enunciado, fecha_creacion)"
				+ " VALUES ('" + p.getAutor() + "', '" + p.getCurso() + "', '" + p.getGrupo() + "', '" + p.getModulo()
				+ "', '" + p.getRa() + "', '" + p.getTema() + "', '" + p.getEnunciado() + "', '" + p.getFechaCreacion().toString() + "');");
			
			ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID();");
			int preguntaId = 0;
			if (rs.next()) {
				preguntaId = rs.getInt(1);
			}
			rs.close();
			stmt.executeUpdate("INSERT INTO pregunta_test (pregunta_id, opcion1, opcion2, opcion3, opcion4, correcta)"
				+ " VALUES (" + preguntaId + ", '" + p.getOpciones().get(0) + ", '" + p.getOpciones().get(1) + ", '"
				+ p.getOpciones().get(2) + ", '" + p.getOpciones().get(3) + "', " + p.getCorrecta() + ");");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void rmQuest(int id) {
		try (Connection conn = DriverManager.getConnection(db_url, db_user, db_pwd); Statement stmt = conn.createStatement()) {
			stmt.executeUpdate("DELETE FROM pregunta where id = " + Integer.toString(id) + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void modTextQuest(HashMap<String, String> camposP, HashMap<String, String> camposPd) {
		try (Connection conn = DriverManager.getConnection(db_url, db_user, db_pwd); Statement stmt = conn.createStatement()) {
			String query = "UPDATE pregunta SET ";
			for (String key : camposP.keySet()) {
				if (!key.equals("id")) {
					query += key + " = '" + camposP.get(key) + "', ";
				}
			}
			query = query.substring(0, query.length() - 2);
			query += " WHERE id = " + camposP.get("id") + ";";
			stmt.executeUpdate(query);
			
			query = "UPDATE pregunta_desarrollo SET ";
			for (String key : camposPd.keySet()) {
				query += key + " = '" + camposPd.get(key) + "', ";
			}
			query = query.substring(0, query.length() - 2);
			query += " WHERE id = " + camposP.get("id") + ";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void modTestQuest(HashMap<String, String> camposP, HashMap<String, String> camposPt) {
		try (Connection conn = DriverManager.getConnection(db_url, db_user, db_pwd); Statement stmt = conn.createStatement()) {
			String query = "UPDATE pregunta SET ";
			for (String key : camposP.keySet()) {
				if (!key.equals("id") && !key.equals("palabrasClave")) {
					query += key + " = '" + camposP.get(key) + "', ";
				}
				/* else if (key.equals("palabrasClave")) {
					stmt.executeUpdate("UPDATE palabra_clave SET " + key + " = '")
				} */
			}
			query = query.substring(0, query.length() - 2);
			query += " WHERE id = " + camposP.get("id") + ";";
			stmt.executeUpdate(query);
			
			query = "UPDATE pregunta_test SET ";
			for (String key : camposPt.keySet()) {
				if (!key.equals("correcta"))
					query += key + " = '" + camposPt.get(key) + "', ";
				else
					query += key + " = " + camposPt.get(key) + ", ";
			}
			query = query.substring(0, query.length() - 2);
			query += " WHERE id = " + camposP.get("id") + ";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ResultSet searchQuest(HashMap<String, String> filtros, boolean isTest) {
		try (Connection conn = DriverManager.getConnection(db_url, db_user, db_pwd); Statement stmt = conn.createStatement()) {
			String query = "";

			if (filtros.isEmpty()) {
				if (isTest)
					query = "SELECT * FROM pregunta p JOIN pregunta_test pt ON p.id = pt.pregunta_id;";
				else
					query = "SELECT * FROM pregunta p JOIN pregunta_desarrollo pd ON p.id = pd.pregunta_id;";
				ResultSet rs = stmt.executeQuery(query);
				return rs;
			}
			if (isTest) {
				query = "SELECT * FROM pregunta p JOIN pregunta_test pt ON p.id = pt.pregunta_id WHERE ";
				for (String key : filtros.keySet()) {
					if (!key.equals("correcta"))
						query += key + " = '" + filtros.get(key) + "' AND ";
				}
			} else {
				query = "SELECT * FROM pregunta p JOIN pregunta_desarrollo pd ON p.id = pd.pregunta_id WHERE ";
				for (String key : filtros.keySet()) {
					query += key + " = '" + filtros.get(key) + "' AND ";
				}
			}
			query = query.substring(0, query.length() - 5);
			query += ";";
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
