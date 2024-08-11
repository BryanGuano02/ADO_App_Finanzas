package modelo.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@DiscriminatorValue("Transferencia")
public class Transferencia extends Movimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManyToOne
    private Cuenta CuentaDestino;

    @ManyToOne
    private Cuenta CuentaOrigen;

    @ManyToOne
    @JoinColumn(name = "Categoria_ID")
    private CategoriaTransferencia categoria;

    public Transferencia() {}

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

    @Override
    public String toString() {
        return "Transferencia";
    }
}