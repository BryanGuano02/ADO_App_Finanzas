package modelo.entidades;

import jakarta.persistence.Entity;

import java.io.Serializable;

@Entity
public class CategoriaEgreso extends Categoria implements Serializable {
    private static final long serialVersionUID = 1L;

    public CategoriaEgreso() {
    }

    public CategoriaEgreso( String nombre) {
        super(nombre);
    }
}