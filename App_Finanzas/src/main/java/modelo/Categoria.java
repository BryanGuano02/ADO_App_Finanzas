package modelo;

import java.util.List;

public class Categoria {
    private int id;
    private String nombre;

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

    public void actualizarSaldo(double valor){

    }
}
