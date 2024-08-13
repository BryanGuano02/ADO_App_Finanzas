package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.entidades.CategoriaEgreso;
import java.io.Serializable;
import java.util.List;


public class CategoriaEgresoDAO implements Serializable {

    private static final long serialVersionUID = 1L;
    private EntityManagerFactory emf = null;
    private EntityManager em = null;

    public CategoriaEgresoDAO() {
        emf = Persistence.createEntityManagerFactory("chaucherita_PU");
        em = emf.createEntityManager();
    }

    public CategoriaEgreso obtenerCategoriaPorId(int idCategoria) {
        CategoriaEgreso categoriaEgreso = null;

        try {
            em = emf.createEntityManager();

            String jpql = "SELECT ce FROM CategoriaEgreso ce WHERE ce.ID = :idCategoria ";

            TypedQuery<CategoriaEgreso> query = em.createQuery(jpql, CategoriaEgreso.class);
            query.setParameter("idCategoria", idCategoria);

            categoriaEgreso = query.getSingleResult();

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

        return categoriaEgreso;
    }

    public List<CategoriaEgreso> obtenerTodo() {
        List<CategoriaEgreso> categoriasEgreso = null;

        try {
            String jpql = "SELECT ce FROM CategoriaEgreso ce";

            TypedQuery<CategoriaEgreso> query = em.createQuery(jpql, CategoriaEgreso.class);

            categoriasEgreso = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            if (em != null && em.getTransaction().isActive())
                em.getTransaction().rollback();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        }
        return categoriasEgreso;
    }

    public void ingresar(CategoriaEgreso categoriaEgreso) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(categoriaEgreso);

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
