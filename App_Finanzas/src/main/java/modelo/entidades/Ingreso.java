package modelo.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;


@Entity
public class Ingreso extends Movimiento implements Serializable {
    @OneToOne
    @JoinColumn(name = "id") // Esto define la columna de la clave foránea en la tabla "Ingreso"
    private Cuenta cuentaOrigen;
    private Double valor;
    private Date fecha;

    public Ingreso() {}

    public Ingreso(Double valor, Integer id, LocalDate fecha, Cuenta cuentaOrigen, Cuenta cuentaDestino, String concepto, Categoria categoria, Date fecha1, Double valor1, Cuenta cuentaOrigen1) {
        super(valor, id, fecha, cuentaOrigen, cuentaDestino, concepto, categoria);
        this.fecha = fecha1;
        this.valor = valor1;
        this.cuentaOrigen = cuentaOrigen1;
    }

    // Getters y Setters
    public Cuenta getCuentaOrigen() {
        return cuentaOrigen;
    }


    public void setCuentaOrigen(Cuenta cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

}
