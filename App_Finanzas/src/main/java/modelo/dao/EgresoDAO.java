package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.entidades.Egreso;

import java.io.Serializable;

public class EgresoDAO implements Serializable {
    private static final long serialVersionUID = 1L;
    private EntityManagerFactory emf;

    public EgresoDAO() {
        emf = Persistence.createEntityManagerFactory("chaucherita_PU");
    }

    public void guardarEgreso(Egreso egreso) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(egreso);
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
