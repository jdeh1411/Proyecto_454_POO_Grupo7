/**
 * Empleado con rol de asesor de venta.
 * Es el encargado de gestionar clientes, reservas, ventas y pagos.
 */
public class AsesorVenta extends Empleado {

    public AsesorVenta(String dni, String nombres, String apellidos,
            String usuario, String contrasena) {
        super(dni, nombres, apellidos, usuario, contrasena);
    }

    @Override
    public String obtenerRol() {
        return "ASESOR";
    }
}
