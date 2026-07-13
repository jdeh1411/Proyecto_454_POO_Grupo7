import java.util.Scanner;
import java.time.LocalDate;


public class Main {

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        SistemaInmobiliaria sistema =
                new SistemaInmobiliaria(100, 50, 100, 100, 100, 50);
        inicializarDatos(sistema);
        boolean ejecutando = true;
        while (ejecutando) {
            mostrarMenuPrincipal();
            String opcion = entrada.nextLine().trim();
            switch (opcion) {
                case "1":
                    iniciarSesion(sistema, entrada);
                    break;
                case "2":
                    ejecutando = false;
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
        entrada.close();
        System.out.println("Sistema finalizado.");
    }

    private static void inicializarDatos(SistemaInmobiliaria sistema) {
        sistema.registrarEmpleado(new Administrador("00000000",
                "Administrador", "General", "admin", "admin123"));
        sistema.registrarEmpleado(new AsesorVenta("11111111",
                "Asesor", "Principal", "asesor", "asesor123"));
        sistema.registrarEmpleado(new Gerente("22222222",
                "Gerente", "General", "gerente", "gerente123"));
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("========================================");
        System.out.println("SISTEMA DE GESTION INMOBILIARIA");
        System.out.println("========================================");
        System.out.println("1. Iniciar sesion");
        System.out.println("2. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    private static void iniciarSesion(SistemaInmobiliaria sistema, Scanner entrada) {
        String usuario = leerTexto(entrada, "Usuario: ");
        String contrasena = leerTexto(entrada, "Contrasena: ");
        Empleado empleado = sistema.autenticar(usuario, contrasena);
        if (empleado == null) {
            System.out.println("Usuario o contrasena incorrectos.");
            return;
        }
        System.out.println("Bienvenido(a), " + empleado.getNombreCompleto()
                + " (" + empleado.obtenerRol() + ")");
        if (empleado instanceof Administrador) {
            menuAdministrador(sistema, entrada);
        } else if (empleado instanceof AsesorVenta) {
            menuAsesor(sistema, entrada, (AsesorVenta) empleado);
        } else if (empleado instanceof Gerente) {
            menuGerente(sistema, entrada);
        }
    }

    private static String leerTexto(Scanner entrada, String mensaje) {
        System.out.print(mensaje);
        return entrada.nextLine().trim();
    }

    private static int leerEntero(Scanner entrada, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(entrada.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Numero entero invalido. Intente nuevamente.");
            }
        }
    }

    private static double leerDecimal(Scanner entrada, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(entrada.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Numero decimal invalido. Intente nuevamente.");
            }
        }
    }

    private static LocalDate leerFecha(Scanner entrada, String mensaje) {
        while (true) {
            System.out.print(mensaje + " (AAAA-MM-DD): ");
            try {
                return LocalDate.parse(entrada.nextLine().trim());
            } catch (Exception ex) {
                System.out.println("Fecha con formato incorrecto. Ejemplo: 2026-07-15");
            }
        }
    }


    private static void menuAdministrador(SistemaInmobiliaria sistema, Scanner entrada) {
        boolean cerrarSesion = false;
        while (!cerrarSesion) {
            System.out.println("========================================");
            System.out.println("MENU DEL ADMINISTRADOR");
            System.out.println("========================================");
            System.out.println("1. Gestionar empleados");
            System.out.println("2. Gestionar proyectos");
            System.out.println("3. Gestionar departamentos");
            System.out.println("4. Gestionar acabados opcionales");
            System.out.println("5. Mostrar datos del sistema");
            System.out.println("6. Cerrar sesion");
            System.out.print("Seleccione una opcion: ");
            String opcion = entrada.nextLine().trim();
            switch (opcion) {
                case "1":
                    gestionarEmpleados(sistema, entrada);
                    break;
                case "2":
                    gestionarProyectos(sistema, entrada);
                    break;
                case "3":
                    gestionarDepartamentos(sistema, entrada);
                    break;
                case "4":
                    gestionarAcabados(sistema, entrada);
                    break;
                case "5":
                    System.out.println(sistema.mostrarDatosSistema());
                    break;
                case "6":
                    cerrarSesion = true;
                    System.out.println("Sesion cerrada.");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

  private static void gestionarEmpleados(SistemaInmobiliaria sistema, Scanner entrada) {
        boolean regresar = false;
        while (!regresar) {
            System.out.println("----------------------------------------");
            System.out.println("GESTION DE EMPLEADOS");
            System.out.println("----------------------------------------");
            System.out.println("1. Registrar empleado");
            System.out.println("2. Buscar empleado");
            System.out.println("3. Modificar empleado");
            System.out.println("4. Eliminar empleado");
            System.out.println("5. Listar empleados");
            System.out.println("6. Volver");
            System.out.print("Seleccione una opcion: ");
            String opcion = entrada.nextLine().trim();
            switch (opcion) {
                case "1":
                    registrarEmpleado(sistema, entrada);
                    break;
                case "2":
                    buscarEmpleado(sistema, entrada);
                    break;
                case "3":
                    modificarEmpleado(sistema, entrada);
                    break;
                case "4":
                    eliminarEmpleado(sistema, entrada);
                    break;
                case "5":
                    listarEmpleados(sistema);
                    break;
                case "6":
                    regresar = true;
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    private static void registrarEmpleado(SistemaInmobiliaria sistema, Scanner entrada) {
        if (sistema.empleadosLlenos()) {
            System.out.println("Capacidad maxima alcanzada.");
            return;
        }
        String dni = leerTexto(entrada, "DNI: ");
        if (sistema.buscarEmpleado(dni) != null) {
            System.out.println("No se pudo registrar: el DNI ya existe.");
            return;
        }
        String nombres = leerTexto(entrada, "Nombres: ");
        String apellidos = leerTexto(entrada, "Apellidos: ");
        String usuario = leerTexto(entrada, "Usuario: ");
        if (sistema.buscarEmpleadoPorUsuario(usuario) != null) {
            System.out.println("No se pudo registrar: el usuario ya existe.");
            return;
        }
        String contrasena = leerTexto(entrada, "Contrasena: ");
        System.out.println("Roles disponibles:");
        System.out.println("1. Administrador");
        System.out.println("2. Asesor de venta");
        System.out.println("3. Gerente");
        String rol = leerTexto(entrada, "Rol (1-3): ");
        Empleado nuevoEmpleado;
        switch (rol) {
            case "1":
                nuevoEmpleado = new Administrador(dni, nombres, apellidos, usuario, contrasena);
                break;
            case "2":
                nuevoEmpleado = new AsesorVenta(dni, nombres, apellidos, usuario, contrasena);
                break;
            case "3":
                nuevoEmpleado = new Gerente(dni, nombres, apellidos, usuario, contrasena);
                break;
            default:
                System.out.println("Rol invalido. No se registro el empleado.");
                return;
        }
        if (sistema.registrarEmpleado(nuevoEmpleado)) {
            System.out.println("Empleado registrado correctamente.");
        } else {
            System.out.println("No se pudo registrar el empleado.");
        }
    }

    private static void buscarEmpleado(SistemaInmobiliaria sistema, Scanner entrada) {
        String dni = leerTexto(entrada, "DNI del empleado: ");
        Empleado empleado = sistema.buscarEmpleado(dni);
        if (empleado == null) {
            System.out.println("No se encontro el empleado.");
        } else {
            System.out.println(empleado.mostrarInformacion());
        }
    }

    private static void modificarEmpleado(SistemaInmobiliaria sistema, Scanner entrada) {
        String dni = leerTexto(entrada, "DNI del empleado a modificar: ");
        Empleado empleado = sistema.buscarEmpleado(dni);
        if (empleado == null) {
            System.out.println("No se encontro el empleado.");
            return;
        }
        System.out.println(empleado.mostrarInformacion());
        System.out.println("Deje en blanco los campos que no desea cambiar.");
        String nombres = leerTexto(entrada, "Nuevos nombres: ");
        if (!nombres.isEmpty()) {
            empleado.setNombres(nombres);
        }
        String apellidos = leerTexto(entrada, "Nuevos apellidos: ");
        if (!apellidos.isEmpty()) {
            empleado.setApellidos(apellidos);
        }
        String usuario = leerTexto(entrada, "Nuevo usuario: ");
        if (!usuario.isEmpty()) {
            Empleado existente = sistema.buscarEmpleadoPorUsuario(usuario);
            if (existente != null && existente != empleado) {
                System.out.println("El usuario ya existe. No se cambio el usuario.");
            } else {
                empleado.setUsuario(usuario);
            }
        }
        String contrasena = leerTexto(entrada, "Nueva contrasena: ");
        if (!contrasena.isEmpty()) {
            empleado.setContrasena(contrasena);
        }
        System.out.println("Empleado modificado correctamente.");
    }

    private static void eliminarEmpleado(SistemaInmobiliaria sistema, Scanner entrada) {
        String dni = leerTexto(entrada, "DNI del empleado a eliminar: ");
        if (sistema.eliminarEmpleado(dni)) {
            System.out.println("Empleado eliminado correctamente.");
        } else {
            System.out.println("No se encontro el empleado.");
        }
    }

    private static void listarEmpleados(SistemaInmobiliaria sistema) {
        if (sistema.getCantidadEmpleados() == 0) {
            System.out.println("No hay empleados registrados.");
            return;
        }
        System.out.println("LISTA DE EMPLEADOS:");
        for (int i = 0; i < sistema.getCantidadEmpleados(); i++) {
            System.out.println(sistema.obtenerEmpleado(i).mostrarInformacion());
        }
    }


    private static void gestionarProyectos(SistemaInmobiliaria sistema, Scanner entrada) {
        boolean regresar = false;
        while (!regresar) {
            System.out.println("----------------------------------------");
            System.out.println("GESTION DE PROYECTOS");
            System.out.println("----------------------------------------");
            System.out.println("1. Registrar proyecto");
            System.out.println("2. Buscar proyecto");
            System.out.println("3. Modificar proyecto");
            System.out.println("4. Eliminar proyecto");
            System.out.println("5. Listar proyectos");
            System.out.println("6. Volver");
            System.out.print("Seleccione una opcion: ");
            String opcion = entrada.nextLine().trim();
            switch (opcion) {
                case "1":
                    registrarProyecto(sistema, entrada);
                    break;
                case "2":
                    buscarProyecto(sistema, entrada);
                    break;
                case "3":
                    modificarProyecto(sistema, entrada);
                    break;
                case "4":
                    eliminarProyecto(sistema, entrada);
                    break;
                case "5":
                    listarProyectos(sistema);
                    break;
                case "6":
                    regresar = true;
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    private static void registrarProyecto(SistemaInmobiliaria sistema, Scanner entrada) {
        if (sistema.proyectosLlenos()) {
            System.out.println("Capacidad maxima alcanzada.");
            return;
        }
        String codigo = leerTexto(entrada, "Codigo del proyecto: ");
        if (sistema.buscarProyecto(codigo) != null) {
            System.out.println("No se pudo registrar: el codigo ya existe.");
            return;
        }
        String nombre = leerTexto(entrada, "Nombre del proyecto: ");
        String ubicacion = leerTexto(entrada, "Ubicacion: ");
        Proyecto proyecto = new Proyecto(codigo, nombre, ubicacion, 100);
        if (sistema.registrarProyecto(proyecto)) {
            System.out.println("Proyecto registrado correctamente.");
        } else {
            System.out.println("No se pudo registrar el proyecto.");
        }
    }

    private static void buscarProyecto(SistemaInmobiliaria sistema, Scanner entrada) {
        String codigo = leerTexto(entrada, "Codigo del proyecto: ");
        Proyecto proyecto = sistema.buscarProyecto(codigo);
        if (proyecto == null) {
            System.out.println("No se encontro el proyecto.");
        } else {
            System.out.println(proyecto.mostrarInformacion());
        }
    }

    private static void modificarProyecto(SistemaInmobiliaria sistema, Scanner entrada) {
        String codigo = leerTexto(entrada, "Codigo del proyecto a modificar: ");
        Proyecto proyecto = sistema.buscarProyecto(codigo);
        if (proyecto == null) {
            System.out.println("No se encontro el proyecto.");
            return;
        }
        System.out.println(proyecto.mostrarInformacion());
        System.out.println("Deje en blanco los campos que no desea cambiar.");
        String nombre = leerTexto(entrada, "Nuevo nombre: ");
        if (!nombre.isEmpty()) {
            proyecto.setNombre(nombre);
        }
        String ubicacion = leerTexto(entrada, "Nueva ubicacion: ");
        if (!ubicacion.isEmpty()) {
            proyecto.setUbicacion(ubicacion);
        }
        System.out.println("Proyecto modificado correctamente.");
    }

    private static void eliminarProyecto(SistemaInmobiliaria sistema, Scanner entrada) {
        String codigo = leerTexto(entrada, "Codigo del proyecto a eliminar: ");
        Proyecto proyecto = sistema.buscarProyecto(codigo);
        if (proyecto == null) {
            System.out.println("No se encontro el proyecto.");
            return;
        }
        if (sistema.proyectoTieneOperaciones(codigo)) {
            System.out.println("No se puede eliminar: el proyecto tiene "
                    + "departamentos reservados o vendidos.");
            return;
        }
        if (sistema.eliminarProyecto(codigo)) {
            System.out.println("Proyecto eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el proyecto.");
        }
    }

    private static void listarProyectos(SistemaInmobiliaria sistema) {
        if (sistema.getCantidadProyectos() == 0) {
            System.out.println("No hay proyectos registrados.");
            return;
        }
        System.out.println("LISTA DE PROYECTOS:");
        for (int i = 0; i < sistema.getCantidadProyectos(); i++) {
            System.out.println(sistema.obtenerProyecto(i).mostrarInformacion());
        }
    }

  
    private static void gestionarDepartamentos(SistemaInmobiliaria sistema, Scanner entrada) {
        boolean regresar = false;
        while (!regresar) {
            System.out.println("----------------------------------------");
            System.out.println("GESTION DE DEPARTAMENTOS");
            System.out.println("----------------------------------------");
            System.out.println("1. Registrar departamento en un proyecto");
            System.out.println("2. Buscar departamento");
            System.out.println("3. Modificar departamento");
            System.out.println("4. Eliminar departamento");
            System.out.println("5. Listar departamentos de un proyecto");
            System.out.println("6. Volver");
            System.out.print("Seleccione una opcion: ");
            String opcion = entrada.nextLine().trim();
            switch (opcion) {
                case "1":
                    registrarDepartamento(sistema, entrada);
                    break;
                case "2":
                    buscarDepartamento(sistema, entrada);
                    break;
                case "3":
                    modificarDepartamento(sistema, entrada);
                    break;
                case "4":
                    eliminarDepartamento(sistema, entrada);
                    break;
                case "5":
                    listarDepartamentos(sistema, entrada);
                    break;
                case "6":
                    regresar = true;
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    private static void registrarDepartamento(SistemaInmobiliaria sistema, Scanner entrada) {
        String codigoProyecto = leerTexto(entrada, "Codigo del proyecto: ");
        Proyecto proyecto = sistema.buscarProyecto(codigoProyecto);
        if (proyecto == null) {
            System.out.println("No se encontro el proyecto.");
            return;
        }
        String codigo = leerTexto(entrada, "Codigo del departamento: ");
        if (sistema.buscarDepartamento(codigo) != null) {
            System.out.println("No se pudo registrar: el codigo ya existe.");
            return;
        }
        int piso = leerEntero(entrada, "Piso: ");
        int dormitorios = leerEntero(entrada, "Numero de dormitorios: ");
        double area = leerDecimal(entrada, "Area (m2): ");
        double precio = leerDecimal(entrada, "Precio (S/): ");
        if (precio <= 0) {
            System.out.println("El precio debe ser mayor a cero.");
            return;
        }
        Departamento departamento = new Departamento(codigo, piso, dormitorios, area, precio);
        if (sistema.registrarDepartamento(codigoProyecto, departamento)) {
            System.out.println("Departamento registrado correctamente.");
        } else {
            System.out.println("Capacidad maxima alcanzada.");
        }
    }

    private static void buscarDepartamento(SistemaInmobiliaria sistema, Scanner entrada) {
        String codigo = leerTexto(entrada, "Codigo del departamento: ");
        Departamento departamento = sistema.buscarDepartamento(codigo);
        if (departamento == null) {
            System.out.println("No se encontro el departamento.");
            return;
        }
        Proyecto proyecto = sistema.buscarProyectoDeDepartamento(codigo);
        System.out.println(departamento.mostrarInformacion()
                + " | Proyecto: " + proyecto.getNombre());
    }

    private static void modificarDepartamento(SistemaInmobiliaria sistema, Scanner entrada) {
        String codigo = leerTexto(entrada, "Codigo del departamento a modificar: ");
        Departamento departamento = sistema.buscarDepartamento(codigo);
        if (departamento == null) {
            System.out.println("No se encontro el departamento.");
            return;
        }
        if (departamento.getEstado().equals("VENDIDO")) {
            System.out.println("No se puede modificar un departamento vendido.");
            return;
        }
        System.out.println(departamento.mostrarInformacion());
        int piso = leerEntero(entrada, "Nuevo piso: ");
        int dormitorios = leerEntero(entrada, "Nuevo numero de dormitorios: ");
        double area = leerDecimal(entrada, "Nueva area (m2): ");
        double precio = leerDecimal(entrada, "Nuevo precio (S/): ");
        if (precio <= 0) {
            System.out.println("El precio debe ser mayor a cero. No se modifico.");
            return;
        }
        departamento.setPiso(piso);
        departamento.setNumeroDormitorios(dormitorios);
        departamento.setArea(area);
        departamento.setPrecio(precio);
        System.out.println("Departamento modificado correctamente.");
    }

    private static void eliminarDepartamento(SistemaInmobiliaria sistema, Scanner entrada) {
        String codigo = leerTexto(entrada, "Codigo del departamento a eliminar: ");
        Departamento departamento = sistema.buscarDepartamento(codigo);
        if (departamento == null) {
            System.out.println("No se encontro el departamento.");
            return;
        }
        if (!departamento.estaDisponible()) {
            System.out.println("No se puede eliminar: el departamento esta "
                    + departamento.getEstado().toLowerCase() + ".");
            return;
        }
        if (sistema.eliminarDepartamento(codigo)) {
            System.out.println("Departamento eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el departamento.");
        }
    }

    private static void listarDepartamentos(SistemaInmobiliaria sistema, Scanner entrada) {
        String codigoProyecto = leerTexto(entrada, "Codigo del proyecto: ");
        Proyecto proyecto = sistema.buscarProyecto(codigoProyecto);
        if (proyecto == null) {
            System.out.println("No se encontro el proyecto.");
            return;
        }
        if (proyecto.getCantidadDepartamentos() == 0) {
            System.out.println("El proyecto no tiene departamentos registrados.");
            return;
        }
        System.out.println("DEPARTAMENTOS DEL PROYECTO " + proyecto.getNombre() + ":");
        for (int i = 0; i < proyecto.getCantidadDepartamentos(); i++) {
            System.out.println(proyecto.obtenerDepartamento(i).mostrarInformacion());
        }
    }

  
    private static void gestionarAcabados(SistemaInmobiliaria sistema, Scanner entrada) {
        boolean regresar = false;
        while (!regresar) {
            System.out.println("----------------------------------------");
            System.out.println("GESTION DE ACABADOS OPCIONALES");
            System.out.println("----------------------------------------");
            System.out.println("1. Registrar acabado");
            System.out.println("2. Buscar acabado");
            System.out.println("3. Modificar acabado");
            System.out.println("4. Eliminar acabado");
            System.out.println("5. Listar acabados");
            System.out.println("6. Volver");
            System.out.print("Seleccione una opcion: ");
            String opcion = entrada.nextLine().trim();
            switch (opcion) {
                case "1":
                    registrarAcabado(sistema, entrada);
                    break;
                case "2":
                    buscarAcabado(sistema, entrada);
                    break;
                case "3":
                    modificarAcabado(sistema, entrada);
                    break;
                case "4":
                    eliminarAcabado(sistema, entrada);
                    break;
                case "5":
                    listarAcabados(sistema);
                    break;
                case "6":
                    regresar = true;
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    private static void registrarAcabado(SistemaInmobiliaria sistema, Scanner entrada) {
        if (sistema.acabadosLlenos()) {
            System.out.println("Capacidad maxima alcanzada.");
            return;
        }
        String codigo = leerTexto(entrada, "Codigo del acabado: ");
        if (sistema.buscarAcabado(codigo) != null) {
            System.out.println("No se pudo registrar: el codigo ya existe.");
            return;
        }
        String nombre = leerTexto(entrada, "Nombre: ");
        String descripcion = leerTexto(entrada, "Descripcion: ");
        double precio = leerDecimal(entrada, "Precio (S/): ");
        if (precio <= 0) {
            System.out.println("El precio debe ser mayor a cero.");
            return;
        }
        AcabadoOpcional acabado = new AcabadoOpcional(codigo, nombre, descripcion, precio);
        if (sistema.registrarAcabado(acabado)) {
            System.out.println("Acabado registrado correctamente.");
        } else {
            System.out.println("No se pudo registrar el acabado.");
        }
    }

    private static void buscarAcabado(SistemaInmobiliaria sistema, Scanner entrada) {
        String codigo = leerTexto(entrada, "Codigo del acabado: ");
        AcabadoOpcional acabado = sistema.buscarAcabado(codigo);
        if (acabado == null) {
            System.out.println("No se encontro el acabado.");
        } else {
            System.out.println(acabado.mostrarInformacion());
        }
    }

    private static void modificarAcabado(SistemaInmobiliaria sistema, Scanner entrada) {
        String codigo = leerTexto(entrada, "Codigo del acabado a modificar: ");
        AcabadoOpcional acabado = sistema.buscarAcabado(codigo);
        if (acabado == null) {
            System.out.println("No se encontro el acabado.");
            return;
        }
        System.out.println(acabado.mostrarInformacion());
        System.out.println("Deje en blanco los campos de texto que no desea cambiar.");
        String nombre = leerTexto(entrada, "Nuevo nombre: ");
        if (!nombre.isEmpty()) {
            acabado.setNombre(nombre);
        }
        String descripcion = leerTexto(entrada, "Nueva descripcion: ");
        if (!descripcion.isEmpty()) {
            acabado.setDescripcion(descripcion);
        }
        double precio = leerDecimal(entrada, "Nuevo precio (S/): ");
        if (precio > 0) {
            acabado.setPrecio(precio);
        } else {
            System.out.println("Precio invalido. Se mantuvo el precio anterior.");
        }
        System.out.println("Acabado modificado correctamente.");
    }

    private static void eliminarAcabado(SistemaInmobiliaria sistema, Scanner entrada) {
        String codigo = leerTexto(entrada, "Codigo del acabado a eliminar: ");
        if (sistema.eliminarAcabado(codigo)) {
            System.out.println("Acabado eliminado correctamente.");
        } else {
            System.out.println("No se encontro el acabado.");
        }
    }

    private static void listarAcabados(SistemaInmobiliaria sistema) {
        if (sistema.getCantidadAcabados() == 0) {
            System.out.println("No hay acabados registrados.");
            return;
        }
        System.out.println("LISTA DE ACABADOS OPCIONALES:");
        for (int i = 0; i < sistema.getCantidadAcabados(); i++) {
            System.out.println(sistema.obtenerAcabado(i).mostrarInformacion());
        }
    }


    private static void menuAsesor(SistemaInmobiliaria sistema, Scanner entrada,
            AsesorVenta asesor) {
        boolean cerrarSesion = false;
        while (!cerrarSesion) {
            System.out.println("========================================");
            System.out.println("MENU DEL ASESOR DE VENTA");
            System.out.println("========================================");
            System.out.println("1. Gestionar clientes");
            System.out.println("2. Gestionar reservas");
            System.out.println("3. Gestionar ventas");
            System.out.println("4. Registrar pagos");
            System.out.println("5. Generar cotizacion");
            System.out.println("6. Mostrar contrato");
            System.out.println("7. Cerrar sesion");
            System.out.print("Seleccione una opcion: ");
            String opcion = entrada.nextLine().trim();
            switch (opcion) {
                case "1":
                    gestionarClientes(sistema, entrada);
                    break;
                case "2":
                    gestionarReservas(sistema, entrada, asesor);
                    break;
                case "3":
                    gestionarVentas(sistema, entrada);
                    break;
                case "4":
                    gestionarPagos(sistema, entrada);
                    break;
                case "5":
                    generarCotizacion(sistema, entrada);
                    break;
                case "6":
                    mostrarContrato(sistema, entrada);
                    break;
                case "7":
                    cerrarSesion = true;
                    System.out.println("Sesion cerrada.");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }


    private static void gestionarClientes(SistemaInmobiliaria sistema, Scanner entrada) {
        boolean regresar = false;
        while (!regresar) {
            System.out.println("----------------------------------------");
            System.out.println("GESTION DE CLIENTES");
            System.out.println("----------------------------------------");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Buscar cliente");
            System.out.println("3. Modificar cliente");
            System.out.println("4. Eliminar cliente");
            System.out.println("5. Listar clientes");
            System.out.println("6. Volver");
            System.out.print("Seleccione una opcion: ");
            String opcion = entrada.nextLine().trim();
            switch (opcion) {
                case "1":
                    registrarCliente(sistema, entrada);
                    break;
                case "2":
                    buscarCliente(sistema, entrada);
                    break;
                case "3":
                    modificarCliente(sistema, entrada);
                    break;
                case "4":
                    eliminarCliente(sistema, entrada);
                    break;
                case "5":
                    listarClientes(sistema);
                    break;
                case "6":
                    regresar = true;
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    private static void registrarCliente(SistemaInmobiliaria sistema, Scanner entrada) {
        if (sistema.clientesLlenos()) {
            System.out.println("Capacidad maxima alcanzada.");
            return;
        }
        String dni = leerTexto(entrada, "DNI: ");
        if (sistema.buscarCliente(dni) != null) {
            System.out.println("No se pudo registrar: el DNI ya existe.");
            return;
        }
        String nombres = leerTexto(entrada, "Nombres: ");
        String apellidos = leerTexto(entrada, "Apellidos: ");
        String telefono = leerTexto(entrada, "Telefono: ");
        String correo = leerTexto(entrada, "Correo: ");
        ClientePotencial cliente = new ClientePotencial(dni, nombres, apellidos,
                telefono, correo);
        if (sistema.registrarCliente(cliente)) {
            System.out.println("Cliente registrado correctamente.");
        } else {
            System.out.println("No se pudo registrar el cliente.");
        }
    }

    private static void buscarCliente(SistemaInmobiliaria sistema, Scanner entrada) {
        String dni = leerTexto(entrada, "DNI del cliente: ");
        ClientePotencial cliente = sistema.buscarCliente(dni);
        if (cliente == null) {
            System.out.println("No se encontro el cliente.");
        } else {
            System.out.println(cliente.mostrarInformacion());
        }
    }

    private static void modificarCliente(SistemaInmobiliaria sistema, Scanner entrada) {
        String dni = leerTexto(entrada, "DNI del cliente a modificar: ");
        ClientePotencial cliente = sistema.buscarCliente(dni);
        if (cliente == null) {
            System.out.println("No se encontro el cliente.");
            return;
        }
        System.out.println(cliente.mostrarInformacion());
        System.out.println("Deje en blanco los campos que no desea cambiar.");
        String nombres = leerTexto(entrada, "Nuevos nombres: ");
        if (!nombres.isEmpty()) {
            cliente.setNombres(nombres);
        }
        String apellidos = leerTexto(entrada, "Nuevos apellidos: ");
        if (!apellidos.isEmpty()) {
            cliente.setApellidos(apellidos);
        }
        String telefono = leerTexto(entrada, "Nuevo telefono: ");
        if (!telefono.isEmpty()) {
            cliente.setTelefono(telefono);
        }
        String correo = leerTexto(entrada, "Nuevo correo: ");
        if (!correo.isEmpty()) {
            cliente.setCorreo(correo);
        }
        System.out.println("Cliente modificado correctamente.");
    }

    private static void eliminarCliente(SistemaInmobiliaria sistema, Scanner entrada) {
        String dni = leerTexto(entrada, "DNI del cliente a eliminar: ");
        if (sistema.buscarCliente(dni) == null) {
            System.out.println("No se encontro el cliente.");
            return;
        }
        if (sistema.clienteTieneOperaciones(dni)) {
            System.out.println("No se puede eliminar: el cliente tiene reservas o ventas.");
            return;
        }
        if (sistema.eliminarCliente(dni)) {
            System.out.println("Cliente eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el cliente.");
        }
    }

    private static void listarClientes(SistemaInmobiliaria sistema) {
        if (sistema.getCantidadClientes() == 0) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        System.out.println("LISTA DE CLIENTES:");
        for (int i = 0; i < sistema.getCantidadClientes(); i++) {
            System.out.println(sistema.obtenerCliente(i).mostrarInformacion());
        }
    }

 
    private static void gestionarReservas(SistemaInmobiliaria sistema, Scanner entrada,
            AsesorVenta asesor) {
        boolean regresar = false;
        while (!regresar) {
            System.out.println("----------------------------------------");
            System.out.println("GESTION DE RESERVAS");
            System.out.println("----------------------------------------");
            System.out.println("1. Registrar reserva");
            System.out.println("2. Buscar reserva");
            System.out.println("3. Cancelar reserva");
            System.out.println("4. Listar reservas");
            System.out.println("5. Volver");
            System.out.print("Seleccione una opcion: ");
            String opcion = entrada.nextLine().trim();
            switch (opcion) {
                case "1":
                    registrarReserva(sistema, entrada, asesor);
                    break;
                case "2":
                    buscarReserva(sistema, entrada);
                    break;
                case "3":
                    cancelarReserva(sistema, entrada);
                    break;
                case "4":
                    listarReservas(sistema);
                    break;
                case "5":
                    regresar = true;
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    private static void registrarReserva(SistemaInmobiliaria sistema, Scanner entrada,
            AsesorVenta asesor) {
        if (sistema.reservasLlenas()) {
            System.out.println("Capacidad maxima alcanzada.");
            return;
        }
        sistema.actualizarReservas();
        String codigo = leerTexto(entrada, "Codigo de reserva: ");
        if (sistema.buscarReserva(codigo) != null) {
            System.out.println("No se pudo registrar: el codigo ya existe.");
            return;
        }
        String dniCliente = leerTexto(entrada, "DNI del cliente: ");
        ClientePotencial cliente = sistema.buscarCliente(dniCliente);
        if (cliente == null) {
            System.out.println("No se encontro el cliente.");
            return;
        }
        String codigoDepartamento = leerTexto(entrada, "Codigo del departamento: ");
        Departamento departamento = sistema.buscarDepartamento(codigoDepartamento);
        if (departamento == null) {
            System.out.println("No se encontro el departamento.");
            return;
        }
        if (!departamento.estaDisponible()) {
            System.out.println("El departamento no esta disponible.");
            return;
        }
        double monto = leerDecimal(entrada, "Monto de separacion (S/): ");
        if (monto <= 0) {
            System.out.println("El monto de separacion debe ser mayor a cero.");
            return;
        }
        LocalDate fechaVigencia = leerFecha(entrada, "Fecha de vigencia");
        if (fechaVigencia.isBefore(LocalDate.now())) {
            System.out.println("La fecha de vigencia no puede ser anterior a hoy.");
            return;
        }
        Reserva reserva = new Reserva(codigo, cliente, departamento, asesor,
                monto, fechaVigencia);
        if (sistema.registrarReserva(reserva)) {
            System.out.println("Reserva registrada correctamente.");
        } else {
            System.out.println("No se pudo registrar la reserva.");
        }
    }

    private static void buscarReserva(SistemaInmobiliaria sistema, Scanner entrada) {
        sistema.actualizarReservas();
        String codigo = leerTexto(entrada, "Codigo de la reserva: ");
        Reserva reserva = sistema.buscarReserva(codigo);
        if (reserva == null) {
            System.out.println("No se encontro la reserva.");
            return;
        }
        System.out.println(reserva.mostrarInformacion());
        if (reserva.getEstado().equals("VENCIDA")) {
            System.out.println("La reserva se encuentra vencida.");
        }
    }

    private static void cancelarReserva(SistemaInmobiliaria sistema, Scanner entrada) {
        sistema.actualizarReservas();
        String codigo = leerTexto(entrada, "Codigo de la reserva a cancelar: ");
        Reserva reserva = sistema.buscarReserva(codigo);
        if (reserva == null) {
            System.out.println("No se encontro la reserva.");
            return;
        }
        if (reserva.getEstado().equals("VENCIDA")) {
            System.out.println("La reserva se encuentra vencida.");
            return;
        }
        if (!reserva.getEstado().equals("VIGENTE")) {
            System.out.println("La reserva no esta vigente. Estado actual: "
                    + reserva.getEstado());
            return;
        }
        reserva.cancelar();
        System.out.println("Reserva cancelada correctamente.");
    }

    private static void listarReservas(SistemaInmobiliaria sistema) {
        sistema.actualizarReservas();
        if (sistema.getCantidadReservas() == 0) {
            System.out.println("No hay reservas registradas.");
            return;
        }
        System.out.println("LISTA DE RESERVAS:");
        for (int i = 0; i < sistema.getCantidadReservas(); i++) {
            System.out.println(sistema.obtenerReserva(i).mostrarInformacion());
        }
    }

    // --------------------------------------------------
    // GESTION DE VENTAS
    // --------------------------------------------------

    private static void gestionarVentas(SistemaInmobiliaria sistema, Scanner entrada) {
        boolean regresar = false;
        while (!regresar) {
            System.out.println("----------------------------------------");
            System.out.println("GESTION DE VENTAS");
            System.out.println("----------------------------------------");
            System.out.println("1. Registrar venta");
            System.out.println("2. Buscar venta");
            System.out.println("3. Listar ventas");
            System.out.println("4. Mostrar contrato de una venta");
            System.out.println("5. Volver");
            System.out.print("Seleccione una opcion: ");
            String opcion = entrada.nextLine().trim();
            switch (opcion) {
                case "1":
                    registrarVenta(sistema, entrada);
                    break;
                case "2":
                    buscarVenta(sistema, entrada);
                    break;
                case "3":
                    listarVentas(sistema);
                    break;
                case "4":
                    mostrarContrato(sistema, entrada);
                    break;
                case "5":
                    regresar = true;
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    private static void registrarVenta(SistemaInmobiliaria sistema, Scanner entrada) {
        if (sistema.ventasLlenas()) {
            System.out.println("Capacidad maxima alcanzada.");
            return;
        }
        sistema.actualizarReservas();
        String codigo = leerTexto(entrada, "Codigo de venta: ");
        if (sistema.buscarVenta(codigo) != null) {
            System.out.println("No se pudo registrar: el codigo ya existe.");
            return;
        }
        String codigoReserva = leerTexto(entrada, "Codigo de la reserva: ");
        Reserva reserva = sistema.buscarReserva(codigoReserva);
        if (reserva == null) {
            System.out.println("No se encontro la reserva.");
            return;
        }
        if (reserva.getEstado().equals("VENCIDA")) {
            System.out.println("La reserva se encuentra vencida.");
            return;
        }
        if (!reserva.getEstado().equals("VIGENTE")) {
            System.out.println("La reserva no esta vigente. Estado actual: "
                    + reserva.getEstado());
            return;
        }
        System.out.println("Modalidades de pago:");
        System.out.println("1. Contado");
        System.out.println("2. Financiamiento bancario");
        System.out.println("3. Cuotas directas");
        String opcionModalidad = leerTexto(entrada, "Modalidad (1-3): ");
        String modalidad;
        switch (opcionModalidad) {
            case "1":
                modalidad = "CONTADO";
                break;
            case "2":
                modalidad = "FINANCIAMIENTO BANCARIO";
                break;
            case "3":
                modalidad = "CUOTAS DIRECTAS";
                break;
            default:
                System.out.println("Modalidad invalida. No se registro la venta.");
                return;
        }
        Venta venta = new Venta(codigo, reserva, modalidad, 20);
        agregarAcabadosAVenta(sistema, entrada, venta);
        System.out.println("Precio total de la venta: S/ "
                + String.format("%.2f", venta.getPrecioTotal()));
        if (modalidad.equals("CUOTAS DIRECTAS")) {
            double cuotaInicial = leerDecimal(entrada, "Cuota inicial (S/): ");
            if (cuotaInicial < 0 || cuotaInicial >= venta.getPrecioTotal()) {
                System.out.println("La cuota inicial debe ser mayor o igual a cero "
                        + "y menor al precio total. No se registro la venta.");
                return;
            }
            int numeroCuotas = leerEntero(entrada, "Numero de cuotas: ");
            if (numeroCuotas <= 0) {
                System.out.println("El numero de cuotas debe ser mayor a cero. "
                        + "No se registro la venta.");
                return;
            }
            LocalDate primerVencimiento = leerFecha(entrada, "Fecha del primer vencimiento");
            if (primerVencimiento.isBefore(LocalDate.now())) {
                System.out.println("La fecha del primer vencimiento no puede ser "
                        + "anterior a hoy. No se registro la venta.");
                return;
            }
            venta.generarCronograma(cuotaInicial, numeroCuotas, primerVencimiento);
            System.out.println("Monto por cuota: S/ "
                    + String.format("%.2f", venta.getCronograma().getMontoPorCuota()));
        }
        if (sistema.registrarVenta(venta)) {
            System.out.println("Venta concretada correctamente.");
            System.out.println("Contrato generado: "
                    + venta.getContrato().getCodigo());
        } else {
            System.out.println("No se pudo registrar la venta.");
        }
    }

    private static void agregarAcabadosAVenta(SistemaInmobiliaria sistema,
            Scanner entrada, Venta venta) {
        System.out.println("Agregue acabados opcionales "
                + "(deje en blanco para terminar).");
        boolean terminar = false;
        while (!terminar) {
            String codigoAcabado = leerTexto(entrada, "Codigo de acabado: ");
            if (codigoAcabado.isEmpty()) {
                terminar = true;
            } else {
                AcabadoOpcional acabado = sistema.buscarAcabado(codigoAcabado);
                if (acabado == null) {
                    System.out.println("No se encontro el acabado.");
                } else if (venta.agregarAcabado(acabado)) {
                    System.out.println("Acabado agregado: " + acabado.getNombre()
                            + " (S/ " + String.format("%.2f", acabado.getPrecio()) + ")");
                } else {
                    System.out.println("No se pudo agregar el acabado.");
                }
            }
        }
    }

    private static void buscarVenta(SistemaInmobiliaria sistema, Scanner entrada) {
        String codigo = leerTexto(entrada, "Codigo de la venta: ");
        Venta venta = sistema.buscarVenta(codigo);
        if (venta == null) {
            System.out.println("No se encontro la venta.");
        } else {
            System.out.println(venta.mostrarInformacion());
        }
    }

    private static void listarVentas(SistemaInmobiliaria sistema) {
        if (sistema.getCantidadVentas() == 0) {
            System.out.println("No hay ventas registradas.");
            return;
        }
        System.out.println("LISTA DE VENTAS:");
        for (int i = 0; i < sistema.getCantidadVentas(); i++) {
            System.out.println(sistema.obtenerVenta(i).mostrarInformacion());
        }
    }

    private static void mostrarContrato(SistemaInmobiliaria sistema, Scanner entrada) {
        String codigo = leerTexto(entrada, "Codigo de la venta: ");
        Venta venta = sistema.buscarVenta(codigo);
        if (venta == null) {
            System.out.println("No se encontro la venta.");
            return;
        }
        if (venta.getContrato() == null) {
            System.out.println("La venta no tiene contrato generado.");
            return;
        }
        System.out.println(venta.getContrato().generarTexto());
    }


    private static void gestionarPagos(SistemaInmobiliaria sistema, Scanner entrada) {
        boolean regresar = false;
        while (!regresar) {
            System.out.println("----------------------------------------");
            System.out.println("GESTION DE PAGOS");
            System.out.println("----------------------------------------");
            System.out.println("1. Registrar pago de cuota");
            System.out.println("2. Mostrar cronograma");
            System.out.println("3. Consultar saldo pendiente");
            System.out.println("4. Volver");
            System.out.print("Seleccione una opcion: ");
            String opcion = entrada.nextLine().trim();
            switch (opcion) {
                case "1":
                    registrarPago(sistema, entrada);
                    break;
                case "2":
                    mostrarCronograma(sistema, entrada);
                    break;
                case "3":
                    consultarSaldo(sistema, entrada);
                    break;
                case "4":
                    regresar = true;
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    private static void registrarPago(SistemaInmobiliaria sistema, Scanner entrada) {
        String codigoVenta = leerTexto(entrada, "Codigo de venta: ");
        Venta venta = sistema.buscarVenta(codigoVenta);
        if (venta == null) {
            System.out.println("No se encontro la venta.");
            return;
        }
        if (venta.getCronograma() == null) {
            System.out.println("La venta no tiene cronograma de pagos "
                    + "(modalidad " + venta.getModalidad() + ").");
            return;
        }
        int numeroCuota = leerEntero(entrada, "Numero de cuota: ");
        CuotaPago cuota = venta.getCronograma().buscarCuota(numeroCuota);
        if (cuota == null) {
            System.out.println("No se encontro la cuota.");
            return;
        }
        System.out.println(cuota.mostrarInformacion());
        double monto = leerDecimal(entrada, "Monto pagado (S/): ");
        int resultado = cuota.registrarPago(monto);
        switch (resultado) {
            case 0:
                System.out.println("Cuota pagada completamente.");
                break;
            case 1:
                System.out.println("Pago parcial registrado.");
                break;
            case -1:
                System.out.println("El monto supera el saldo de la cuota.");
                break;
            case -2:
                System.out.println("La cuota ya se encuentra pagada.");
                break;
            default:
                System.out.println("El monto debe ser mayor a cero.");
        }
    }

    private static void mostrarCronograma(SistemaInmobiliaria sistema, Scanner entrada) {
        String codigoVenta = leerTexto(entrada, "Codigo de venta: ");
        Venta venta = sistema.buscarVenta(codigoVenta);
        if (venta == null) {
            System.out.println("No se encontro la venta.");
            return;
        }
        if (venta.getCronograma() == null) {
            System.out.println("La venta no tiene cronograma de pagos "
                    + "(modalidad " + venta.getModalidad() + ").");
            return;
        }
        System.out.println(venta.getCronograma().mostrarCronograma());
    }

    private static void consultarSaldo(SistemaInmobiliaria sistema, Scanner entrada) {
        String codigoVenta = leerTexto(entrada, "Codigo de venta: ");
        Venta venta = sistema.buscarVenta(codigoVenta);
        if (venta == null) {
            System.out.println("No se encontro la venta.");
            return;
        }
        System.out.println("Precio total: S/ "
                + String.format("%.2f", venta.getPrecioTotal()));
        System.out.println("Monto pagado: S/ "
                + String.format("%.2f", venta.calcularMontoPagado()));
        System.out.println("Saldo pendiente: S/ "
                + String.format("%.2f", venta.calcularSaldoPendiente()));
    }


    private static void generarCotizacion(SistemaInmobiliaria sistema, Scanner entrada) {
        String codigoDepartamento = leerTexto(entrada, "Codigo del departamento: ");
        Departamento departamento = sistema.buscarDepartamento(codigoDepartamento);
        if (departamento == null) {
            System.out.println("No se encontro el departamento.");
            return;
        }
        Proyecto proyecto = sistema.buscarProyectoDeDepartamento(codigoDepartamento);
        double total = departamento.getPrecio();
        String detalleAcabados = "";
        System.out.println("Agregue acabados opcionales a la cotizacion "
                + "(deje en blanco para terminar).");
        boolean terminar = false;
        while (!terminar) {
            String codigoAcabado = leerTexto(entrada, "Codigo de acabado: ");
            if (codigoAcabado.isEmpty()) {
                terminar = true;
            } else {
                AcabadoOpcional acabado = sistema.buscarAcabado(codigoAcabado);
                if (acabado == null) {
                    System.out.println("No se encontro el acabado.");
                } else {
                    total = total + acabado.getPrecio();
                    detalleAcabados += "- " + acabado.getNombre() + ": S/ "
                            + String.format("%.2f", acabado.getPrecio()) + "\n";
                }
            }
        }
        System.out.println("========================================");
        System.out.println("COTIZACION");
        System.out.println("========================================");
        System.out.println("Fecha: " + LocalDate.now());
        System.out.println("Proyecto: " + proyecto.getNombre());
        System.out.println("Departamento: " + departamento.getCodigo()
                + " | Piso: " + departamento.getPiso()
                + " | Dormitorios: " + departamento.getNumeroDormitorios()
                + " | Area: " + departamento.getArea() + " m2");
        System.out.println("Estado: " + departamento.getEstado());
        System.out.println("Precio base: S/ "
                + String.format("%.2f", departamento.getPrecio()));
        if (detalleAcabados.isEmpty()) {
            System.out.println("Acabados opcionales: ninguno");
        } else {
            System.out.println("Acabados opcionales:");
            System.out.print(detalleAcabados);
        }
        System.out.println("----------------------------------------");
        System.out.println("PRECIO TOTAL COTIZADO: S/ " + String.format("%.2f", total));
        System.out.println("========================================");
    }


    private static void menuGerente(SistemaInmobiliaria sistema, Scanner entrada) {
        boolean cerrarSesion = false;
        while (!cerrarSesion) {
            System.out.println("========================================");
            System.out.println("MENU DEL GERENTE");
            System.out.println("========================================");
            System.out.println("1. Reporte de departamentos");
            System.out.println("2. Reporte de ventas");
            System.out.println("3. Reporte de ingresos");
            System.out.println("4. Reporte de proyectos");
            System.out.println("5. Reporte de ventas por asesor");
            System.out.println("6. Reporte de ventas por fechas");
            System.out.println("7. Cerrar sesion");
            System.out.print("Seleccione una opcion: ");
            String opcion = entrada.nextLine().trim();
            switch (opcion) {
                case "1":
                    System.out.println(sistema.reporteDepartamentos());
                    break;
                case "2":
                    System.out.println(sistema.reporteVentas());
                    break;
                case "3":
                    System.out.println(sistema.reporteIngresos());
                    break;
                case "4":
                    System.out.println(sistema.reporteProyectos());
                    break;
                case "5":
                    System.out.println(sistema.reporteVentasPorAsesor());
                    break;
                case "6":
                    reporteVentasPorFechas(sistema, entrada);
                    break;
                case "7":
                    cerrarSesion = true;
                    System.out.println("Sesion cerrada.");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    private static void reporteVentasPorFechas(SistemaInmobiliaria sistema, Scanner entrada) {
        LocalDate fechaInicio = leerFecha(entrada, "Fecha de inicio");
        LocalDate fechaFin = leerFecha(entrada, "Fecha de fin");
        if (fechaFin.isBefore(fechaInicio)) {
            System.out.println("La fecha de fin no puede ser anterior a la fecha de inicio.");
            return;
        }
        System.out.println(sistema.reporteVentasPorFechas(fechaInicio, fechaFin));
    }
}
