/**
 * Clase abstracta que representa a un empleado de la inmobiliaria.
 * No se puede instanciar directamente: se crean Administrador,
 * AsesorVenta o Gerente (herencia y polimorfismo).
 */
public abstract class Empleado extends Persona {

    protected String usuario;
    protected String contrasena;

    public Empleado(String dni, String nombres, String apellidos,
            String usuario, String contrasena) {
        super(dni, nombres, apellidos);
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Verifica las credenciales del empleado.
     */
    public boolean validarCredenciales(String usuario, String contrasena) {
        return this.usuario.equals(usuario) && this.contrasena.equals(contrasena);
    }

    /**
     * Cada rol devuelve su nombre (polimorfismo).
     */
    public abstract String obtenerRol();

    @Override
    public String mostrarInformacion() {
        return "DNI: " + dni
                + " | Nombre: " + getNombreCompleto()
                + " | Usuario: " + usuario
                + " | Rol: " + obtenerRol();
    }
}
