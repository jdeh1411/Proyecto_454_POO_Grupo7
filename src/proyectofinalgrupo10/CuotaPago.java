import java.time.LocalDate;

/**
 * Cuota individual dentro de un cronograma de pagos (composición).
 * Estados posibles: PENDIENTE, PARCIAL, PAGADA.
 */
public class CuotaPago {

    private int numero;
    private double monto;
    private LocalDate fechaVencimiento;
    private double montoPagado;
    private String estado;

    public CuotaPago(int numero, double monto, LocalDate fechaVencimiento) {
        this.numero = numero;
        this.monto = monto;
        this.fechaVencimiento = fechaVencimiento;
        this.montoPagado = 0;
        this.estado = "PENDIENTE";
    }

    public int getNumero() {
        return numero;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public double getMontoPagado() {
        return montoPagado;
    }

    public String getEstado() {
        return estado;
    }

    public double getSaldo() {
        return monto - montoPagado;
    }

    /**
     * Registra un pago sobre la cuota.
     * Retorna un codigo que Main interpreta:
     *  0 = cuota pagada completamente
     *  1 = pago parcial registrado
     * -1 = el monto supera el saldo de la cuota
     * -2 = la cuota ya se encuentra pagada
     * -3 = el monto debe ser mayor a cero
     */
    public int registrarPago(double montoAPagar) {
        if (estado.equals("PAGADA")) {
            return -2;
        }
        if (montoAPagar <= 0) {
            return -3;
        }
        if (montoAPagar > getSaldo() + 0.009) {
            return -1;
        }
        montoPagado = montoPagado + montoAPagar;
        if (getSaldo() <= 0.009) {
            estado = "PAGADA";
            return 0;
        }
        estado = "PARCIAL";
        return 1;
    }

    public String mostrarInformacion() {
        return "Cuota " + numero
                + " | Vence: " + fechaVencimiento
                + " | Monto: S/ " + String.format("%.2f", monto)
                + " | Pagado: S/ " + String.format("%.2f", montoPagado)
                + " | Saldo: S/ " + String.format("%.2f", getSaldo())
                + " | Estado: " + estado;
    }
}
