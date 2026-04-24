package CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import backend.Pregunta;

/**
 * Clase encargada de gestionar las operaciones CRUD relacionadas
 * con preguntas de desarrollo (Preguntas tipo explicación).
 *
 * Esta clase inserta primero la pregunta en la tabla "pregunta"
 * y después inserta la respuesta modelo en "pregunta_desarrollo".
 */
public class QuestionQuerys {

    /**
     * Inserta una pregunta de desarrollo en la base de datos.
     *
     * @param p Pregunta base con autor, curso, grupo, módulo, etc.
     * @param respuestaModelo Texto de la respuesta modelo.
     * @return true si la operación fue exitosa, false si ocurrió un error.
     */
    public static boolean addExplainQuest(Pregunta p, String respuestaModelo) {

        String sqlPregunta = "INSERT INTO pregunta "
                + "(autor, curso, grupo, modulo, ra, tema, enunciado, fecha_creacion) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        String sqlDesarrollo = "INSERT INTO pregunta_desarrollo "
                + "(pregunta_id, respuesta_modelo) VALUES (?, ?)";

        try (Connection conn = ConnectionDB.getConnection()) {

            // 1. Insertar la pregunta base
            PreparedStatement stmtPregunta = conn.prepareStatement(sqlPregunta, PreparedStatement.RETURN_GENERATED_KEYS);
            stmtPregunta.setString(1, p.getAutor());
            stmtPregunta.setString(2, p.getCurso());
            stmtPregunta.setString(3, p.getGrupo());
            stmtPregunta.setString(4, p.getModulo());
            stmtPregunta.setString(5, p.getRa());
            stmtPregunta.setString(6, p.getTema());
            stmtPregunta.setString(7, p.getEnunciado());
            stmtPregunta.setDate(8, java.sql.Date.valueOf(p.getFechaCreacion()));

            stmtPregunta.executeUpdate();

            // 2. Obtener el ID generado
            ResultSet rs = stmtPregunta.getGeneratedKeys();
            int preguntaId = -1;

            if (rs.next()) {
                preguntaId = rs.getInt(1);
            }

            if (preguntaId == -1) {
                System.err.println("ERROR: No se pudo obtener el ID de la pregunta insertada.");
                return false;
            }

            // 3. Insertar la respuesta modelo
            PreparedStatement stmtDesarrollo = conn.prepareStatement(sqlDesarrollo);
            stmtDesarrollo.setInt(1, preguntaId);
            stmtDesarrollo.setString(2, respuestaModelo);

            stmtDesarrollo.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}


