package CRUD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserQuerys {
    private static String db_url = "jdbc:mariadb://localhost:3306/examquest_db";
    private static String db_user = "root";
    private static String db_pwd = "Admin1234";

    public static ResultSet getUserPwd() {
        try {
            Connection conn = DriverManager.getConnection(db_url, db_user, db_pwd);
            Statement stmt = conn.createStatement();
            return stmt.executeQuery("SELECT id, nombre, password_hash FROM usuario");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean userExists(String user) {
        String query = "SELECT id FROM usuario WHERE nombre = ?";
        try (Connection conn = DriverManager.getConnection(db_url, db_user, db_pwd);
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean authenticateUser(String user, String pwd) {
        String query = "SELECT password_hash FROM usuario WHERE nombre = ?";
        try (Connection conn = DriverManager.getConnection(db_url, db_user, db_pwd);
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1).equals(hashPwd(pwd));
                }
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void createUser(String user, String pwd) {
        String query = "INSERT INTO usuario (nombre, password_hash) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(db_url, db_user, db_pwd);
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user);
            stmt.setString(2, hashPwd(pwd));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getUserId() {
        try (Connection conn = DriverManager.getConnection(db_url, db_user, db_pwd);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt
                        .executeQuery("SELECT usuario_id FROM operacion WHERE id = (SELECT MAX(id) FROM operacion);")) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static String hashPwd(String pwd) {
        return Integer.toString(pwd.hashCode());
    }
}
