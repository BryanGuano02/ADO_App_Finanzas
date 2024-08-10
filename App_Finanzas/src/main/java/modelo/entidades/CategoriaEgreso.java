package modelo.entidades;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class CategoriaEgreso extends Categoria {
    private List<CategoriaEgreso> categoriasEgreso = null;
//    constructor??


    public CategoriaEgreso(LocalDate fechaCreacion, Double value, String nombre, Integer ID) {
        super(fechaCreacion, value, nombre, ID);

    }

    public List<CategoriaEgreso> obtenerTodoPorFecha(Date desde, Date hasta){
        return null;
    }
}
