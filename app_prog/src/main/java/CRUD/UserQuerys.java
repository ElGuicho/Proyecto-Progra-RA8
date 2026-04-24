package CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserQuerys {

    private static final String SQL_FIND_BY_NAME =
            "SELECT id, nombre, password_hash FROM usuario WHERE nombre = ?";

    private static final String SQL_INSERT =
            "INSERT INTO usuario (nombre, password_hash) VALUES (?, ?)";

    // Obtener usuario por nombre
    public static ResultSet getUserPwd(String nombre) {
        try {
            Connection conn = ConnectionDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_NAME);
            stmt.setString(1, nombre);
            return stmt.executeQuery(); // NO cierres la conexión aquí

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Crear usuario con contraseña hasheada
    public static boolean createUser(String user, String pwdHash) {
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {

            stmt.setString(1, user);
            stmt.setString(2, pwdHash);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
