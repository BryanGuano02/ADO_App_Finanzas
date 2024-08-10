package modelo.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.io.Serializable;
import java.time.LocalDate;


@Entity
public class Egreso extends Movimiento implements Serializable {


    @OneToOne
    @JoinColumn(name = "id") // Esto define la columna de la clave foránea en la tabla "Ingreso"
    private Cuenta cuentaOrigen;



    public Egreso( String concepto, LocalDate fecha, double valor, Cuenta cuentaOrigen, CategoriaEgreso categoria) {
        super( concepto, fecha, valor);
        CuentaOrigen = cuentaOrigen;
        Categoria = categoria;
    }

    public Cuenta getCuentaOrigen() {
        return CuentaOrigen;
    }

    public void setCuentaOrigen(Cuenta cuentaOrigen) {
        CuentaOrigen = cuentaOrigen;
    }

    public CategoriaEgreso getCategoria() {
        return Categoria;
    }

    public void setCategoria(CategoriaEgreso categoria) {
        Categoria = categoria;
    }
}

