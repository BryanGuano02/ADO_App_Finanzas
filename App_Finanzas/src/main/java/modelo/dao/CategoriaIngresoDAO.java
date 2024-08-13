package modelo.dao;

import jakarta.persistence.*;
import modelo.entidades.CategoriaIngreso;

import java.io.Serializable;
import java.util.List;


public class CategoriaIngresoDAO implements Serializable {
    private static final long serialVersionUID = 1L;

    private EntityManagerFactory emf = null;
    private EntityManager em = null;

    public CategoriaIngresoDAO() {
        emf = Persistence.createEntityManagerFactory("chaucherita_PU");
        em = emf.createEntityManager();
    }

    public void ingresar(CategoriaIngreso categoriaIngreso) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(categoriaIngreso);

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

    public List<CategoriaIngreso> obtenerTodo() {
        List<CategoriaIngreso> categoriasIngreso = null;
        try {
            // Get all categories
            String jpql = "SELECT ci FROM CategoriaIngreso ci";
            TypedQuery<CategoriaIngreso> query = em.createQuery(jpql, CategoriaIngreso.class);

            categoriasIngreso = query.getResultList();
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
        return categoriasIngreso;
    }

    public CategoriaIngreso obtenerCategoriaPorId(int idCategoria) {
        CategoriaIngreso categoriaIngreso = null;

        try {
            em = emf.createEntityManager();

            String jpql = "SELECT ci FROM CategoriaIngreso ci WHERE ci.ID = :idCategoria ";

            TypedQuery<CategoriaIngreso> query = em.createQuery(jpql, CategoriaIngreso.class);
            query.setParameter("idCategoria", idCategoria);

            categoriaIngreso = query.getSingleResult();
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

        return categoriaIngreso;
    }

}

