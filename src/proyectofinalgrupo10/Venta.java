import java.time.LocalDate;

/**
 * Venta de un departamento a partir de una reserva (asociación).
 * Contiene los acabados opcionales seleccionados, el cronograma
 * de pagos (cuando corresponde) y el contrato (composición).
 * Modalidades: CONTADO, FINANCIAMIENTO BANCARIO, CUOTAS DIRECTAS.
 */
public class Venta implements Reportable {

    private String codigo;
    private Reserva reserva;
    private String modalidad;
    private AcabadoOpcional[] acabadosSeleccionados;
    private int cantidadAcabados;
    private double precioTotal;
    private LocalDate fechaVenta;
    private CronogramaPago cronograma;
    private ContratoCompraVenta contrato;

    public Venta(String codigo, Reserva reserva, String modalidad,
            int maxAcabados) {
        this.codigo = codigo;
        this.reserva = reserva;
        this.modalidad = modalidad;
        this.acabadosSeleccionados = new AcabadoOpcional[maxAcabados];
        this.cantidadAcabados = 0;
        this.precioTotal = reserva.getDepartamento().getPrecio();
        this.fechaVenta = LocalDate.now();
        this.cronograma = null;
        this.contrato = null;
    }

    public String getCodigo() {
        return codigo;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public String getModalidad() {
        return modalidad;
    }

    public int getCantidadAcabados() {
        return cantidadAcabados;
    }

    public AcabadoOpcional obtenerAcabado(int indice) {
        if (indice < 0 || indice >= cantidadAcabados) {
            return null;
        }
        return acabadosSeleccionados[indice];
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public CronogramaPago getCronograma() {
        return cronograma;
    }

    public ContratoCompraVenta getContrato() {
        return contrato;
    }

    public AsesorVenta getAsesor() {
        return reserva.getAsesor();
    }

    /**
     * Agrega un acabado opcional y actualiza el precio total.
     */
    public boolean agregarAcabado(AcabadoOpcional acabado) {
        if (acabado == null) {
            return false;
        }
        if (cantidadAcabados >= acabadosSeleccionados.length) {
            return false;
        }
        acabadosSeleccionados[cantidadAcabados] = acabado;
        cantidadAcabados++;
        precioTotal = precioTotal + acabado.getPrecio();
        return true;
    }

    /**
     * Genera el cronograma de pagos para la modalidad de cuotas directas.
     * El monto por cuota se calcula a partir del saldo restante.
     */
    public void generarCronograma(double cuotaInicial, int numeroCuotas,
            LocalDate primerVencimiento) {
        double montoPorCuota = (precioTotal - cuotaInicial) / numeroCuotas;
        cronograma = new CronogramaPago(cuotaInicial, numeroCuotas,
                montoPorCuota, primerVencimiento);
    }

    /**
     * Genera automáticamente el contrato de la venta.
     */
    public void generarContrato() {
        contrato = new ContratoCompraVenta("CON-" + codigo, this);
    }

    /**
     * Monto ya cobrado de la venta.
     * Al contado y con financiamiento bancario se considera pagada.
     * En cuotas directas: cuota inicial + pagos de las cuotas.
     */
    public double calcularMontoPagado() {
        if (modalidad.equals("CUOTAS DIRECTAS")) {
            if (cronograma == null) {
                return 0;
            }
            return cronograma.getCuotaInicial() + cronograma.calcularTotalPagado();
        }
        return precioTotal;
    }

    public double calcularSaldoPendiente() {
        return precioTotal - calcularMontoPagado();
    }

    public String mostrarInformacion() {
        return "Código: " + codigo
                + " | Fecha: " + fechaVenta
                + " | Cliente: " + reserva.getCliente().getNombreCompleto()
                + " | Departamento: " + reserva.getDepartamento().getCodigo()
                + " | Modalidad: " + modalidad
                + " | Precio total: S/ " + String.format("%.2f", precioTotal)
                + " | Pagado: S/ " + String.format("%.2f", calcularMontoPagado())
                + " | Saldo: S/ " + String.format("%.2f", calcularSaldoPendiente());
    }

    @Override
    public String generarReporte() {
        return mostrarInformacion();
    }
}
