package modelo.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@DiscriminatorValue("INGRESO")
public class Ingreso extends Movimiento implements Serializable {


    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Cuenta CuentaDestino;
    @ManyToOne
    @JoinColumn(name = "Categoria_ID")
    private CategoriaIngreso categoria;

    public Ingreso() {}

    public Ingreso(String concepto, LocalDate fecha, double valor, Cuenta cuentaDestino, CategoriaIngreso categoria) {
        super(concepto, fecha, valor);
        CuentaDestino = cuentaDestino;
        this.categoria = categoria;
    }

    public Cuenta getCuentaDestino() {
        return CuentaDestino;
    }

    public void setCuentaDestino(Cuenta cuentaDestino) {
        CuentaDestino = cuentaDestino;
    }

    public CategoriaIngreso getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaIngreso categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Ingreso";
    }
}
