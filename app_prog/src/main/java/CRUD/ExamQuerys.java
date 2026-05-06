package CRUD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExamQuerys {
    private static String db_url = "jdbc:mariadb://localhost:3306/examquest_db";
    private static String db_user = "root";
    private static String db_pwd = "Admin1234";

    public static String examRndQuestsSummary(int quest_num, Integer[] usedIds, boolean isTest) {
        String table = isTest ? "pregunta_test" : "pregunta_desarrollo";
        StringBuilder query = new StringBuilder();
        query.append("SELECT p.id, p.curso, p.grupo, p.modulo, p.tema, p.enunciado");
        if (isTest) {
            query.append(", t.opcion1, t.opcion2, t.opcion3, t.opcion4, t.correcta");
        } else {
            query.append(", t.respuesta_modelo");
        }
        query.append(" FROM pregunta p JOIN ").append(table).append(" t ON p.id = t.pregunta_id");

        if (usedIds != null && usedIds.length > 0) {
            query.append(" WHERE p.id NOT IN (");
            for (int i = 0; i < usedIds.length; i++) {
                query.append("?");
                if (i < usedIds.length - 1) {
                    query.append(", ");
                }
            }
            query.append(")");
        }
        query.append(" ORDER BY RAND() LIMIT ?");

        try (Connection conn = DriverManager.getConnection(db_url, db_user, db_pwd);
                PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            int index = 1;
            if (usedIds != null) {
                for (Integer id : usedIds) {
                    stmt.setInt(index++, id);
                }
            }
            stmt.setInt(index, quest_num);

            try (ResultSet rs = stmt.executeQuery()) {
                StringBuilder result = new StringBuilder();
                int counter = 1;
                while (rs.next()) {
                    result.append(counter++).append(". ");
                    result.append("Tema: ").append(rs.getString("tema")).append("\n");
                    result.append(rs.getString("enunciado")).append("\n");
                    if (isTest) {
                        result.append("  A: ").append(rs.getString("opcion1")).append("\n");
                        result.append("  B: ").append(rs.getString("opcion2")).append("\n");
                        result.append("  C: ").append(rs.getString("opcion3")).append("\n");
                        result.append("  D: ").append(rs.getString("opcion4")).append("\n");
                        result.append("  Correcta: ").append(rs.getInt("correcta")).append("\n");
                    } else {
                        result.append("  Respuesta modelo: ").append(rs.getString("respuesta_modelo")).append("\n");
                    }
                    result.append("---------------------------------------------------\n");
                }
                return result.toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
}
