package modelo.entidades;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
public class CategoriaIngreso extends Categoria implements Serializable {

    private static final long serialVersionUID = 1L;
   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Idingreso;*/



    public CategoriaIngreso() {
    }

    public CategoriaIngreso( String nombre) {
        super(nombre);
    }

}