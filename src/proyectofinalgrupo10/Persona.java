/**
 * Clase abstracta base para todas las personas del sistema.
 * Demuestra herencia y clases abstractas.
 */
public abstract class Persona {

    protected String dni;
    protected String nombres;
    protected String apellidos;

    public Persona(String dni, String nombres, String apellidos) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    /**
     * Cada subclase muestra su información de forma distinta (polimorfismo).
     */
    public abstract String mostrarInformacion();
}
