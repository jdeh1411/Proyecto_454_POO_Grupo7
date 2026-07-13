import java.time.LocalDate;

/**
 * Cronograma de pagos de una venta en cuotas directas.
 * Contiene un arreglo de cuotas (composición).
 */
public class CronogramaPago {

    private double cuotaInicial;
    private double montoPorCuota;
    private CuotaPago[] cuotas;
    private int cantidadCuotas;

    public CronogramaPago(double cuotaInicial, int numeroCuotas,
            double montoPorCuota, LocalDate primerVencimiento) {
        this.cuotaInicial = cuotaInicial;
        this.montoPorCuota = montoPorCuota;
        this.cuotas = new CuotaPago[numeroCuotas];
        this.cantidadCuotas = 0;
        for (int i = 0; i < numeroCuotas; i++) {
            cuotas[cantidadCuotas] = new CuotaPago(i + 1, montoPorCuota,
                    primerVencimiento.plusMonths(i));
            cantidadCuotas++;
        }
    }

    public double getCuotaInicial() {
        return cuotaInicial;
    }

    public double getMontoPorCuota() {
        return montoPorCuota;
    }

    public int getCantidadCuotas() {
        return cantidadCuotas;
    }

    public CuotaPago buscarCuota(int numero) {
        for (int i = 0; i < cantidadCuotas; i++) {
            if (cuotas[i].getNumero() == numero) {
                return cuotas[i];
            }
        }
        return null;
    }

    /**
     * Suma de todo lo pagado en las cuotas (sin incluir la cuota inicial).
     */
    public double calcularTotalPagado() {
        double total = 0;
        for (int i = 0; i < cantidadCuotas; i++) {
            total = total + cuotas[i].getMontoPagado();
        }
        return total;
    }

    /**
     * Saldo pendiente de todas las cuotas.
     */
    public double calcularSaldoPendiente() {
        double saldo = 0;
        for (int i = 0; i < cantidadCuotas; i++) {
            saldo = saldo + cuotas[i].getSaldo();
        }
        return saldo;
    }

    public String mostrarCronograma() {
        String texto = "----------------------------------------\n";
        texto += "CRONOGRAMA DE PAGOS\n";
        texto += "Cuota inicial: S/ " + String.format("%.2f", cuotaInicial) + "\n";
        texto += "Monto por cuota: S/ " + String.format("%.2f", montoPorCuota) + "\n";
        texto += "----------------------------------------\n";
        for (int i = 0; i < cantidadCuotas; i++) {
            texto += cuotas[i].mostrarInformacion() + "\n";
        }
        texto += "----------------------------------------\n";
        texto += "Saldo pendiente del cronograma: S/ "
                + String.format("%.2f", calcularSaldoPendiente());
        return texto;
    }
}
