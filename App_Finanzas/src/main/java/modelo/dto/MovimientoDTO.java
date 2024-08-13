package modelo.dto;

import modelo.dao.CuentaDAO;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class MovimientoDTO implements Serializable {
    //paramentros que dese mostrar en el jsp
    private String id;
    private LocalDate fecha;
    private String concepto;
    private double valor;
    private String cuentaOrigen;
    private String cuentaDestino;
    private String TipoDeMovimiento;


    public MovimientoDTO(String id, LocalDate fecha, String concepto, double valor, String cuentaOrigen, String cuentaDestino) {
        this.id = id;
        this.fecha = fecha;
        this.concepto = concepto;
        this.valor = valor;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
    }

    // Getter para cuentaOrigen
    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    // Setter para cuentaOrigen
    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    // Getter para cuentaDestino
    public String getCuentaDestino() {
        return cuentaDestino;
    }

    // Setter para cuentaDestino
    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

}
