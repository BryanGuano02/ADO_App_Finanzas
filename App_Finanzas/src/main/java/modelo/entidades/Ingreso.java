package modelo.entidades;

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

    // Getters y Setters
    public Cuenta getCuentaOrigen() {
        return cuentaOrigen;
    }


    public void setCuentaOrigen(Cuenta cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

}
