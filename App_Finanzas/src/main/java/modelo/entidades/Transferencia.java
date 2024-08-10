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





    public Transferencia() {

    }
}