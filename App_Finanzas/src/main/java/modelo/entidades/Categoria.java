package modelo.entidades;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "Categoria")
@DiscriminatorColumn(name = "tipo_Categoria", discriminatorType = DiscriminatorType.STRING)
public class Categoria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;
    private String nombre;
    private LocalDate fechaCreacion;
    private Double total;


    public Categoria() {

    }

    public Categoria(LocalDate fechaCreacion, Double total, String nombre, Integer ID) {
        this.ID = ID;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.total = total;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Double getValue() {
        return total;
    }

    public void setValue(Double total) {
        this.total = total;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }
}
