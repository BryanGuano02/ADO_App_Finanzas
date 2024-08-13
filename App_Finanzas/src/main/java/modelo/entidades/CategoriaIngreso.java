package modelo.entidades;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class CategoriaIngreso extends Categoria implements Serializable {

    private static final long serialVersionUID = 1L;

    public CategoriaIngreso() {
    }

    public CategoriaIngreso( String nombre) {
        super(nombre);
    }
}