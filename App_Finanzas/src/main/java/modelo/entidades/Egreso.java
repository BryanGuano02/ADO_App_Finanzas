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



    public Egreso(Double valor, Integer id, LocalDate fecha, Cuenta cuentaOrigen, Cuenta cuentaDestino, String concepto, Categoria categoria) {
        super(valor, id, fecha, cuentaOrigen, cuentaDestino, concepto, categoria);
        this.cuentaOrigen = cuentaOrigen;
//     tthis.cuentaOrigen = cuentaOrigen;
    }

    public Egreso() {

    }

    // Getters y Se;
}

