package modelo.entidades;

import jakarta.persistence.Entity;

import java.time.LocalDate;
@Entity
public class CategoriaTransferencia extends Categoria{

    public CategoriaTransferencia(LocalDate fechaCreacion, Double total, String nombre, Integer ID) {
        super(fechaCreacion, total, nombre, ID);
    }


}
