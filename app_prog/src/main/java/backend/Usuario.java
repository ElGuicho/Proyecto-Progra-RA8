package backend;

import com.mycompany.proyecto.utils.HashUtil;

/**
 * Clase que representa a un usuario del sistema.
 * 
 * Contiene la información necesaria para la autenticación:
 * - id del usuario
 * - nombre de usuario
 * - contraseña almacenada como hash SHA-256
 */
public class Usuario {

    private int id;
    private String nombre;
    private String passwordHash;

    /**
     * Constructor vacío necesario para los DAO.
     */
    public Usuario() {}

    /**
     * Constructor completo.
     */
    public Usuario(int id, String nombre, String passwordHash) {
        this.id = id;
        this.nombre = nombre;
        this.passwordHash = passwordHash;
    }

    /**
     * Verifica si la contraseña ingresada coincide con el hash almacenado.
     *
     * @param passwordTextoPlano contraseña ingresada por el usuario
     * @return true si coincide, false si no
     */
    public boolean verificarPassword(String passwordTextoPlano) {
        return HashUtil.verificar(passwordTextoPlano, this.passwordHash);
    }
 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    // Métodos de utilidad
    
    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nombre=" + nombre + "]";
    }
}
id + ", nombre=" + nombre + ", passwordHash=" + passwordHash + "]";
	}
}
