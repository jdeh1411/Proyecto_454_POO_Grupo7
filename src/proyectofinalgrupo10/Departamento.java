/**
 * Departamento que pertenece a un proyecto inmobiliario (composición).
 * Su estado puede ser: DISPONIBLE, RESERVADO o VENDIDO.
 */
public class Departamento {

    private String codigo;
    private int piso;
    private int numeroDormitorios;
    private double area;
    private double precio;
    private String estado;

    public Departamento(String codigo, int piso, int numeroDormitorios,
            double area, double precio) {
        this.codigo = codigo;
        this.piso = piso;
        this.numeroDormitorios = numeroDormitorios;
        this.area = area;
        this.precio = precio;
        this.estado = "DISPONIBLE";
    }

    public String getCodigo() {
        return codigo;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public int getNumeroDormitorios() {
        return numeroDormitorios;
    }

    public void setNumeroDormitorios(int numeroDormitorios) {
        this.numeroDormitorios = numeroDormitorios;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean estaDisponible() {
        return estado.equals("DISPONIBLE");
    }

    public String mostrarInformacion() {
        return "Código: " + codigo
                + " | Piso: " + piso
                + " | Dormitorios: " + numeroDormitorios
                + " | Área: " + area + " m2"
                + " | Precio: S/ " + String.format("%.2f", precio)
                + " | Estado: " + estado;
    }
}
