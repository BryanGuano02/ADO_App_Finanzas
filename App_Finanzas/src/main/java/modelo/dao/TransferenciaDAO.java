package modelo.dao;

import jakarta.persistence.*;
import modelo.entidades.Cuenta;
import modelo.entidades.Egreso;
import modelo.entidades.Transferencia;

import java.io.Serializable;

public class TransferenciaDAO implements Serializable {
    private static final long serialVersionUID = 1L;
    private EntityManagerFactory emf = null;
    private EntityManager em = null;

    public TransferenciaDAO() {
        emf = Persistence.createEntityManagerFactory("chaucherita_PU");
        em = emf.createEntityManager();
    }

    public void guardarTransferencia(Transferencia transferencia) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(transferencia);
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
