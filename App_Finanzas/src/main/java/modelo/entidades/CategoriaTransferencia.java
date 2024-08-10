package modelo.entidades;

import jakarta.persistence.Entity;

import java.io.Serializable;
import java.time.LocalDate;
@Entity
public class CategoriaTransferencia extends Categoria implements Serializable {


    public CategoriaTransferencia(  String nombre, Integer ID) {
        super( nombre, ID);
    }


    public CategoriaTransferencia() {

    }
}
