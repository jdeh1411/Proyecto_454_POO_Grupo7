/**
 * Proyecto inmobiliario que contiene departamentos (composición).
 * Implementa Reportable para generar su propio reporte.
 */
public class Proyecto implements Reportable {

    private String codigo;
    private String nombre;
    private String ubicacion;
    private Departamento[] departamentos;
    private int cantidadDepartamentos;

    public Proyecto(String codigo, String nombre, String ubicacion,
            int maxDepartamentos) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.departamentos = new Departamento[maxDepartamentos];
        this.cantidadDepartamentos = 0;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getCantidadDepartamentos() {
        return cantidadDepartamentos;
    }

    public Departamento obtenerDepartamento(int indice) {
        if (indice < 0 || indice >= cantidadDepartamentos) {
            return null;
        }
        return departamentos[indice];
    }

    /**
     * Agrega un departamento al proyecto si hay capacidad
     * y el código no está repetido dentro del proyecto.
     */
    public boolean agregarDepartamento(Departamento departamento) {
        if (departamento == null) {
            return false;
        }
        if (cantidadDepartamentos >= departamentos.length) {
            return false;
        }
        if (buscarDepartamento(departamento.getCodigo()) != null) {
            return false;
        }
        departamentos[cantidadDepartamentos] = departamento;
        cantidadDepartamentos++;
        return true;
    }

    public Departamento buscarDepartamento(String codigoDepartamento) {
        for (int i = 0; i < cantidadDepartamentos; i++) {
            if (departamentos[i].getCodigo().equalsIgnoreCase(codigoDepartamento)) {
                return departamentos[i];
            }
        }
        return null;
    }

    /**
     * Elimina un departamento desplazando los elementos del arreglo.
     */
    public boolean eliminarDepartamento(String codigoDepartamento) {
        for (int i = 0; i < cantidadDepartamentos; i++) {
            if (departamentos[i].getCodigo().equalsIgnoreCase(codigoDepartamento)) {
                for (int j = i; j < cantidadDepartamentos - 1; j++) {
                    departamentos[j] = departamentos[j + 1];
                }
                departamentos[cantidadDepartamentos - 1] = null;
                cantidadDepartamentos--;
                return true;
            }
        }
        return false;
    }

    public int contarPorEstado(String estado) {
        int contador = 0;
        for (int i = 0; i < cantidadDepartamentos; i++) {
            if (departamentos[i].getEstado().equals(estado)) {
                contador++;
            }
        }
        return contador;
    }

    public String mostrarInformacion() {
        return "Código: " + codigo
                + " | Nombre: " + nombre
                + " | Ubicación: " + ubicacion
                + " | Departamentos registrados: " + cantidadDepartamentos;
    }

    @Override
    public String generarReporte() {
        String reporte = "Proyecto: " + nombre + " (" + codigo + ")"
                + " - Ubicación: " + ubicacion + "\n";
        reporte += "  Total de departamentos: " + cantidadDepartamentos + "\n";
        reporte += "  Disponibles: " + contarPorEstado("DISPONIBLE")
                + " | Reservados: " + contarPorEstado("RESERVADO")
                + " | Vendidos: " + contarPorEstado("VENDIDO");
        return reporte;
    }
}
