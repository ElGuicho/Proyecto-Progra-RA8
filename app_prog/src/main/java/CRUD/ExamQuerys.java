package CRUD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExamQuerys {
	private static String db_url = "jdbc:mariadb://localhost:3306/examquest_db";
	private static String db_user = "root";
	private static String db_pwd = "Admin1234";

	public static ResultSet examQuests(int quest_num, Integer[] ids, boolean isTest) {
		try (Connection conn = DriverManager.getConnection(db_url, db_user, db_pwd); Statement stmt = conn.createStatement()) {
			String table = isTest ? "pregunta_test" : "pregunta_desarrollo";
			String query = "SELECT * FROM pregunta p JOIN " + table + " t ON p.id = t.pregunta_id WHERE p.id IN (";
			for (int i = 0; i < quest_num; i++) {
				query += ids[i];
				if (i < quest_num - 1)
					query += ", ";
			}
			query += ");";
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ResultSet examRndQuests(int quest_num, Integer[] usedIds, boolean isTest) {
		try (Connection conn = DriverManager.getConnection(db_url, db_user, db_pwd); Statement stmt = conn.createStatement()) {
			String table = isTest ? "pregunta_test" : "pregunta_desarrollo";
			ResultSet rs = null;
			String query = "";

			if (usedIds.length == 0) {
				query = "SELECT * FROM pregunta p JOIN " + table + " t ON p.id = t.pregunta_id ORDER BY RAND() LIMIT " + quest_num + ";";
				rs = stmt.executeQuery(query);
				return rs;
			}
			query = "SELECT * FROM pregunta p JOIN " + table + " t ON p.id = t.pregunta_id WHERE p.id NOT IN (";
			for (int i = 0; i < usedIds.length; i++) {
				query += usedIds[i];
				if (i < usedIds.length - 1)
					query += ", ";
			}
			query += ") ORDER BY RAND() LIMIT " + quest_num + ";";
			rs = stmt.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
