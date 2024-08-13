package modelo.entidades;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="Cuenta")
public class Cuenta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nombre;
	private double total;

	public Cuenta() {
	}

	public Cuenta(int id, String nombre, double total) {
		this.id = id;
		this.nombre = nombre;
		this.total = total;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
}
