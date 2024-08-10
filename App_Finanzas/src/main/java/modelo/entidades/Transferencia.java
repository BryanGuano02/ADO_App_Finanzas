package modelo.entidades;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Transferencia extends Movimiento implements Serializable  {
    @OneToOne
    @JoinColumn(name = "id") // Esto define la columna de la clave foránea en la tabla "Ingreso"
    private Cuenta cuentaOrigen;

    @OneToOne
    @JoinColumn(name = "id") // Esto define la columna de la clave foránea en la tabla "Ingreso"
    private Cuenta cuentaDestino;


    public Transferencia( String concepto, LocalDate fecha, double valor, Cuenta cuentaDestino, Cuenta cuentaOrigen, CategoriaTransferencia categoria) {
        super( concepto, fecha, valor);
        CuentaDestino = cuentaDestino;
        CuentaOrigen = cuentaOrigen;
        this.categoria = categoria;
    }


    public Cuenta getCuentaDestino() {
        return CuentaDestino;
    }

    public void setCuentaDestino(Cuenta cuentaDestino) {
        this.CuentaDestino = cuentaDestino;
    }

    public Cuenta getCuentaOrigen() {
        return CuentaOrigen;
    }

    public void setCuentaOrigen(Cuenta cuentaOrigen) {
        this.CuentaOrigen = cuentaOrigen;
    }

    public CategoriaTransferencia getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaTransferencia categoria) {
        this.categoria = categoria;
    }
}