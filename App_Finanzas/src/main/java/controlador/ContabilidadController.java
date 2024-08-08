package controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
		String ruta = (req.getParameter("ruta") != null)? "verCuenta": req.getParameter("ruta");
		
		switch (ruta) {
		case "verDashboard":
			this.verDashboard(req, resp);
			break;
		case "verCuenta":
			this.verCuenta(req, resp);
			break;
		}
	}

	private void verDashboard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.print("HOla");
	}

	private void verCuenta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	private void verMovimientos(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	private void verCategoria(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	private void eliminarMovimiento(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

	private void confirmarEliminacion(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

	private void actualizarMovimiento(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

	private void confirmarActualizacion(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

	private void registrarlnfoActualizacion(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

	private void registrarGasto(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	private void ingresarlnfoGasto(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

	private void registrarlngreso(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

	private void ingresarlnfolngreso(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

	private void registrarTransferencia(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

	private void ingresarlnfoTransferencia(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

}
