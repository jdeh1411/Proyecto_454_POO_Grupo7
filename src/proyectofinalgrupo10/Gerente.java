/**
 * Empleado con rol de gerente. Solo consulta reportes.
 */
public class Gerente extends Empleado {

    public Gerente(String dni, String nombres, String apellidos,
            String usuario, String contrasena) {
        super(dni, nombres, apellidos, usuario, contrasena);
    }

    @Override
    public String obtenerRol() {
        return "GERENTE";
    }
}
