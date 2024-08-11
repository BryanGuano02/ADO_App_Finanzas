package controlador;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.dto.MovimientoDTO;
import modelo.entidades.*;
import modelo.dao.*;

@WebServlet("/ContabilidadController")
public class ContabilidadController extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.ruteador(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.ruteador(req, resp);

    }

    private void ruteador(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ruta = (req.getParameter("ruta") != null) ? req.getParameter("ruta") : "verCuenta";
        switch (ruta) {
            case "verDashboard":
                verDashboard(req, resp);
                break;
            case "verCuenta":
                this.verCuenta(req, resp);
                break;
            case "filtrarPorFechas":
                this.filtrarPorFechas(req, resp);
                break;
            case "verMovimientos":
                this.verMovimientos(req, resp);
                break;
            case "registrarGasto":
                this.registrarGasto(req, resp);
                break;
            case "ingresarInfoGasto":
                this.ingresarInfoGasto(req, resp);
                break;
            case "verCategoria":
                verCategoria(req, resp);
                break;
            case "eliminarMovimiento":
                eliminarMovimiento(req, resp);
                break;
            case "actualizarMovimiento":
                actualizarMovimiento(req, resp);
                break;
            case "registrarInfoActualizacion":
                registrarInfoActualizacion(req, resp);
                break;
            case "ingresarInfoIngreso":
                ingresarInfoIngreso(req, resp);
                break;
            case "registrarTransferencia":
                registrarTransferencia(req, resp);
                break;
            case "confirmarEliminacion":
                confirmarEliminacion(req, resp);
                break;
            case "registrarIngreso":
                registrarIngreso(req, resp);
                break;
            case "ingresarInfoTransferencia":
                ingresarInfoTransferencia(req, resp);
                break;
        }
    }

    private void filtrarPorFechas(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LocalDate fechaInicio = LocalDate.parse(req.getParameter("fechaInicio"));
        LocalDate fechaActual = LocalDate.parse(req.getParameter("fechaFin"));

        HttpSession session = req.getSession();
        session.setAttribute("fechaInicio", fechaInicio);
        session.setAttribute("fechaActual", fechaActual);

        resp.sendRedirect("ContabilidadController?ruta=verDashboard");
    }

    private void verDashboard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        1. Obtener parámetros
        HttpSession session = req.getSession();

        LocalDate fechaInicio = null;
        LocalDate fechaActual = null;

        if (session.getAttribute("fechaInicio") == null && session.getAttribute("fechaActual") == null) {
            fechaInicio = LocalDate.now().withDayOfMonth(1);
            fechaActual = LocalDate.now().plusDays(1);

            session.setAttribute("fechaInicio", fechaInicio);
            session.setAttribute("fechaActual", fechaActual);
        }

//        2. Hablar con el modelo
        CuentaDAO cuentaDAO = new CuentaDAO();
        List<Cuenta> cuentas = cuentaDAO.obtenerTodo(); //TODO: El saldo de la cuenta es también en base a las fechas

     //   CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
       // List<CategoriaEgreso> categoriasEgreso = categoriaEgresoDAO.obtenerTodo();

        req.setAttribute("cuentas", cuentas);
      //  req.setAttribute("categoriasEgreso", categoriasEgreso);

        MovimientoDAO movimientoDAO = new MovimientoDAO();
        List<MovimientoDTO> movimientos = movimientoDAO.obtenerTodo(fechaInicio,fechaActual);

        req.setAttribute("movimientos", movimientos);
/*         CategoriaIngreso categoria = new CategoriaIngreso("CHAUCHAS PEQUEÑAS", 1);
         CategoriaIngresoDAO categoriaIngresoDAO = new CategoriaIngresoDAO();
         categoriaIngresoDAO.ingresar(categoria);
         Ingreso ingreso = new Ingreso("CHAUCHA",LocalDate.now(),2,cuentas.get(4),categoria);
         IngresoDAO ingresoDAO = new IngresoDAO();
         ingresoDAO.guardarIngreso(ingreso);

        CategoriaEgreso categoriaEgre = new CategoriaEgreso("playita", 1);
        CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
        categoriaEgresoDAO.ingresar(categoriaEgre);
        Egreso egreso = new Egreso("tonsupaFest",LocalDate.now(),12.2,cuentas.get(2),categoriaEgre);
        EgresoDAO egresoDAO = new EgresoDAO();
        egresoDAO.guardarEgreso(egreso);

        CategoriaTransferencia categoriaTrans = new CategoriaTransferencia("PAPAS DEL NIKO", 1);
        CategoriaTransferenciaDAO categoriaTransDAO = new CategoriaTransferenciaDAO();
        categoriaTransDAO.ingresar(categoriaTrans);
        Transferencia trans = new Transferencia("PAPAS CON TU ÑAÑA",LocalDate.now(),12.2,cuentas.get(1), cuentas.get(2),categoriaTrans);
        TransferenciaDAO transDAO = new TransferenciaDAO();
        transDAO.guardarTransferencia(trans);*/

        //req.setAttribute("ingresos", ingresos);
        //TODO: Mandar los parámetros restantes  de mostrar()
        //		llamar a la vista - mostrar()
        req.getRequestDispatcher("jsp/VerDashboard.jsp").forward(req, resp);
    }

    private void verCuenta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        1.
//        int idCuenta = Integer.parseInt(req.getParameter("idCuenta"));
        int idCuenta = 1;
//        2.
        CuentaDAO cuentaDAO = new CuentaDAO();
        Cuenta cuenta = cuentaDAO.obtenerCuentaPorId(idCuenta);

        MovimientoDAO movimientoDao = new MovimientoDAO();
        //List<MovimientoDTO> movimientos = movimientoDao.obtenerMovimientosPorIdCuenta(idCuenta);

        req.setAttribute("cuenta", cuenta);
        //req.setAttribute("movimientos", movimientos);

//        3.
        req.getRequestDispatcher("jsp/VerCuenta.jsp").forward(req, resp);
    }

    private void verMovimientos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Obtener las fechas desde la solicitud
        HttpSession session = req.getSession();

        // Obtener las fechas desde la solicitud
        LocalDate desde = (LocalDate) session.getAttribute("fechaInicio");
        LocalDate hasta = (LocalDate) session.getAttribute("fechaActual");

        // Verificar si alguna de las fechas es nula y establecer valores predeterminados si es necesario
        if (desde == null && hasta == null) {
            desde = LocalDate.now().withDayOfMonth(1);
            session.setAttribute("fechaInicio", desde);

            hasta = LocalDate.now().plusDays(1);
            session.setAttribute("fechaActual", hasta);
        }

        MovimientoDAO movimientoDAO = new MovimientoDAO();


        List<MovimientoDTO> movimientos = movimientoDAO.obtenerTodo(desde, hasta);

        req.setAttribute("movimientos", movimientos);

        // Llamar a la vista para mostrar los movimientos
        req.getRequestDispatcher("jsp/VerMovimientos.jsp").forward(req, resp);
    }

    private void verCategoria(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idCategoria = Integer.parseInt(req.getParameter("idCategoria"));

        CategoriaDAO categoriaDao = new CategoriaDAO();
        Categoria categoria = categoriaDao.obtenerCategoriaPorId(idCategoria);

        MovimientoDAO movimientoDao = new MovimientoDAO();
//        List<MovimientoDTO> movimientos = movimientoDao.obtenerMovimientosPorIdCategoria(idCategoria);

        req.setAttribute("categoria", categoria);
//        req.setAttribute("movimientos", movimientos);

        // Llamar a la vista para mostrar la categoría y sus movimientos
        req.getRequestDispatcher("jsp/VerCategoria.jsp").forward(req, resp);
    }
/*
    private void eliminarMovimiento(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Step 1: Retrieve the movement ID from the request parameters
        int idMovimiento = Integer.parseInt(req.getParameter("idMovimiento"));

        // Step 1.1: Fetch the movement details using MovimientoDAO
        MovimientoDAO movimientoDao = new MovimientoDAO();
        MovimientoDTO movimiento = movimientoDao.obtenerMovimientoPorIdMovimiento(idMovimiento);

        // Step 1.2: Show a confirmation page to the user
        req.setAttribute("movimiento", movimiento);
        req.getRequestDispatcher("jsp/VerMovimientos.jsp").forward(req, resp);
    }
*/
private void eliminarMovimiento(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
    // Esta función ahora solo muestra la página de confirmación (con el pop-up)
    int idMovimiento = Integer.parseInt(req.getParameter("id"));

    // Redirigir a la página JSP donde se muestra el pop-up de confirmación
    req.setAttribute("idMovimiento", idMovimiento);
    req.getRequestDispatcher("jsp/VerMovimientos.jsp").forward(req, resp);
}


    private void confirmarEliminacion(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Obtener parámetros del formulario
        String puedeEliminarStr = req.getParameter("puedeEliminar");
        String idStr = req.getParameter("id");

        boolean puedeEliminar = Boolean.parseBoolean(puedeEliminarStr);

        if (puedeEliminar) {
            try {
                // Convertir el ID del movimiento a un número entero
                int idMovimiento = Integer.parseInt(idStr);

                // Crear instancia del DAO para realizar la eliminación
                MovimientoDAO movimientoDao = new MovimientoDAO();
                movimientoDao.eliminarMovimiento(idMovimiento);

                // Opcional: agregar un mensaje de éxito si lo deseas
                req.setAttribute("mensaje", "El movimiento ha sido eliminado.");
            } catch (NumberFormatException e) {
                // Opcional: manejar el error si el ID no es válido
                req.setAttribute("error", "El ID del movimiento no es válido.");
                e.printStackTrace();
            }
        }

        // Redirigir nuevamente a la vista de movimientos
        req.getRequestDispatcher("jsp/VerMovimientos.jsp").forward(req, resp);
    }

    private void actualizarMovimiento(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Obtener el ID del movimiento desde la solicitud
        int idMovimiento = Integer.parseInt(req.getParameter("id"));

        // Llamar al controlador para obtener el movimiento
        MovimientoDAO movimientoDao = new MovimientoDAO();
        Movimiento movimiento = movimientoDao.obtenerMovimientoPorIdMovimiento1(idMovimiento);

        // Crear una lista para almacenar las categorías
        List<Categoria> categorias = new ArrayList<>();

        // Usar polimorfismo para manejar diferentes tipos de movimientos
        if (movimiento instanceof Transferencia) {
            CategoriaTransferenciaDAO categoriaTransferenciaDao = new CategoriaTransferenciaDAO();
            categorias.addAll(categoriaTransferenciaDao.obtenerTodo()); // Corregir la adición de categorías
        } else if (movimiento instanceof Ingreso) {
            CategoriaIngresoDAO categoriaIngresoDao = new CategoriaIngresoDAO();
            categorias.addAll(categoriaIngresoDao.obtenerTodo()); // Corregir la adición de categorías
        } else if (movimiento instanceof Egreso) {
            CategoriaEgresoDAO categoriaEgresoDao = new CategoriaEgresoDAO();
            categorias.addAll(categoriaEgresoDao.obtenerTodo()); // Corregir la adición de categorías
        }

        // Enviar el movimiento y las categorías como atributos a la vista
        req.setAttribute("movimiento", movimiento);
        req.setAttribute("categorias", categorias);
        req.getRequestDispatcher("jsp/VerActualizarMovimiento.jsp").forward(req, resp);
    }

/*

    private void registrarInfoActualizacion(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
/*
            Movimiento movimiento =  null;
        // Usar polimorfismo para manejar diferentes tipos de movimientos
        if (movimiento = (Transferencia) req.getParameter("movimiento"); instanceof Transferencia) {
            CategoriaTransferenciaDAO categoriaTransferenciaDao = new CategoriaTransferenciaDAO();
            categorias.addAll(categoriaTransferenciaDao.obtenerTodo()); // Corregir la adición de categorías
        } else if (movimiento instanceof Ingreso) {
            CategoriaIngresoDAO categoriaIngresoDao = new CategoriaIngresoDAO();
            categorias.addAll(categoriaIngresoDao.obtenerTodo()); // Corregir la adición de categorías
        } else if (movimiento instanceof Egreso) {
            CategoriaEgresoDAO categoriaEgresoDao = new CategoriaEgresoDAO();
            categorias.addAll(categoriaEgresoDao.obtenerTodo()); // Corregir la adición de categorías
        }
//        1.
        String concepto = req.getParameter("concepto");
        LocalDate fecha = LocalDate.parse(req.getParameter("fecha"));
        double valor = Double.parseDouble(req.getParameter("valor"));
        int idCategoria = Integer.parseInt(req.getParameter("idCategoria"));
        int idCuentaOrigen = Integer.parseInt(req.getParameter("idCuentaOrigen"));
        int idCuentaDestino = Integer.parseInt(req.getParameter("idCuentaDestino"));

//        2.
        HttpSession session = req.getSession();
        session.setAttribute("concepto", concepto);
        session.setAttribute("fecha", fecha);
        session.setAttribute("valor", valor);
        session.setAttribute("idCategoria", idCategoria);
        session.setAttribute("idCuentaOrigen", idCuentaOrigen);
        session.setAttribute("idCuentaDestino", idCuentaDestino);


    }*/

    private void registrarInfoActualizacion(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Obtener los parámetros comunes
        String concepto = req.getParameter("concepto");
        LocalDate fecha = LocalDate.parse(req.getParameter("fecha"));
        double valor = Double.parseDouble(req.getParameter("valor"));

        System.out.println("SI LLEGO PERO NOSE: "+req.getParameter("id"));
        // Obtener el ID del movimiento desde la solicitud
        int idMovimiento = Integer.parseInt(req.getParameter("id"));
        System.out.println("paso 1 ");
        MovimientoDAO movimientoDao = new MovimientoDAO();
        Movimiento movimiento = movimientoDao.obtenerMovimientoPorIdMovimiento1(idMovimiento);
        System.out.println("paso 2 ");

        // Usar polimorfismo para actualizar el movimiento según su tipo
        if (movimiento instanceof Transferencia) {
            // Obtener parámetros específicos de Transferencia
            int idCuentaOrigen = Integer.parseInt(req.getParameter("idCuentaOrigen"));
            int idCuentaDestino = Integer.parseInt(req.getParameter("idCuentaDestino"));
            int idCategoria = Integer.parseInt(req.getParameter("categoria"));

            // Actualizar el movimiento Transferencia
            Transferencia transferencia = (Transferencia) movimiento;
            transferencia.setConcepto(concepto);
            transferencia.setFecha(fecha);
            transferencia.setValor(valor);
            transferencia.setCuentaOrigen(new CuentaDAO().obtenerCuentaPorId(idCuentaOrigen));
            transferencia.setCuentaDestino(new CuentaDAO().obtenerCuentaPorId(idCuentaDestino));
            transferencia.setCategoria(new CategoriaTransferenciaDAO().obtenerCategoriaPorId(idCategoria));

            // Guardar los cambios
            movimientoDao.actualizarMovimiento(transferencia);

        } else if (movimiento instanceof Ingreso) {
            // Obtener parámetros específicos de Ingreso
            int idCuentaDestino = Integer.parseInt(req.getParameter("idCuentaDestino"));
            int idCategoria = Integer.parseInt(req.getParameter("categoria"));

            // Actualizar el movimiento Ingreso
            Ingreso ingreso = (Ingreso) movimiento;
            ingreso.setConcepto(concepto);
            ingreso.setFecha(fecha);
            ingreso.setValor(valor);
            ingreso.setCuentaDestino(new CuentaDAO().obtenerCuentaPorId(idCuentaDestino));
            ingreso.setCategoria(new CategoriaIngresoDAO().obtenerCategoriaPorId(idCategoria));

            // Guardar los cambios
            movimientoDao.actualizarMovimiento(ingreso);

        } else if (movimiento instanceof Egreso) {
            // Obtener parámetros específicos de Egreso
            int idCuentaOrigen = Integer.parseInt(req.getParameter("idCuentaOrigen"));
            int idCategoria = Integer.parseInt(req.getParameter("categoria"));

            // Actualizar el movimiento Egreso
            Egreso egreso = (Egreso) movimiento;
            egreso.setConcepto(concepto);
            egreso.setFecha(fecha);
            egreso.setValor(valor);
            egreso.setCuentaOrigen(new CuentaDAO().obtenerCuentaPorId(idCuentaOrigen));
            egreso.setCategoria(new CategoriaEgresoDAO().obtenerCategoriaPorId(idCategoria));

            // Guardar los cambios
            movimientoDao.actualizarMovimiento(egreso);
        }

        // Redirigir a una página de éxito o mostrar un mensaje de éxito
        resp.sendRedirect("rutaExito.jsp");
    }


    private void confirmarActualizacion(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    }


    private void registrarGasto(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        1.
        int idCuenta = Integer.parseInt(req.getParameter("idCuenta"));

//        2.
        CuentaDAO cuentaDao = new CuentaDAO();
        Cuenta cuenta = cuentaDao.obtenerCuentaPorId(idCuenta);

        CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
        List<CategoriaEgreso> categoriasEgreso = categoriaEgresoDAO.obtenerTodo();

        req.setAttribute("cuenta", cuenta);
        req.setAttribute("categoriasEgreso", categoriasEgreso);
        actualizarIdCuenta(req, cuenta);

//        3.
        req.getRequestDispatcher("jsp/VerRegistrarGasto.jsp").forward(req, resp);
    }


    private void ingresarInfoGasto(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
//        1.
        HttpSession session = req.getSession();

        Cuenta cuenta = (Cuenta) session.getAttribute("cuenta");
        String concepto = String.valueOf(req.getParameter("concepto"));
        LocalDate fecha = LocalDate.parse((req.getParameter("fecha")));
        double valor = Double.parseDouble(req.getParameter("valor"));
        int idCategoria = Integer.parseInt(req.getParameter("idCategoria"));

//        2.
        CategoriaEgresoDAO categoriaEgresoDao = new CategoriaEgresoDAO();
        CategoriaEgreso categoriaEgreso = categoriaEgresoDao.obtenerCategoriaPorId(idCategoria);

        Egreso egreso = new Egreso(concepto, fecha, valor, cuenta, categoriaEgreso);

        EgresoDAO egresoDao = new EgresoDAO();
        egresoDao.guardarEgreso(egreso);

//        actualizar cuenta
        CuentaDAO cuentaDao = new CuentaDAO();
        cuentaDao.actualizarSaldo(cuenta, -valor);
//        actualizar categoria
        //categoriaEgresoDao.actualizarSaldo(categoriaEgreso, valor);
        //CategoriaIngreso categoria = new CategoriaIngreso("Prueba", 1);
       // Ingreso ingreso = new Ingreso("prueba", LocalDate.now(), 12, cuenta, categoria);

//        3.

        resp.sendRedirect("ContabilidadController?ruta=verDashboard");

    }

    private void registrarIngreso(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
//        1.
        int idCuenta = Integer.parseInt(req.getParameter("idCuenta"));

//        2.
        CuentaDAO cuentaDao = new CuentaDAO();
        Cuenta cuenta = cuentaDao.obtenerCuentaPorId(idCuenta);

        CategoriaIngresoDAO categoriaIngresoDAO = new CategoriaIngresoDAO();
        List<CategoriaIngreso> categoriasIngreso = categoriaIngresoDAO.obtenerTodo();

//        req.setAttribute("saldoCuenta", cuenta.getTotal());
        req.setAttribute("categoriasIngreso", categoriasIngreso);
        // req.setAttribute("idCuenta", cuenta.getId());
        actualizarIdCuenta(req, cuenta);
//        3.
        req.getRequestDispatcher("jsp/VerRegistrarIngreso.jsp").forward(req, resp);
    }

    private void ingresarInfoIngreso(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //        1.
        HttpSession session = req.getSession();

        Cuenta cuenta = (Cuenta) session.getAttribute("cuenta");
        String concepto = String.valueOf(req.getParameter("concepto"));
        LocalDate fecha = LocalDate.parse((req.getParameter("fecha")));
        double valor = Double.parseDouble(req.getParameter("valor"));
        int idCategoria = Integer.parseInt(req.getParameter("idCategoria"));

//        2.
        CategoriaIngresoDAO categoriaIngresoDao = new CategoriaIngresoDAO();
        CategoriaIngreso categoriaIngreso = categoriaIngresoDao.obtenerCategoriaPorId(idCategoria);

        //Ingreso ingreso = new Ingreso("nuevo ingreso", LocalDate.now(), 12.4, new Cuenta(1, "Billetera544", 192.1), categoriaIngreso);
        Ingreso ingreso = new Ingreso(concepto, fecha, valor, cuenta, categoriaIngreso);

        IngresoDAO ingresoDao = new IngresoDAO();
        ingresoDao.guardarIngreso(ingreso);

//        actualizar cuenta
        CuentaDAO cuentaDao = new CuentaDAO();
        cuentaDao.actualizarSaldo(cuenta, valor);
//        actualizar categoria
//        categoriaIngresoDao.actualizarSaldo(categoriaIngreso, valor);


//        3.
        resp.sendRedirect("ContabilidadController?ruta=verDashboard");
    }

    private void registrarTransferencia(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //        1.
        int idCuenta = Integer.parseInt(req.getParameter("idCuenta"));

//        2.
        CuentaDAO cuentaDao = new CuentaDAO();
        Cuenta cuenta = cuentaDao.obtenerCuentaPorId(idCuenta);

        List<Cuenta> cuentas = cuentaDao.obtenerTodo();

        CategoriaTransferenciaDAO categoriaTransferenciaDAO = new CategoriaTransferenciaDAO();
        List<CategoriaTransferencia> categoriasTransferencia = categoriaTransferenciaDAO.obtenerTodo();

        req.setAttribute("cuentaOrigen", cuenta);
        req.setAttribute("cuentasDestino", cuentas);
        req.setAttribute("categoriasTransferencia", categoriasTransferencia);

        //Mandar las cuentas
        //Mandar las categoras
        actualizarIdCuenta(req, cuenta);

//        3.
        req.getRequestDispatcher("jsp/VerRegistrarTransferencia.jsp").forward(req, resp);
    }

    private void ingresarInfoTransferencia(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //        1.
        HttpSession session = req.getSession();

        Cuenta cuentaOrigen = (Cuenta) session.getAttribute("cuenta");
        int idCuentaDestino = Integer.parseInt(req.getParameter("cuentaDestino"));

        String concepto = String.valueOf(req.getParameter("concepto"));
        LocalDate fecha = LocalDate.parse((req.getParameter("fecha")));
        double valor = Double.parseDouble(req.getParameter("valor"));
        int idCategoria = Integer.parseInt(req.getParameter("idCategoria"));

//        2.
        CuentaDAO cuentaDao = new CuentaDAO();
        Cuenta cuentaDestino = cuentaDao.obtenerCuentaPorId(idCuentaDestino);

        CategoriaTransferenciaDAO categoriaTransferenciaDao = new CategoriaTransferenciaDAO();
        CategoriaTransferencia categoriaTransferencia = categoriaTransferenciaDao.obtenerCategoriaPorId(idCategoria);

        Transferencia transferencia = new Transferencia(concepto, fecha, valor, cuentaDestino, cuentaOrigen, categoriaTransferencia);

        TransferenciaDAO transferenciaDao = new TransferenciaDAO();
        transferenciaDao.guardarTransferencia(transferencia);

        //TODO: cranear como mostrar la lista de cuentas

//        actualizar cuenta

        cuentaDao.actualizarSaldo(cuentaOrigen, -valor);
        cuentaDao.actualizarSaldo(cuentaDestino, valor);

//        3.
        resp.sendRedirect("/ContabilidadController?ruta=verDashboard");
    }

    private void actualizarIdCuenta(HttpServletRequest req, Cuenta nuevaCuenta) {
        HttpSession session = req.getSession();

        Integer idCuentaExistente = (Integer) session.getAttribute("idCuenta");

        if ((idCuentaExistente == null) || (idCuentaExistente != nuevaCuenta.getId())) {
            // Si el idCuenta no está en la sesión o es diferente, actualiza la sesión
            session.setAttribute("cuenta", nuevaCuenta);
            session.setAttribute("idCuenta", nuevaCuenta.getId());
        }
    }

}
