package modelo.recursos;

import java.util.ArrayList;
import java.util.List;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import modelo.dao.*;
import modelo.entidades.*;

@Path("/contabilidad")
public class RecursoContabilidad {

    @GET
    @Path("/cuentas")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cuenta> obtenerCuentas(){
        CuentaDAO cuentaDao = new CuentaDAO();
        return cuentaDao.obtenerTodo();
    }

    @GET
    @Path("/categorias")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Categoria> obtenerCategorias() {
        CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
        CategoriaIngresoDAO categoriaIngresoDAO = new CategoriaIngresoDAO();
        CategoriaTransferenciaDAO categoriaTransferenciaDAO = new CategoriaTransferenciaDAO();

        List<Categoria> categoriasRecopiladas = new ArrayList<>();
        categoriasRecopiladas.addAll(categoriaEgresoDAO.obtenerTodo());
        categoriasRecopiladas.addAll(categoriaIngresoDAO.obtenerTodo());
        categoriasRecopiladas.addAll(categoriaTransferenciaDAO.obtenerTodo());

        return categoriasRecopiladas;
    }
//hola
    
    @GET
    @Path("cuenta/{idCuenta}")
    @Produces(MediaType.APPLICATION_JSON)
    public Cuenta obtenerCuentaPorId(@PathParam("idCuenta") int idCuenta) {
        CuentaDAO cuentaDAO = new CuentaDAO();
        return cuentaDAO.obtenerCuentaPorId(idCuenta);
    }

}
