package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.entidades.Ingreso;

import java.io.Serializable;

public class IngresoDAO extends MovimientoDAO implements Serializable {
    private static final long serialVersionUID = 1L;
    private EntityManagerFactory emf;

    public IngresoDAO() {
        emf = Persistence.createEntityManagerFactory("chaucherita_PU");
    }

    public void guardarIngreso(Ingreso ingreso) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(ingreso);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
