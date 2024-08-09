package modelo.entidades;

import java.util.Date;
import java.util.List;

public class Categoria {
    private int id;
    private String nombre;
    private Date fechaCreacion;
    private List<Movimiento> movimientos;

    private List<Categoria> categorias = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public Categoria() {
    }

    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Categoria obtenerCategoriaPorId(int idCategoria) {
        if (categorias == null) {
            return null;
        }

        for (Categoria categoria : categorias) {
            if (categoria != null && categoria.getId() == idCategoria) {
                return categoria;
            }
        }

        return null;
    }

    public void actualizarSaldo(double valor) {

    }
}
