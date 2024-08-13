package modelo.entidades;

import jakarta.persistence.Entity;

import java.io.Serializable;

@Entity
public class CategoriaTransferencia extends Categoria implements Serializable {
    private static final long serialVersionUID = 1L;

    public CategoriaTransferencia() {

    }

    public CategoriaTransferencia(String nombre) {
        super(nombre);
    }
}
