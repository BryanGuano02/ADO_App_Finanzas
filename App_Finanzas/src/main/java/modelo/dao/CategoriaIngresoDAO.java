package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.entidades.CategoriaIngreso;
import modelo.entidades.Cuenta;

import java.time.LocalDate;
import java.util.List;

public class CategoriaIngresoDAO extends CategoriaDAO {
    private EntityManagerFactory emf = null;
    private EntityManager em = null;

    public CategoriaIngresoDAO() {
        this.emf = Persistence.createEntityManagerFactory("Contabilidad");
        this.em = emf.createEntityManager();
    }



}
