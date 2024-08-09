package modelo.entidades;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

public class Egreso extends Movimiento{
    @OneToOne
    @JoinColumn(name = "cuenta_origen_id") // Esto define la columna de la clave foránea en la tabla "Ingreso"
    private Cuenta cuentaDestino;

    // Getters y Setters
    public Cuenta getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(Cuenta cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

}
