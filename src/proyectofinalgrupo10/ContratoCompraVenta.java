import java.time.LocalDate;

/**
 * Contrato de compraventa generado automáticamente por una venta
 * (composición: el contrato pertenece a la venta).
 */
public class ContratoCompraVenta {

    private String codigo;
    private Venta venta;
    private LocalDate fechaEmision;

    public ContratoCompraVenta(String codigo, Venta venta) {
        this.codigo = codigo;
        this.venta = venta;
        this.fechaEmision = LocalDate.now();
    }

    public String getCodigo() {
        return codigo;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    /**
     * Genera el texto completo del contrato para mostrarlo en consola.
     */
    public String generarTexto() {
        ClientePotencial cliente = venta.getReserva().getCliente();
        Departamento departamento = venta.getReserva().getDepartamento();
        AsesorVenta asesor = venta.getReserva().getAsesor();

        String texto = "========================================\n";
        texto += "CONTRATO DE COMPRAVENTA N° " + codigo + "\n";
        texto += "========================================\n";
        texto += "Fecha de emision: " + fechaEmision + "\n";
        texto += "----------------------------------------\n";
        texto += "COMPRADOR\n";
        texto += "Nombre: " + cliente.getNombreCompleto() + "\n";
        texto += "DNI: " + cliente.getDni() + "\n";
        texto += "----------------------------------------\n";
        texto += "INMUEBLE\n";
        texto += "Departamento: " + departamento.getCodigo() + "\n";
        texto += "Piso: " + departamento.getPiso()
                + " | Dormitorios: " + departamento.getNumeroDormitorios()
                + " | Area: " + departamento.getArea() + " m2\n";
        texto += "Precio base: S/ "
                + String.format("%.2f", departamento.getPrecio()) + "\n";
        texto += "----------------------------------------\n";
        texto += "ACABADOS OPCIONALES\n";
        if (venta.getCantidadAcabados() == 0) {
            texto += "Sin acabados opcionales.\n";
        } else {
            for (int i = 0; i < venta.getCantidadAcabados(); i++) {
                AcabadoOpcional acabado = venta.obtenerAcabado(i);
                texto += "- " + acabado.getNombre() + ": S/ "
                        + String.format("%.2f", acabado.getPrecio()) + "\n";
            }
        }
        texto += "----------------------------------------\n";
        texto += "CONDICIONES DE PAGO\n";
        texto += "Modalidad: " + venta.getModalidad() + "\n";
        texto += "Precio total: S/ "
                + String.format("%.2f", venta.getPrecioTotal()) + "\n";
        if (venta.getCronograma() != null) {
            texto += "Cuota inicial: S/ "
                    + String.format("%.2f", venta.getCronograma().getCuotaInicial()) + "\n";
            texto += "Número de cuotas: "
                    + venta.getCronograma().getCantidadCuotas() + "\n";
            texto += "Monto por cuota: S/ "
                    + String.format("%.2f", venta.getCronograma().getMontoPorCuota()) + "\n";
        }
        texto += "----------------------------------------\n";
        texto += "Asesor responsable: " + asesor.getNombreCompleto() + "\n";
        texto += "Fecha de venta: " + venta.getFechaVenta() + "\n";
        texto += "========================================";
        return texto;
    }
}
