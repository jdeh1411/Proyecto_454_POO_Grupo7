/**
 * Acabado opcional que puede agregarse a una venta
 * (piso laminado, cocina amoblada, etc.).
 */
public class AcabadoOpcional {

    private String codigo;
    private String nombre;
    private String descripcion;
    private double precio;

    public AcabadoOpcional(String codigo, String nombre,
            String descripcion, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String mostrarInformacion() {
        return "Codigo: " + codigo
                + " | Nombre: " + nombre
                + " | Descripcion: " + descripcion
                + " | Precio: S/ " + String.format("%.2f", precio);
    }
}
