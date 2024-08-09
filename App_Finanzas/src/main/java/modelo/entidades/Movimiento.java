package modelo.entidades;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;



@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Movimiento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "valor")
    private Double valor;
    @Column(name = "concepto")
    private String concepto;
    @Column(name = "fecha")
    private LocalDate fecha;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "Cuenta_origen")
    private Cuenta Cuenta_Origen;


    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "cuenta_destino")
    private Cuenta cuenta_Destino;



    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public Movimiento() {
    }


    public Movimiento(Double valor, Integer id, LocalDate fecha, Cuenta cuenta_Origen, Cuenta cuenta_Destino, String concepto, Categoria categoria) {
        this.valor = valor;
        this.id = id;
        this.fecha = fecha;
        this.Cuenta_Origen = cuenta_Origen;
        this.cuenta_Destino = cuenta_Destino;
        this.concepto = concepto;
        this.categoria = categoria;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public List<Movimiento> movimientos(Date desde, Date hasta){
        return null;
    }

    public List<Movimiento> obtenerMovimientosPorIdCuenda(int idCuenta){
        return null;
    }

    public Movimiento obtenerMovimientoPorIdMovimiento(int idMovimiento){
        return null;

    }

    public void eliminarMovimiento(int i0dMovimiento){

    }

    public void guardarMovimiento(Movimiento movimiento){

    }

    public List<Movimiento> obtenerMovimientosPorIdCategoria(int idCategoria){
        return null;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
