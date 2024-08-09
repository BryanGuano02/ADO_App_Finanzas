package modelo.entidades;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Movimiento {
    private String concepto;
    private LocalDate fecha;
    private double valor;

    public Movimiento() {
    }

    public Movimiento(String concepto, LocalDate fecha, double valor) {
        this.concepto = concepto;
        this.fecha = fecha;
        this.valor = valor;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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

    public void eliminarMovimiento(int i0dMovimiento){

    }

    public void guardarMovimiento(Movimiento movimiento){

    }

    public List<Movimiento> obtenerMovimientosPorIdCategoria(int idCategoria){
        return null;
    }
}
