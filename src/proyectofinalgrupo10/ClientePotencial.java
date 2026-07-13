import java.time.LocalDate;

/**
 * Cliente potencial de la inmobiliaria.
 * Hereda de Persona (herencia).
 */
public class ClientePotencial extends Persona {

    private String telefono;
    private String correo;
    private LocalDate fechaRegistro;

    public ClientePotencial(String dni, String nombres, String apellidos,
            String telefono, String correo) {
        super(dni, nombres, apellidos);
        this.telefono = telefono;
        this.correo = correo;
        this.fechaRegistro = LocalDate.now();
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    @Override
    public String mostrarInformacion() {
        return "DNI: " + dni
                + " | Nombre: " + getNombreCompleto()
                + " | Telefono: " + telefono
                + " | Correo: " + correo
                + " | Fecha de registro: " + fechaRegistro;
    }
}
