package modelo;

import java.util.Date;
import java.util.List;

public class Movimiento {
    private String concepto;
    private Date fecha;
    private double valor;

    public Movimiento() {
    }

    public Movimiento(String concepto, Date fecha, double valor) {
        this.concepto = concepto;
        this.fecha = fecha;
        this.valor = valor;
    }

    public List<Movimiento> movimientos(Date desde, Date hasta){
        return null;
    }

    public List<Movimiento> obtenerMovimientosPorIdCuenda(int idCuenta){
        return null;
    }

    public Movimiento obtenerMovimientoPorIdMovimiento(int idMovimiento){
        return null;
    }

    public void eliminarMovimiento(int idMovimiento){

    }

    public void guardarMovimiento(Movimiento movimiento){

    }

    public List<Movimiento> obtenerMovimientosPorIdCategoria(int idCategoria){
        return null;
    }
}
