package CRUD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserQuerys {
	private static String db_url = "jdbc:mariadb://localhost:3306/examquest_db";
	private static String db_user = "root";
	private static String db_pwd = "Admin1234";

	public static ResultSet getUserPwd() {
		try (Connection conn = DriverManager.getConnection(db_url, db_user, db_pwd)) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT nombre, password_hash FROM usuario");
			stmt.close();
			conn.close();
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean createUser(String user, String pwd) {
		try (Connection conn = DriverManager.getConnection(db_url, db_user, db_pwd)) {
			Statement stmt = conn.createStatement();
			stmt.executeQuery("INSERT INTO usuario (nombre, password_hash) VALUES ('" + user + "', '"
					+ Integer.toString(pwd.hashCode()) + "');");
			stmt.close();
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
