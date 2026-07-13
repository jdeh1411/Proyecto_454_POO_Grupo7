/**
 * Empleado con rol de administrador del sistema.
 */
public class Administrador extends Empleado {

    public Administrador(String dni, String nombres, String apellidos,
            String usuario, String contrasena) {
        super(dni, nombres, apellidos, usuario, contrasena);
    }

    @Override
    public String obtenerRol() {
        return "ADMINISTRADOR";
    }
}
