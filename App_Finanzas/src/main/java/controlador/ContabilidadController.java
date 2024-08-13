package controlador;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

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
            case "registrarEgreso":
                this.registrarEgreso(req, resp);
                break;
            case "ingresarInfoEgreso":
                this.ingresarInfoEgreso(req, resp);
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
            case "registrarIngreso":
                registrarIngreso(req, resp);
                break;
            case "ingresarInfoTransferencia":
                ingresarInfoTransferencia(req, resp);
                break;
            case "cancelar":
                cancelar(req, resp);
                break;
        }
    }

    private void verDashboard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Get parameters
        HttpSession session = req.getSession();
        LocalDate desde = (LocalDate) session.getAttribute("desde");
        LocalDate hasta = (LocalDate) session.getAttribute("hasta");

        if (desde == null && hasta == null) {
            desde = LocalDate.now().withDayOfMonth(1);
            hasta = LocalDate.now().plusDays(1);
            session.setAttribute("desde", desde);
            session.setAttribute("hasta", hasta);
        }

        // 2. Interact with the models
        CuentaDAO cuentaDAO = new CuentaDAO();
        CategoriaIngresoDAO categoriaIngresoDAO = new CategoriaIngresoDAO();
        CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
        MovimientoDAO movimientoDAO = new MovimientoDAO();

        List<Cuenta> cuentas = cuentaDAO.obtenerTodo();
        List<CategoriaIngreso> categoriasIngreso = categoriaIngresoDAO.obtenerTodo();
        List<CategoriaEgreso> categoriasEgreso = categoriaEgresoDAO.obtenerTodo();
        List<MovimientoDTO> movimientos = movimientoDAO.obtenerTodo(desde, hasta);

        Map<Integer, Double> valorCategoriasIngreso = calcularValorCategorias(categoriasIngreso, movimientoDAO, desde, hasta);
        Map<Integer, Double> valorCategoriasEgreso = calcularValorCategorias(categoriasEgreso, movimientoDAO, desde, hasta);

       // inicializarCategorias(cuentas);
        // 3. Send to the corresponding view
        req.setAttribute("cuentas", cuentas);
        req.setAttribute("categoriasIngreso", categoriasIngreso);
        req.setAttribute("categoriasEgreso", categoriasEgreso);
        req.setAttribute("movimientos", movimientos);
        req.setAttribute("valorCategoriasIngreso", valorCategoriasIngreso);
        req.setAttribute("valorCategoriasEgreso", valorCategoriasEgreso);

        req.getRequestDispatcher("jsp/VerDashboard.jsp").forward(req, resp);
    }

    private void filtrarPorFechas(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
//        1.
        String desdeStr = req.getParameter("desde");
        String hastaStr = req.getParameter("hasta");

        LocalDate desde = null;
        LocalDate hasta = null;

        if (desdeStr != null && !desdeStr.isEmpty()) {
            desde = LocalDate.parse(desdeStr);
        }
        if (hastaStr != null && !hastaStr.isEmpty()) {
            hasta = LocalDate.parse(hastaStr);
        }

//        3.
        HttpSession session = req.getSession();
        session.setAttribute("desde", desde);
        session.setAttribute("hasta", hasta);

        resp.sendRedirect("ContabilidadController?ruta=verDashboard");
    }

    private void verCuenta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        1.
        int idCuenta = Integer.parseInt(req.getParameter("idCuenta"));

//        2.
        CuentaDAO cuentaDAO = new CuentaDAO();
        Cuenta cuenta = cuentaDAO.obtenerCuentaPorId(idCuenta);

        MovimientoDAO movimientoDao = new MovimientoDAO();
        List<MovimientoDTO> movimientos = movimientoDao.obtenerMovimientosPorIdCuenta(idCuenta);

        req.setAttribute("cuenta", cuenta);
        req.setAttribute("movimientos", movimientos);

//        3.
        req.getRequestDispatcher("jsp/VerCuenta.jsp").forward(req, resp);
    }

    private void verMovimientos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        1.
        HttpSession session = req.getSession();

        LocalDate desde = (LocalDate) session.getAttribute("desde");
        LocalDate hasta = (LocalDate) session.getAttribute("hasta");

//        2.
        MovimientoDAO movimientoDAO = new MovimientoDAO();
        List<MovimientoDTO> movimientos = movimientoDAO.obtenerTodo(desde, hasta);

        req.setAttribute("movimientos", movimientos);

//        3.
        req.getRequestDispatcher("jsp/VerMovimientos.jsp").forward(req, resp);
    }

    private void eliminarMovimiento(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
//        1.
        int idMovimiento = Integer.parseInt(req.getParameter("id"));

//        2.
        MovimientoDAO movimientoDao = new MovimientoDAO();
        Movimiento movimiento = movimientoDao.obtenerMovimientoPorIdMovimiento(idMovimiento);

        double valor = movimiento.getValor();

        // Detele the movement
        movimientoDao.eliminarMovimiento(idMovimiento);

        // Update the balances according to the type of movements
        CuentaDAO cuentaDAO = new CuentaDAO();
        actualizarSaldoCuenta(movimiento, cuentaDAO, valor);

//        3.
        req.getRequestDispatcher("ContabilidadController?ruta=verDashboard").forward(req, resp);
    }

    private void actualizarMovimiento(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
//        1.
        int idMovimiento = Integer.parseInt(req.getParameter("id"));

//        2.
        MovimientoDAO movimientoDao = new MovimientoDAO();
        Movimiento movimiento = movimientoDao.obtenerMovimientoPorIdMovimiento(idMovimiento);

        // Get all categories for the type of movements
        List<Categoria> categoriasPorTipoMovimiento = obtenerCategoriasSegunTipoMovimiento(movimiento);

//        3.
        HttpSession session = req.getSession();

        session.setAttribute("movimiento", movimiento);
        session.setAttribute("categorias", categoriasPorTipoMovimiento);
        req.getRequestDispatcher("jsp/VerActualizarMovimiento.jsp").forward(req, resp);
    }


    private void registrarInfoActualizacion(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
//        1.
        HttpSession session = req.getSession();
        MovimientoDAO movimientoDAO = new MovimientoDAO();
        Movimiento movimiento = (Movimiento) session.getAttribute("movimiento");

        String concepto = req.getParameter("concepto");
        LocalDate fecha = LocalDate.parse(req.getParameter("fecha"));
        double valor = Double.parseDouble(req.getParameter("valor"));
        int idCategoria = Integer.parseInt(req.getParameter("categoria"));

//        2.
        CuentaDAO cuentaDAO = new CuentaDAO();

        if (movimiento instanceof Transferencia) {

            actualizarTransferencia(req, resp, movimientoDAO, (Transferencia) movimiento, concepto, fecha, valor, cuentaDAO, idCategoria);
        } else if (movimiento instanceof Ingreso) {
            actualizarIngreso(req,resp, movimientoDAO, (Ingreso) movimiento, concepto, fecha, valor, cuentaDAO, idCategoria);
        } else if (movimiento instanceof Egreso) {
            actualizarEgreso(req,resp, movimientoDAO, (Egreso) movimiento, concepto, fecha, valor, cuentaDAO, idCategoria);
        }

//        3.
        resp.sendRedirect("ContabilidadController?ruta=verDashboard");
    }

    private void verCategoria(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        1.
        int idCategoria = Integer.parseInt(req.getParameter("idCategoria"));
        HttpSession session = req.getSession();

        LocalDate desde = (LocalDate) session.getAttribute("desde");
        LocalDate hasta = (LocalDate) session.getAttribute("hasta");

//        2.
        CategoriaDAO categoriaDao = new CategoriaDAO();
        Categoria categoria = categoriaDao.obtenerCategoriaPorId(idCategoria);

        MovimientoDAO movimientoDao = new MovimientoDAO();
        List<MovimientoDTO> movimientos = movimientoDao.obtenerMovimientosPorIdCategoria(idCategoria, desde, hasta);

        double valor = 0.0;

//        Sum all the values of the movements
        if (!movimientos.isEmpty()) {
            for (MovimientoDTO movimiento : movimientos) {
                valor += movimiento.getValor();
            }
        }

        // Set the attributes in the request
        req.setAttribute("categoria", categoria);
        req.setAttribute("movimientos", movimientos);
        req.setAttribute("valor", valor);

//        3.
        req.getRequestDispatcher("jsp/VerCategoria.jsp").forward(req, resp);
    }


    private void registrarEgreso(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        1.
        int idCuenta = Integer.parseInt(req.getParameter("idCuenta"));

//        2.
        CuentaDAO cuentaDao = new CuentaDAO();
        Cuenta cuenta = cuentaDao.obtenerCuentaPorId(idCuenta);

        CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
        List<CategoriaEgreso> categoriasEgreso = categoriaEgresoDAO.obtenerTodo();

//        3.
        req.setAttribute("cuenta", cuenta);
        req.setAttribute("categoriasEgreso", categoriasEgreso);
        actualizarIdCuenta(req, cuenta);

        req.getRequestDispatcher("jsp/VerRegistrarEgreso.jsp").forward(req, resp);
    }


    private void ingresarInfoEgreso(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
//        1.
        HttpSession session = req.getSession();

        Cuenta cuenta = (Cuenta) session.getAttribute("cuenta");
        String concepto = String.valueOf(req.getParameter("concepto"));
        LocalDate fecha = LocalDate.parse((req.getParameter("fecha")));
        double valor = Double.parseDouble(req.getParameter("valor"));
        int idCategoria = Integer.parseInt(req.getParameter("idCategoria"));

        //It does not allow the expense if it exceeds the account balance
        if (esMovimientoMayorQueSaldoCuenta(valor, cuenta, req, "El valor del egreso no puede ser mayor que el saldo disponible.", resp)) {
            return;
        }

//        2.
        CategoriaEgresoDAO categoriaEgresoDao = new CategoriaEgresoDAO();
        CategoriaEgreso categoriaEgreso = categoriaEgresoDao.obtenerCategoriaPorId(idCategoria);

        Egreso egreso = new Egreso(concepto, fecha, -valor, cuenta, categoriaEgreso);

        EgresoDAO egresoDao = new EgresoDAO();
        egresoDao.guardarEgreso(egreso);

        CuentaDAO cuentaDao = new CuentaDAO();
        cuentaDao.actualizarSaldo(cuenta, -valor);

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

        actualizarIdCuenta(req, cuenta);
//        req.setAttribute("saldoCuenta", cuenta.getTotal());
        req.setAttribute("categoriasIngreso", categoriasIngreso);
        // req.setAttribute("idCuenta", cuenta.getId());

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

        Ingreso ingreso = new Ingreso(concepto, fecha, valor, cuenta, categoriaIngreso);

        IngresoDAO ingresoDao = new IngresoDAO();
        ingresoDao.guardarIngreso(ingreso);

        //update account balance
        CuentaDAO cuentaDao = new CuentaDAO();
        cuentaDao.actualizarSaldo(cuenta, valor);

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

        actualizarIdCuenta(req, cuenta);

//        3.
        req.setAttribute("cuentaOrigen", cuenta);
        req.setAttribute("cuentasDestino", cuentas);
        req.setAttribute("categoriasTransferencia", categoriasTransferencia);

        req.getRequestDispatcher("jsp/VerRegistrarTransferencia.jsp").forward(req, resp);
    }

    private void ingresarInfoTransferencia(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //        1.
        HttpSession session = req.getSession();

        Cuenta cuentaOrigen = (Cuenta) session.getAttribute("cuenta");
        int idCuentaDestino = Integer.parseInt(req.getParameter("idCuentaDestino"));
        String concepto = String.valueOf(req.getParameter("concepto"));
        LocalDate fecha = LocalDate.parse((req.getParameter("fecha")));
        double valor = Double.parseDouble(req.getParameter("valor"));

        int idCategoria = 1;

        if (esMovimientoMayorQueSaldoCuenta(valor, cuentaOrigen, req, "El valor de la transferencia no puede ser mayor que el saldo disponible.", resp))
            return;

//        2.
        CuentaDAO cuentaDao = new CuentaDAO();
        Cuenta cuentaDestino = cuentaDao.obtenerCuentaPorId(idCuentaDestino);

        CategoriaTransferenciaDAO categoriaTransferenciaDao = new CategoriaTransferenciaDAO();
        CategoriaTransferencia categoriaTransferencia = categoriaTransferenciaDao.obtenerCategoriaPorId(idCategoria);

        Transferencia transferencia = new Transferencia(concepto, fecha, valor, cuentaDestino, cuentaOrigen, categoriaTransferencia);

        TransferenciaDAO transferenciaDao = new TransferenciaDAO();
        transferenciaDao.guardarTransferencia(transferencia);

        cuentaDao.actualizarSaldo(cuentaOrigen, -valor);
        cuentaDao.actualizarSaldo(cuentaDestino, valor);

//        3.
        resp.sendRedirect("ContabilidadController?ruta=verDashboard");
    }


    private void cancelar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("ContabilidadController?ruta=verDashboard");
    }


//    Support methods

    private void actualizarIdCuenta(HttpServletRequest req, Cuenta nuevaCuenta) {
        HttpSession session = req.getSession();

        Integer idCuentaExistente = (Integer) session.getAttribute("idCuenta");

        if ((idCuentaExistente == null) || (idCuentaExistente != nuevaCuenta.getId())) {
            // Si el idCuenta no está en la sesión o es diferente, actualiza la sesión
            session.setAttribute("cuenta", nuevaCuenta);
            session.setAttribute("idCuenta", nuevaCuenta.getId());
        }
    }

    private void actualizarSaldoCuenta(Movimiento movimiento, CuentaDAO cuentaDAO, double valor) {
        if (movimiento instanceof Ingreso) {
            Cuenta cuentaDestino = ((Ingreso) movimiento).getCuentaDestino();
            cuentaDAO.actualizarSaldo(cuentaDestino, -valor);
        } else if (movimiento instanceof Egreso) {
            Cuenta cuentaOrigen = ((Egreso) movimiento).getCuentaOrigen();
            cuentaDAO.actualizarSaldo(cuentaOrigen, -valor);
        } else if (movimiento instanceof Transferencia) {
            Cuenta cuentaDestino = ((Transferencia) movimiento).getCuentaDestino();
            Cuenta cuentaOrigen = ((Transferencia) movimiento).getCuentaOrigen();
            cuentaDAO.actualizarSaldo(cuentaDestino, -valor);
            cuentaDAO.actualizarSaldo(cuentaOrigen, valor);
        }
    }

    private void inicializarCategorias(List<Cuenta> cuentas) {
        CategoriaIngresoDAO catdao = new CategoriaIngresoDAO();
        CategoriaIngreso categoria = new CategoriaIngreso("SUELDO");
        catdao.ingresar(categoria);
        CategoriaIngreso categoria1 = new CategoriaIngreso("BECA");
        catdao.ingresar(categoria1);
        CategoriaIngreso categoria2 = new CategoriaIngreso("COMISIONES");
        catdao.ingresar(categoria2);
       /* Ingreso ingreso = new Ingreso("CHAUCHA", LocalDate.now(), 2, cuentas.get(4), categoria);
        IngresoDAO ingresoDAO = new IngresoDAO();
        ingresoDAO.guardarIngreso(ingreso);*/

        CategoriaEgresoDAO catdaoegre = new CategoriaEgresoDAO();
        CategoriaEgreso categoriaEgre = new CategoriaEgreso("PLAYITA");
        catdaoegre.ingresar(categoriaEgre);
        CategoriaEgreso categoriaEgre1 = new CategoriaEgreso("SEGUNDA MATRICULA");
        catdaoegre.ingresar(categoriaEgre1);
        CategoriaEgreso categoriaEgre2 = new CategoriaEgreso("CASA DE CITAS");
        catdaoegre.ingresar(categoriaEgre2);
    /*    Egreso egreso = new Egreso("tonsupaFest", LocalDate.now(), 12.2, cuentas.get(2), categoriaEgre);
        EgresoDAO egresoDAO = new EgresoDAO();
        egresoDAO.guardarEgreso(egreso);
*/
        CategoriaTransferencia categoriaTrans = new CategoriaTransferencia("TRANSFERENCIA ENTRE BANCOS");
        CategoriaTransferenciaDAO categoriaTransDAO = new CategoriaTransferenciaDAO();
        categoriaTransDAO.ingresar(categoriaTrans);
      /*  Transferencia trans = new Transferencia("TRANSFERENCIA ACTUALIZAR", LocalDate.now(), 12.2, cuentas.get(1), cuentas.get(2), categoriaTrans);
        TransferenciaDAO transDAO = new TransferenciaDAO();
        transDAO.guardarTransferencia(trans);*/
    }

    private List<Categoria> obtenerCategoriasSegunTipoMovimiento(Movimiento movimiento) {
        List<Categoria> categorias = new ArrayList<>();
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
        return categorias;
    }



    private void actualizarTransferencia(HttpServletRequest req, HttpServletResponse resp, MovimientoDAO movimientoDAO, Transferencia transferencia,
                                         String concepto, LocalDate fecha, double valor, CuentaDAO cuentaDAO, int idCategoria) throws ServletException, IOException {
        int idCuentaOrigen = transferencia.getCuentaOrigen().getId();
        int idCuentaDestino = transferencia.getCuentaDestino().getId();
        double valorAntiguo = transferencia.getValor();

        double valorCuentaOrigen = transferencia.getCuentaOrigen().getTotal();

        if (valorCuentaOrigen < valor) {
            req.setAttribute("error", "El saldo es insuficiente para realizar la transferencia");
            req.getRequestDispatcher("jsp/Error.jsp").forward(req, resp);
            return;
        }

        double valorDiferencia = valor - valorAntiguo;

        transferencia.setConcepto(concepto);
        transferencia.setFecha(fecha);
        transferencia.setValor(valor);
        transferencia.setCuentaOrigen(cuentaDAO.obtenerCuentaPorId(idCuentaOrigen));
        transferencia.setCuentaDestino(cuentaDAO.obtenerCuentaPorId(idCuentaDestino));
        transferencia.setCategoria(new CategoriaTransferenciaDAO().obtenerCategoriaPorId(idCategoria));

        actualizarSaldoCuenta(transferencia, cuentaDAO, -valorDiferencia);

        movimientoDAO.actualizarMovimiento(transferencia);
    }


    private void actualizarIngreso(HttpServletRequest req, HttpServletResponse resp,  MovimientoDAO movimientoDAO, Ingreso ingreso,
                                   String concepto, LocalDate fecha, double valor, CuentaDAO cuentaDAO, int idCategoria) {
        int idCuentaDestino = ingreso.getCuentaDestino().getId();
        double valorAntiguo = ingreso.getValor();
        double valorDiferencia = valor - valorAntiguo;

        ingreso.setConcepto(concepto);
        ingreso.setFecha(fecha);
        ingreso.setValor(valor);
        ingreso.setCuentaDestino(cuentaDAO.obtenerCuentaPorId(idCuentaDestino));
        ingreso.setCategoria(new CategoriaIngresoDAO().obtenerCategoriaPorId(idCategoria));

        cuentaDAO.actualizarSaldo(ingreso.getCuentaDestino(), valorDiferencia);

        movimientoDAO.actualizarMovimiento(ingreso);
    }

    private void actualizarEgreso(HttpServletRequest req, HttpServletResponse resp, MovimientoDAO movimientoDAO, Egreso egreso,
                                  String concepto, LocalDate fecha, double valor, CuentaDAO cuentaDAO, int idCategoria) throws ServletException, IOException {
        int idCuentaOrigen = egreso.getCuentaOrigen().getId();
        double valorAntiguo = egreso.getValor();

        double valorCuentaOrigen = egreso.getCuentaOrigen().getTotal();

        if (valorCuentaOrigen < valor) {
            req.setAttribute("error", "El saldo es insuficiente para realizar la transferencia");

            req.getRequestDispatcher("jsp/Error.jsp").forward(req, resp);
            return;
        }
        //17
        valorAntiguo = valorAntiguo * (-1);
        //10
        // / 6 - 7   = -1
        // / 8 - 7   = 1
        double valorDiferencia =   valor - valorAntiguo;
        valorDiferencia = valorDiferencia * (-1);

        egreso.setConcepto(concepto);
        egreso.setFecha(fecha);
        egreso.setValor(-valor);
        egreso.setCuentaOrigen(cuentaDAO.obtenerCuentaPorId(idCuentaOrigen));
        egreso.setCategoria(new CategoriaEgresoDAO().obtenerCategoriaPorId(idCategoria));

        cuentaDAO.actualizarSaldo(egreso.getCuentaOrigen(), valorDiferencia);

        movimientoDAO.actualizarMovimiento(egreso);
    }

    private boolean esMovimientoMayorQueSaldoCuenta(double valor, Cuenta cuenta, HttpServletRequest req, String o, HttpServletResponse resp) throws ServletException, IOException {
        if (valor > cuenta.getTotal()) {
            // Redirigir o enviar un mensaje de error
            req.setAttribute("error", o);
            req.getRequestDispatcher("jsp/Error.jsp").forward(req, resp);
            return true;
        }
        return false;
    }


    private <T extends Categoria> Map<Integer, Double> calcularValorCategorias(List<T> categorias, MovimientoDAO movimientoDAO, LocalDate desde, LocalDate hasta) {
        Map<Integer, Double> valorCategorias = new HashMap<>();

        for (T categoria : categorias) {
            List<MovimientoDTO> movimientosCategoria = movimientoDAO.obtenerMovimientosPorIdCategoria(categoria.getID(), desde, hasta);
            double valorTotal = movimientosCategoria.stream()
                    .filter(movimiento -> (movimiento.getFecha().isEqual(desde) || movimiento.getFecha().isAfter(desde)) &&
                            (movimiento.getFecha().isEqual(hasta) || movimiento.getFecha().isBefore(hasta)))
                    .mapToDouble(MovimientoDTO::getValor)
                    .sum();

            valorCategorias.put(categoria.getID(), valorTotal);
        }

        return valorCategorias;
    }
}
