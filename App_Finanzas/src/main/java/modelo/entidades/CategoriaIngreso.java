package modelo.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoriaIngreso extends Categoria {
    private static List<CategoriaIngreso> categoriasIngreso = null;
    private List<Movimiento> movimientos = null;

    public CategoriaIngreso() {
    }

    public CategoriaIngreso(int id, String nombre) {
        super(id, nombre);
    }

    public static List<CategoriaIngreso> getCategoriasIngreso() {
        return categoriasIngreso;
    }

    public void setCategoriasIngreso(List<CategoriaIngreso> categoriasIngreso) {
        this.categoriasIngreso = categoriasIngreso;
    }


    public List<CategoriaIngreso> obtenerCategoriasIngreso() {
        if (categoriasIngreso == null) {
            categoriasIngreso = new ArrayList<CategoriaIngreso>();

            categoriasIngreso.add(new CategoriaIngreso(1, "Sueldo"));
            categoriasIngreso.add(new CategoriaIngreso(2, "Sueldo2"));
        }

        return categoriasIngreso;
    }

    public static List<CategoriaIngreso> obtenerTodoPorFecha(LocalDate desde, LocalDate hasta) {
        List<CategoriaIngreso> categoriasFiltradas = new ArrayList<>();

        for (CategoriaIngreso categoriaIngreso : getCategoriasIngreso()) {
            List<Movimiento> movimientos = categoriaIngreso.getMovimientos();
            boolean tieneMovimientosEnRango = false;

            for (Movimiento movimiento : movimientos) {
                LocalDate fechaMovimiento = movimiento.getFecha();
                if ((fechaMovimiento.equals(desde) || fechaMovimiento.isAfter(desde)) &&
                        (fechaMovimiento.equals(hasta) || fechaMovimiento.isBefore(hasta))) {
                    tieneMovimientosEnRango = true;
                    break;
                }
            }

            if (tieneMovimientosEnRango) {
                categoriasFiltradas.add(categoriaIngreso);
            }
        }

        return categoriasFiltradas;
    }
}