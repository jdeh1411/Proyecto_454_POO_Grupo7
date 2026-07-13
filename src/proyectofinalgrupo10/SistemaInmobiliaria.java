import java.time.LocalDate;

/**
 * Clase central del sistema. Administra todos los arreglos de objetos
 * con sus contadores, la autenticación y los reportes.
 * Los datos viven únicamente en memoria.
 */
public class SistemaInmobiliaria {

    private Empleado[] empleados;
    private int cantidadEmpleados;

    private Proyecto[] proyectos;
    private int cantidadProyectos;

    private ClientePotencial[] clientes;
    private int cantidadClientes;

    private AcabadoOpcional[] acabados;
    private int cantidadAcabados;

    private Reserva[] reservas;
    private int cantidadReservas;

    private Venta[] ventas;
    private int cantidadVentas;

    public SistemaInmobiliaria(int maxEmpleados, int maxProyectos,
            int maxClientes, int maxAcabados, int maxReservas, int maxVentas) {
        empleados = new Empleado[maxEmpleados];
        cantidadEmpleados = 0;
        proyectos = new Proyecto[maxProyectos];
        cantidadProyectos = 0;
        clientes = new ClientePotencial[maxClientes];
        cantidadClientes = 0;
        acabados = new AcabadoOpcional[maxAcabados];
        cantidadAcabados = 0;
        reservas = new Reserva[maxReservas];
        cantidadReservas = 0;
        ventas = new Venta[maxVentas];
        cantidadVentas = 0;
    }

    // ==================================================
    // AUTENTICACIÓN
    // ==================================================

    /**
     * Recorre manualmente el arreglo de empleados y retorna
     * el empleado cuyas credenciales coinciden, o null.
     */
    public Empleado autenticar(String usuario, String contrasena) {
        for (int i = 0; i < cantidadEmpleados; i++) {
            if (empleados[i].validarCredenciales(usuario, contrasena)) {
                return empleados[i];
            }
        }
        return null;
    }

    // ==================================================
    // EMPLEADOS
    // ==================================================

    public boolean empleadosLlenos() {
        return cantidadEmpleados >= empleados.length;
    }

    public boolean registrarEmpleado(Empleado empleado) {
        if (empleado == null || empleadosLlenos()) {
            return false;
        }
        if (buscarEmpleado(empleado.getDni()) != null) {
            return false;
        }
        if (buscarEmpleadoPorUsuario(empleado.getUsuario()) != null) {
            return false;
        }
        empleados[cantidadEmpleados] = empleado;
        cantidadEmpleados++;
        return true;
    }

    public Empleado buscarEmpleado(String dni) {
        for (int i = 0; i < cantidadEmpleados; i++) {
            if (empleados[i].getDni().equals(dni)) {
                return empleados[i];
            }
        }
        return null;
    }

    public Empleado buscarEmpleadoPorUsuario(String usuario) {
        for (int i = 0; i < cantidadEmpleados; i++) {
            if (empleados[i].getUsuario().equals(usuario)) {
                return empleados[i];
            }
        }
        return null;
    }

    public boolean eliminarEmpleado(String dni) {
        for (int i = 0; i < cantidadEmpleados; i++) {
            if (empleados[i].getDni().equals(dni)) {
                for (int j = i; j < cantidadEmpleados - 1; j++) {
                    empleados[j] = empleados[j + 1];
                }
                empleados[cantidadEmpleados - 1] = null;
                cantidadEmpleados--;
                return true;
            }
        }
        return false;
    }

    public int getCantidadEmpleados() {
        return cantidadEmpleados;
    }

    public Empleado obtenerEmpleado(int indice) {
        if (indice < 0 || indice >= cantidadEmpleados) {
            return null;
        }
        return empleados[indice];
    }

    // ==================================================
    // CLIENTES
    // ==================================================

    public boolean clientesLlenos() {
        return cantidadClientes >= clientes.length;
    }

    public boolean registrarCliente(ClientePotencial cliente) {
        if (cliente == null || clientesLlenos()) {
            return false;
        }
        if (buscarCliente(cliente.getDni()) != null) {
            return false;
        }
        clientes[cantidadClientes] = cliente;
        cantidadClientes++;
        return true;
    }

    public ClientePotencial buscarCliente(String dni) {
        for (int i = 0; i < cantidadClientes; i++) {
            if (clientes[i].getDni().equals(dni)) {
                return clientes[i];
            }
        }
        return null;
    }

    public boolean eliminarCliente(String dni) {
        for (int i = 0; i < cantidadClientes; i++) {
            if (clientes[i].getDni().equals(dni)) {
                for (int j = i; j < cantidadClientes - 1; j++) {
                    clientes[j] = clientes[j + 1];
                }
                clientes[cantidadClientes - 1] = null;
                cantidadClientes--;
                return true;
            }
        }
        return false;
    }

    /**
     * Indica si el cliente tiene reservas o ventas asociadas.
     * Un cliente con operaciones no debe eliminarse.
     */
    public boolean clienteTieneOperaciones(String dni) {
        for (int i = 0; i < cantidadReservas; i++) {
            if (reservas[i].getCliente().getDni().equals(dni)) {
                return true;
            }
        }
        for (int i = 0; i < cantidadVentas; i++) {
            if (ventas[i].getReserva().getCliente().getDni().equals(dni)) {
                return true;
            }
        }
        return false;
    }

    public int getCantidadClientes() {
        return cantidadClientes;
    }

    public ClientePotencial obtenerCliente(int indice) {
        if (indice < 0 || indice >= cantidadClientes) {
            return null;
        }
        return clientes[indice];
    }

    // ==================================================
    // PROYECTOS
    // ==================================================

    public boolean proyectosLlenos() {
        return cantidadProyectos >= proyectos.length;
    }

    public boolean registrarProyecto(Proyecto proyecto) {
        if (proyecto == null || proyectosLlenos()) {
            return false;
        }
        if (buscarProyecto(proyecto.getCodigo()) != null) {
            return false;
        }
        proyectos[cantidadProyectos] = proyecto;
        cantidadProyectos++;
        return true;
    }

    public Proyecto buscarProyecto(String codigo) {
        for (int i = 0; i < cantidadProyectos; i++) {
            if (proyectos[i].getCodigo().equalsIgnoreCase(codigo)) {
                return proyectos[i];
            }
        }
        return null;
    }

    public boolean eliminarProyecto(String codigo) {
        for (int i = 0; i < cantidadProyectos; i++) {
            if (proyectos[i].getCodigo().equalsIgnoreCase(codigo)) {
                for (int j = i; j < cantidadProyectos - 1; j++) {
                    proyectos[j] = proyectos[j + 1];
                }
                proyectos[cantidadProyectos - 1] = null;
                cantidadProyectos--;
                return true;
            }
        }
        return false;
    }

    /**
     * Un proyecto solo puede eliminarse si ninguno de sus
     * departamentos está reservado o vendido.
     */
    public boolean proyectoTieneOperaciones(String codigo) {
        Proyecto proyecto = buscarProyecto(codigo);
        if (proyecto == null) {
            return false;
        }
        for (int i = 0; i < proyecto.getCantidadDepartamentos(); i++) {
            Departamento departamento = proyecto.obtenerDepartamento(i);
            if (!departamento.getEstado().equals("DISPONIBLE")) {
                return true;
            }
        }
        return false;
    }

    public int getCantidadProyectos() {
        return cantidadProyectos;
    }

    public Proyecto obtenerProyecto(int indice) {
        if (indice < 0 || indice >= cantidadProyectos) {
            return null;
        }
        return proyectos[indice];
    }

    // ==================================================
    // DEPARTAMENTOS
    // ==================================================

    /**
     * Busca un departamento en todos los proyectos.
     */
    public Departamento buscarDepartamento(String codigo) {
        for (int i = 0; i < cantidadProyectos; i++) {
            Departamento departamento = proyectos[i].buscarDepartamento(codigo);
            if (departamento != null) {
                return departamento;
            }
        }
        return null;
    }

    /**
     * Retorna el proyecto al que pertenece un departamento.
     */
    public Proyecto buscarProyectoDeDepartamento(String codigoDepartamento) {
        for (int i = 0; i < cantidadProyectos; i++) {
            if (proyectos[i].buscarDepartamento(codigoDepartamento) != null) {
                return proyectos[i];
            }
        }
        return null;
    }

    public boolean registrarDepartamento(String codigoProyecto,
            Departamento departamento) {
        Proyecto proyecto = buscarProyecto(codigoProyecto);
        if (proyecto == null || departamento == null) {
            return false;
        }
        if (buscarDepartamento(departamento.getCodigo()) != null) {
            return false;
        }
        return proyecto.agregarDepartamento(departamento);
    }

    public boolean eliminarDepartamento(String codigoDepartamento) {
        Proyecto proyecto = buscarProyectoDeDepartamento(codigoDepartamento);
        if (proyecto == null) {
            return false;
        }
        return proyecto.eliminarDepartamento(codigoDepartamento);
    }

    public int contarDepartamentos() {
        int total = 0;
        for (int i = 0; i < cantidadProyectos; i++) {
            total = total + proyectos[i].getCantidadDepartamentos();
        }
        return total;
    }

    // ==================================================
    // ACABADOS OPCIONALES
    // ==================================================

    public boolean acabadosLlenos() {
        return cantidadAcabados >= acabados.length;
    }

    public boolean registrarAcabado(AcabadoOpcional acabado) {
        if (acabado == null || acabadosLlenos()) {
            return false;
        }
        if (buscarAcabado(acabado.getCodigo()) != null) {
            return false;
        }
        acabados[cantidadAcabados] = acabado;
        cantidadAcabados++;
        return true;
    }

    public AcabadoOpcional buscarAcabado(String codigo) {
        for (int i = 0; i < cantidadAcabados; i++) {
            if (acabados[i].getCodigo().equalsIgnoreCase(codigo)) {
                return acabados[i];
            }
        }
        return null;
    }

    public boolean eliminarAcabado(String codigo) {
        for (int i = 0; i < cantidadAcabados; i++) {
            if (acabados[i].getCodigo().equalsIgnoreCase(codigo)) {
                for (int j = i; j < cantidadAcabados - 1; j++) {
                    acabados[j] = acabados[j + 1];
                }
                acabados[cantidadAcabados - 1] = null;
                cantidadAcabados--;
                return true;
            }
        }
        return false;
    }

    public int getCantidadAcabados() {
        return cantidadAcabados;
    }

    public AcabadoOpcional obtenerAcabado(int indice) {
        if (indice < 0 || indice >= cantidadAcabados) {
            return null;
        }
        return acabados[indice];
    }

    // ==================================================
    // RESERVAS
    // ==================================================

    public boolean reservasLlenas() {
        return cantidadReservas >= reservas.length;
    }

    /**
     * Revisa todas las reservas y marca como vencidas
     * las que pasaron su fecha de vigencia.
     */
    public void actualizarReservas() {
        for (int i = 0; i < cantidadReservas; i++) {
            reservas[i].actualizarEstado();
        }
    }

    public boolean registrarReserva(Reserva reserva) {
        if (reserva == null || reservasLlenas()) {
            return false;
        }
        if (buscarReserva(reserva.getCodigo()) != null) {
            return false;
        }
        if (!reserva.getDepartamento().estaDisponible()) {
            return false;
        }
        reserva.getDepartamento().setEstado("RESERVADO");
        reservas[cantidadReservas] = reserva;
        cantidadReservas++;
        return true;
    }

    public Reserva buscarReserva(String codigo) {
        for (int i = 0; i < cantidadReservas; i++) {
            if (reservas[i].getCodigo().equalsIgnoreCase(codigo)) {
                return reservas[i];
            }
        }
        return null;
    }

    public int getCantidadReservas() {
        return cantidadReservas;
    }

    public Reserva obtenerReserva(int indice) {
        if (indice < 0 || indice >= cantidadReservas) {
            return null;
        }
        return reservas[indice];
    }

    // ==================================================
    // VENTAS
    // ==================================================

    public boolean ventasLlenas() {
        return cantidadVentas >= ventas.length;
    }

    /**
     * Registra la venta: concreta la reserva, marca el departamento
     * como vendido y genera el contrato automáticamente.
     */
    public boolean registrarVenta(Venta venta) {
        if (venta == null || ventasLlenas()) {
            return false;
        }
        if (buscarVenta(venta.getCodigo()) != null) {
            return false;
        }
        if (!venta.getReserva().getEstado().equals("VIGENTE")) {
            return false;
        }
        venta.getReserva().concretar();
        venta.generarContrato();
        ventas[cantidadVentas] = venta;
        cantidadVentas++;
        return true;
    }

    public Venta buscarVenta(String codigo) {
        for (int i = 0; i < cantidadVentas; i++) {
            if (ventas[i].getCodigo().equalsIgnoreCase(codigo)) {
                return ventas[i];
            }
        }
        return null;
    }

    public int getCantidadVentas() {
        return cantidadVentas;
    }

    public Venta obtenerVenta(int indice) {
        if (indice < 0 || indice >= cantidadVentas) {
            return null;
        }
        return ventas[indice];
    }

    // ==================================================
    // REPORTES (para el gerente)
    // ==================================================

    public String reporteDepartamentos() {
        actualizarReservas();
        String reporte = "========================================\n";
        reporte += "REPORTE DE DEPARTAMENTOS\n";
        reporte += "========================================\n";
        if (cantidadProyectos == 0) {
            reporte += "No hay proyectos registrados.";
            return reporte;
        }
        int disponibles = 0;
        int reservados = 0;
        int vendidos = 0;
        for (int i = 0; i < cantidadProyectos; i++) {
            Proyecto proyecto = proyectos[i];
            reporte += "Proyecto: " + proyecto.getNombre()
                    + " (" + proyecto.getCodigo() + ")\n";
            if (proyecto.getCantidadDepartamentos() == 0) {
                reporte += "  Sin departamentos registrados.\n";
            }
            for (int j = 0; j < proyecto.getCantidadDepartamentos(); j++) {
                reporte += "  " + proyecto.obtenerDepartamento(j).mostrarInformacion() + "\n";
            }
            disponibles = disponibles + proyecto.contarPorEstado("DISPONIBLE");
            reservados = reservados + proyecto.contarPorEstado("RESERVADO");
            vendidos = vendidos + proyecto.contarPorEstado("VENDIDO");
        }
        reporte += "----------------------------------------\n";
        reporte += "Total disponibles: " + disponibles
                + " | Total reservados: " + reservados
                + " | Total vendidos: " + vendidos;
        return reporte;
    }

    public String reporteVentas() {
        String reporte = "========================================\n";
        reporte += "REPORTE DE VENTAS\n";
        reporte += "========================================\n";
        if (cantidadVentas == 0) {
            reporte += "No hay ventas registradas.";
            return reporte;
        }
        double total = 0;
        for (int i = 0; i < cantidadVentas; i++) {
            reporte += ventas[i].generarReporte() + "\n";
            total = total + ventas[i].getPrecioTotal();
        }
        reporte += "----------------------------------------\n";
        reporte += "Cantidad de ventas: " + cantidadVentas
                + " | Monto total vendido: S/ " + String.format("%.2f", total);
        return reporte;
    }

    public String reporteIngresos() {
        String reporte = "========================================\n";
        reporte += "REPORTE DE INGRESOS\n";
        reporte += "========================================\n";
        double totalVendido = 0;
        double totalCobrado = 0;
        for (int i = 0; i < cantidadVentas; i++) {
            totalVendido = totalVendido + ventas[i].getPrecioTotal();
            totalCobrado = totalCobrado + ventas[i].calcularMontoPagado();
        }
        double totalSeparaciones = 0;
        for (int i = 0; i < cantidadReservas; i++) {
            if (reservas[i].getEstado().equals("VIGENTE")) {
                totalSeparaciones = totalSeparaciones + reservas[i].getMontoSeparacion();
            }
        }
        reporte += "Monto total vendido: S/ " + String.format("%.2f", totalVendido) + "\n";
        reporte += "Ingresos cobrados por ventas: S/ " + String.format("%.2f", totalCobrado) + "\n";
        reporte += "Saldo por cobrar: S/ " + String.format("%.2f", totalVendido - totalCobrado) + "\n";
        reporte += "Separaciones de reservas vigentes: S/ "
                + String.format("%.2f", totalSeparaciones);
        return reporte;
    }

    public String reporteProyectos() {
        String reporte = "========================================\n";
        reporte += "REPORTE DE PROYECTOS\n";
        reporte += "========================================\n";
        if (cantidadProyectos == 0) {
            reporte += "No hay proyectos registrados.";
            return reporte;
        }
        for (int i = 0; i < cantidadProyectos; i++) {
            reporte += proyectos[i].generarReporte() + "\n";
        }
        return reporte;
    }

    public String reporteVentasPorAsesor() {
        String reporte = "========================================\n";
        reporte += "REPORTE DE VENTAS POR ASESOR\n";
        reporte += "========================================\n";
        boolean hayAsesores = false;
        for (int i = 0; i < cantidadEmpleados; i++) {
            if (empleados[i] instanceof AsesorVenta) {
                hayAsesores = true;
                AsesorVenta asesor = (AsesorVenta) empleados[i];
                int cantidad = 0;
                double monto = 0;
                for (int j = 0; j < cantidadVentas; j++) {
                    if (ventas[j].getAsesor().getDni().equals(asesor.getDni())) {
                        cantidad++;
                        monto = monto + ventas[j].getPrecioTotal();
                    }
                }
                reporte += "Asesor: " + asesor.getNombreCompleto()
                        + " (DNI " + asesor.getDni() + ")"
                        + " | Ventas: " + cantidad
                        + " | Monto total: S/ " + String.format("%.2f", monto) + "\n";
            }
        }
        if (!hayAsesores) {
            reporte += "No hay asesores registrados.";
        }
        return reporte;
    }

    public String reporteVentasPorFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        String reporte = "========================================\n";
        reporte += "REPORTE DE VENTAS POR FECHAS\n";
        reporte += "Desde: " + fechaInicio + "  Hasta: " + fechaFin + "\n";
        reporte += "========================================\n";
        int cantidad = 0;
        double monto = 0;
        for (int i = 0; i < cantidadVentas; i++) {
            LocalDate fecha = ventas[i].getFechaVenta();
            if (!fecha.isBefore(fechaInicio) && !fecha.isAfter(fechaFin)) {
                reporte += ventas[i].generarReporte() + "\n";
                cantidad++;
                monto = monto + ventas[i].getPrecioTotal();
            }
        }
        if (cantidad == 0) {
            reporte += "No hay ventas en el rango de fechas indicado.";
        } else {
            reporte += "----------------------------------------\n";
            reporte += "Ventas en el rango: " + cantidad
                    + " | Monto total: S/ " + String.format("%.2f", monto);
        }
        return reporte;
    }

    // ==================================================
    // DATOS GENERALES (para el administrador)
    // ==================================================

    public String mostrarDatosSistema() {
        actualizarReservas();
        String datos = "========================================\n";
        datos += "DATOS DEL SISTEMA\n";
        datos += "========================================\n";
        datos += "Empleados registrados: " + cantidadEmpleados + "\n";
        datos += "Clientes registrados: " + cantidadClientes + "\n";
        datos += "Proyectos registrados: " + cantidadProyectos + "\n";
        datos += "Departamentos registrados: " + contarDepartamentos() + "\n";
        datos += "Acabados opcionales registrados: " + cantidadAcabados + "\n";
        datos += "Reservas registradas: " + cantidadReservas + "\n";
        datos += "Ventas registradas: " + cantidadVentas;
        return datos;
    }
}
