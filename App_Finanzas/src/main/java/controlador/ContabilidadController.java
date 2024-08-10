package controlador;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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
        }
    }

    private void filtrarPorFechas(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LocalDate fechaInicio = LocalDate.parse(req.getParameter("fechaInicio"));
        LocalDate fechaActual = LocalDate.parse(req.getParameter("fechaFin"));

        HttpSession session = req.getSession();
        session.setAttribute("fechaInicio", fechaInicio);
        session.setAttribute("fechaActual", fechaActual);

        resp.sendRedirect("/ContabilidadController?ruta=verDashboard");
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

        CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
        List<CategoriaEgreso> categoriasEgreso = categoriaEgresoDAO.obtenerTodo();

        req.setAttribute("cuentas", cuentas);
        req.setAttribute("categoriasEgreso", categoriasEgreso);

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
        LocalDate desde = LocalDate.parse(req.getParameter("desde"));
        LocalDate hasta = LocalDate.parse(req.getParameter("hasta"));

        // Crear una instancia del DAO para acceder a los movimientos
        MovimientoDAO movimientoDAO = new MovimientoDAO();

        // Llamar al método obtenerTodo del DAO para obtener los movimientos
        List<MovimientoDTO> movimientos = movimientoDAO.obtenerTodo(desde, hasta);

        // Establecer los movimientos en la solicitud para ser enviados a la vista
        req.setAttribute("movimientos", movimientos);

        // Llamar a la vista para mostrar los movimientos
        req.getRequestDispatcher("jsp/VerMovimientos.jsp").forward(req, resp);
    }

    private void verCategoria(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idCategoria = Integer.parseInt(req.getParameter("idCategoria"));

        CategoriaDAO categoriaDao = new CategoriaDAO();
        Categoria categoria = categoriaDao.obtenerCategoriaPorId(idCategoria);

        MovimientoDAO movimientoDao = new MovimientoDAO();
        //List<MovimientoDTO> movimientos = movimientoDao.obtenerMovimientosPorIdCategoria(idCategoria);

        req.setAttribute("categoria", categoria);
        //req.setAttribute("movimientos", movimientos);

        // Llamar a la vista para mostrar la categoría y sus movimientos
        req.getRequestDispatcher("jsp/VerCategoria.jsp").forward(req, resp);
    }

    private void eliminarMovimiento(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Step 1: Retrieve the movement ID from the request parameters
        int idMovimiento = Integer.parseInt(req.getParameter("idMovimiento"));

        // Step 1.1: Fetch the movement details using MovimientoDAO
        MovimientoDAO movimientoDao = new MovimientoDAO();
        //Movimiento movimiento = movimientoDao.obtenerMovimientoPorIdMovimiento(idMovimiento);

        // Step 1.2: Show a confirmation page to the user
        // req.setAttribute("movimiento", movimiento);
        req.getRequestDispatcher("jsp/verMovimientos.jsp").forward(req, resp);
    }


    private void confirmarEliminacion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Step 2: Retrieve confirmation status from the request parameters
        boolean puedeEliminar = Boolean.parseBoolean(req.getParameter("puedeEliminar"));

        if (puedeEliminar) {
            // Retrieve the movement ID from the request parameters
            int idMovimiento = Integer.parseInt(req.getParameter("idMovimiento"));

            // Step 2.1: If confirmed, delete the movement using MovimientoDAO
            MovimientoDAO movimientoDao = new MovimientoDAO();
            //  movimientoDao.eliminarMovimiento(idMovimiento);

            // Redirect to the movement list after deletion
            resp.sendRedirect("VerMovimientosServlet");
        } else {
            // If not confirmed, redirect back to the movement list without deletion
            resp.sendRedirect("VerMovimientosServlet");
        }
    }

    private void actualizarMovimiento(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Obtener el ID del movimiento desde la solicitud
        int idMovimiento = Integer.parseInt(req.getParameter("idMovimiento"));

        // Llamar al controlador para obtener el movimiento
        MovimientoDAO movimientoDao = new MovimientoDAO();
        // Movimiento movimiento = movimientoDao.obtenerMovimientoPorIdMovimiento(idMovimiento);

        // Enviar el movimiento como atributo a la vista
        //req.setAttribute("movimiento", movimiento);
        req.getRequestDispatcher("/verActualizarMovimiento.jsp").forward(req, resp);
    }

    private void registrarInfoActualizacion(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
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

        req.setAttribute("cuenta", cuenta); // Para obtener el saldo de la cuenta
        req.setAttribute("categoriasEgreso", categoriasEgreso);


//        actualizarIdCuenta(req, cuenta);

//        3.
        req.getRequestDispatcher("jsp/VerRegistrarGasto.jsp").forward(req, resp);
    }


    private void ingresarInfoGasto(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
//        1.
        int idCuenta = Integer.parseInt(req.getParameter("idCuenta"));
        String concepto = String.valueOf(Integer.parseInt(req.getParameter("concepto")));
        LocalDate fecha = LocalDate.ofEpochDay(Integer.parseInt(req.getParameter("fecha")));
        double valor = Double.parseDouble(req.getParameter("valor"));
        int idCategoria = Integer.parseInt(req.getParameter("idCategoria"));

//        2.
        CuentaDAO cuentaDao = new CuentaDAO();
        Cuenta cuenta = cuentaDao.obtenerCuentaPorId(idCuenta);

        CategoriaEgresoDAO categoriaEgresoDao = new CategoriaEgresoDAO();
        CategoriaEgreso categoriaEgreso = categoriaEgresoDao.obtenerCategoriaPorId(idCategoria);

        Egreso egreso = new Egreso(concepto, fecha, 12.4, new Cuenta(1, "Billetera544", 192.1), categoriaEgreso);

        EgresoDAO egresoDao = new EgresoDAO();
        egresoDao.guardarEgreso(egreso);

//        actualizar cuenta
        cuentaDao.actualizarSaldo(cuenta, valor);
//        actualizar categoria
        categoriaEgresoDao.actualizarSaldo(categoriaEgreso, valor);

//        3.
        resp.sendRedirect("/ContabilidadController?ruta=verCuenta");

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

        req.setAttribute("saldoCuenta", cuenta.getTotal());
        req.setAttribute("categoriasIngreso", categoriasIngreso);

        // actualizarIdCuenta(req, idCuenta);

//        3.
        req.getRequestDispatcher("jsp/VerRegistrarIngreso.jsp").forward(req, resp);
    }

    private void ingresarInfoIngreso(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //        1.
        int idCuenta = Integer.parseInt(req.getParameter("idCuenta"));
        String concepto = String.valueOf(Integer.parseInt(req.getParameter("concepto")));
        LocalDate fecha = LocalDate.ofEpochDay(Integer.parseInt(req.getParameter("fecha")));
        double valor = Double.parseDouble(req.getParameter("valor"));
        int idCategoria = Integer.parseInt(req.getParameter("idCategoria"));

//        2.
        CuentaDAO cuentaDao = new CuentaDAO();
        Cuenta cuenta = cuentaDao.obtenerCuentaPorId(idCuenta);

        CategoriaIngresoDAO categoriaIngresoDao = new CategoriaIngresoDAO();
        CategoriaIngreso categoriaIngreso = categoriaIngresoDao.obtenerCategoriaPorId(idCategoria);

        Ingreso ingreso = new Ingreso("nuevo ingreso", LocalDate.now(), 12.4, new Cuenta(1, "Billetera544", 192.1), categoriaIngreso);

        IngresoDAO ingresoDao = new IngresoDAO();
        ingresoDao.guardarIngreso(ingreso);


//        actualizar cuenta
        cuentaDao.actualizarSaldo(cuenta, valor);
//        actualizar categoria
        categoriaIngresoDao.actualizarSaldo(categoriaIngreso, valor);


//        3.
        resp.sendRedirect("/ContabilidadController?ruta=verCuenta");
    }

    private void registrarTransferencia(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //        1.
        int idCuenta = Integer.parseInt(req.getParameter("idCuenta"));

//        2.
        CuentaDAO cuentaDao = new CuentaDAO();
        Cuenta cuenta = cuentaDao.obtenerCuentaPorId(idCuenta);

        CategoriaTransferenciaDAO categoriaTransferenciaDAO = new CategoriaTransferenciaDAO();
        List<CategoriaTransferencia> categoriasTransferencia = categoriaTransferenciaDAO.obtenerTodo();

        req.setAttribute("saldoCuenta", cuenta.getTotal());
        req.setAttribute("categoriasTransferencia", categoriasTransferencia);

        //actualizarIdCuenta(req, idCuenta);

//        3.
        req.getRequestDispatcher("jsp/VerRegistrarTransferencia.jsp").forward(req, resp);


    }

    private void ingresarInfoTransferencia(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //        1.
        int idCuenta = Integer.parseInt(req.getParameter("idCuenta"));
        String concepto = String.valueOf(Integer.parseInt(req.getParameter("concepto")));
        LocalDate fecha = LocalDate.ofEpochDay(Integer.parseInt(req.getParameter("fecha")));
        double valor = Double.parseDouble(req.getParameter("valor"));
        int idCategoria = Integer.parseInt(req.getParameter("idCategoria"));

//        2.
        CuentaDAO cuentaDao = new CuentaDAO();
        Cuenta cuenta = cuentaDao.obtenerCuentaPorId(idCuenta);

        CategoriaTransferenciaDAO categoriaTransferenciaDao = new CategoriaTransferenciaDAO();
        CategoriaTransferencia categoriaTransferencia = categoriaTransferenciaDao.obtenerCategoriaPorId(idCategoria);

        Transferencia transferencia = new Transferencia(concepto, fecha, 12.2, new Cuenta(1, "Billetera544", 192.1), new Cuenta(4, "Bajo del colchón", 15.24), categoriaTransferencia);

        TransferenciaDAO transferenciaDao = new TransferenciaDAO();
        transferenciaDao.guardarTransferencia(transferencia);

//        actualizar cuenta
        cuentaDao.actualizarSaldo(cuenta, valor);
//        actualizar categoria
        categoriaTransferenciaDao.actualizarSaldo(categoriaTransferencia, valor);

//        3.
        resp.sendRedirect("/ContabilidadController?ruta=verCuenta");
    }
/*
    private void actualizarIdCuenta(HttpServletRequest req, Cuenta nuevaCuenta) {
        HttpSession session = req.getSession();

        int idCuentaExistente = (int) session.getAttribute(nuevaCuenta.getId());

        if (idCuentaExistente == null || idCuentaExistente != nuevaCuenta) {
            // Si el idCuenta no está en la sesión o es diferente, actualiza la sesión
            session.setAttribute("idCuenta", nuevaCuenta);
        }
    }*/

}
