package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.entidades.CategoriaEgreso;
import modelo.entidades.CategoriaIngreso;
import modelo.entidades.CategoriaTransferencia;

import java.util.List;

public class CategoriaTransferenciaDAO {
    private EntityManagerFactory emf = null;
    private EntityManager em = null;

    public CategoriaTransferenciaDAO() {
        emf = Persistence.createEntityManagerFactory("chaucherita_PU");
        em = emf.createEntityManager();
    }

    public List<CategoriaTransferencia> obtenerTodo() {
        List<CategoriaTransferencia> categoriasTransferencia = null;
        try {

            String jpql = "SELECT ct FROM CategoriaTransferencia ct";
            TypedQuery<CategoriaTransferencia> query = em.createQuery(jpql, CategoriaTransferencia.class);

            categoriasTransferencia = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        }
        return categoriasTransferencia;
    }

    public CategoriaTransferencia obtenerCategoriaPorId(int idCategoria) {
        CategoriaTransferencia categoriaTransferencia = null;

        try {
            em = emf.createEntityManager();

            String jpql = "SELECT ct FROM CategoriaTransferencia ct WHERE ct.ID = :idCategoria ";

            TypedQuery<CategoriaTransferencia> query = em.createQuery(jpql, CategoriaTransferencia.class);
            query.setParameter("idCategoria", idCategoria);

            categoriaTransferencia =  query.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        }

        return categoriaTransferencia;
    }

    public void ingresar(CategoriaTransferencia categoriaTrans) {
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(categoriaTrans);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
