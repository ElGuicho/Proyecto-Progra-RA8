package CRUD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import backend.PreguntaDesarrollo;
import backend.PreguntaTest;

public class QuestionQuerys {
	private static String db_url = "jdbc:mariadb://localhost:3306/examquest_db";
	private static String db_user = "root";
	private static String db_pwd = "Admin1234";

	public static void addTextQuest(PreguntaDesarrollo p, String respuesta_modelo) {
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
				+ " VALUES (" + preguntaId + ", '" + respuesta_modelo + "');");
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
}
