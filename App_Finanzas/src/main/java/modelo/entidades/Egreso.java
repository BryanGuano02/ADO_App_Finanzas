package modelo.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.time.LocalDate;


@Entity
@DiscriminatorValue("EGRESO")
public class Egreso extends Movimiento implements Serializable {


    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name = "CuentaOrigen_ID")
    private Cuenta CuentaOrigen;


    @ManyToOne
    @JoinColumn(name = "Categoria_ID")
    private CategoriaEgreso categoria;

    public Egreso() {}

    public Egreso( String concepto, LocalDate fecha, double valor, Cuenta cuentaOrigen, CategoriaEgreso categoria) {
        super( concepto, fecha, valor );
        CuentaOrigen = cuentaOrigen;
        this.categoria = categoria;
    }

    public Cuenta getCuentaOrigen() {
        return CuentaOrigen;
    }

    public void setCuentaOrigen(Cuenta cuentaOrigen) {
        CuentaOrigen = cuentaOrigen;
    }

    public CategoriaEgreso getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaEgreso categoria) {
        this.categoria = categoria;
    }



    @Override
    public String toString() {
        return "Egreso";
    }
}

