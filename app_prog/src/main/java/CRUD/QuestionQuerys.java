package CRUD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import Model.PreguntaDesarrollo;
import Model.PreguntaTest;

public class QuestionQuerys {
    private static String db_url = "jdbc:mariadb://localhost:3306/examquest_db";
    private static String db_user = "root";
    private static String db_pwd = "Admin1234";

    public static void addTextQuest(PreguntaDesarrollo p) {
        String preguntaQuery = "INSERT INTO pregunta (autor, curso, grupo, modulo, ra, tema, enunciado, fecha_creacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String desarrolloQuery = "INSERT INTO pregunta_desarrollo (pregunta_id, respuesta_modelo) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(db_url, db_user, db_pwd);
                PreparedStatement preguntaStmt = conn.prepareStatement(preguntaQuery, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement desarrolloStmt = conn.prepareStatement(desarrolloQuery)) {
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
                    int preguntaId = rs.getInt(1);
                    desarrolloStmt.setInt(1, preguntaId);
                    desarrolloStmt.setString(2, p.getRespuestaModelo());
                    desarrolloStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addTestQuest(PreguntaTest p) {
        String preguntaQuery = "INSERT INTO pregunta (autor, curso, grupo, modulo, ra, tema, enunciado, fecha_creacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String testQuery = "INSERT INTO pregunta_test (pregunta_id, opcion1, opcion2, opcion3, opcion4, correcta) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(db_url, db_user, db_pwd);
                PreparedStatement preguntaStmt = conn.prepareStatement(preguntaQuery, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement testStmt = conn.prepareStatement(testQuery)) {
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
                    int preguntaId = rs.getInt(1);
                    testStmt.setInt(1, preguntaId);
                    List<String> opciones = p.getOpciones();
                    testStmt.setString(2, opciones.get(0));
                    testStmt.setString(3, opciones.get(1));
                    testStmt.setString(4, opciones.get(2));
                    testStmt.setString(5, opciones.get(3));
                    testStmt.setInt(6, p.getCorrecta());
                    testStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public static String searchQuestions(HashMap<String, String> filtros, boolean isTest) {
        StringBuilder query = new StringBuilder();
        String table = isTest ? "pregunta_test" : "pregunta_desarrollo";
        query.append("SELECT p.id, p.autor, p.curso, p.grupo, p.modulo, p.ra, p.tema, p.enunciado");
        if (isTest) {
            query.append(", pt.opcion1, pt.opcion2, pt.opcion3, pt.opcion4, pt.correcta");
        } else {
            query.append(", pd.respuesta_modelo");
        }
        query.append(" FROM pregunta p JOIN ").append(table).append(" pt ON p.id = pt.pregunta_id");

        if (!filtros.isEmpty()) {
            query.append(" WHERE ");
            for (String key : filtros.keySet()) {
                query.append(key).append(" = ? AND ");
            }
            query.setLength(query.length() - 5);
        }

        try (Connection conn = DriverManager.getConnection(db_url, db_user, db_pwd);
                PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            int index = 1;
            for (String key : filtros.keySet()) {
                stmt.setString(index++, filtros.get(key));
            }
            try (ResultSet rs = stmt.executeQuery()) {
                StringBuilder result = new StringBuilder();
                while (rs.next()) {
                    result.append("ID: ").append(rs.getInt("id")).append("\n");
                    result.append("Autor: ").append(rs.getString("autor")).append("\n");
                    result.append("Curso: ").append(rs.getString("curso")).append("\n");
                    result.append("M�dulo: ").append(rs.getString("modulo")).append("\n");
                    result.append("Tema: ").append(rs.getString("tema")).append("\n");
                    result.append("Enunciado: ").append(rs.getString("enunciado")).append("\n");
                    if (isTest) {
                        result.append("Opciones: [").append(rs.getString("opcion1")).append(", ")
                                .append(rs.getString("opcion2")).append(", ")
                                .append(rs.getString("opcion3")).append(", ")
                                .append(rs.getString("opcion4")).append("]\n");
                        result.append("Correcta: ").append(rs.getInt("correcta")).append("\n");
                    } else {
                        result.append("Respuesta modelo: ").append(rs.getString("respuesta_modelo")).append("\n");
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
