import java.time.LocalDate;

/**
 * Reserva de un departamento realizada por un cliente
 * a través de un asesor de venta (agregación y asociación).
 * Estados posibles: VIGENTE, VENCIDA, CANCELADA, CONCRETADA.
 */
public class Reserva implements Reportable {

    private String codigo;
    private ClientePotencial cliente;
    private Departamento departamento;
    private AsesorVenta asesor;
    private double montoSeparacion;
    private LocalDate fechaReserva;
    private LocalDate fechaVigencia;
    private String estado;

    public Reserva(String codigo, ClientePotencial cliente,
            Departamento departamento, AsesorVenta asesor,
            double montoSeparacion, LocalDate fechaVigencia) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.departamento = departamento;
        this.asesor = asesor;
        this.montoSeparacion = montoSeparacion;
        this.fechaReserva = LocalDate.now();
        this.fechaVigencia = fechaVigencia;
        this.estado = "VIGENTE";
    }

    public String getCodigo() {
        return codigo;
    }

    public ClientePotencial getCliente() {
        return cliente;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public AsesorVenta getAsesor() {
        return asesor;
    }

    public double getMontoSeparacion() {
        return montoSeparacion;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public LocalDate getFechaVigencia() {
        return fechaVigencia;
    }

    public String getEstado() {
        return estado;
    }

    /**
     * Indica si la reserva vigente ya pasó su fecha de vigencia.
     */
    public boolean estaVencida() {
        return estado.equals("VIGENTE") && fechaVigencia.isBefore(LocalDate.now());
    }

    /**
     * Si la reserva venció, cambia su estado y libera el departamento.
     */
    public void actualizarEstado() {
        if (estaVencida()) {
            estado = "VENCIDA";
            departamento.setEstado("DISPONIBLE");
        }
    }

    /**
     * Cancela la reserva y libera el departamento.
     */
    public void cancelar() {
        estado = "CANCELADA";
        departamento.setEstado("DISPONIBLE");
    }

    /**
     * Marca la reserva como concretada (se convirtió en venta)
     * y el departamento como vendido.
     */
    public void concretar() {
        estado = "CONCRETADA";
        departamento.setEstado("VENDIDO");
    }

    public String mostrarInformacion() {
        return "Código: " + codigo
                + " | Cliente: " + cliente.getNombreCompleto()
                + " (DNI " + cliente.getDni() + ")"
                + " | Departamento: " + departamento.getCodigo()
                + " | Asesor: " + asesor.getNombreCompleto()
                + " | Separación: S/ " + String.format("%.2f", montoSeparacion)
                + " | Fecha reserva: " + fechaReserva
                + " | Vigencia: " + fechaVigencia
                + " | Estado: " + estado;
    }

    @Override
    public String generarReporte() {
        return mostrarInformacion();
    }
}
