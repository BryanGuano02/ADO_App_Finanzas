package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.entidades.CategoriaEgreso;
import modelo.entidades.CategoriaIngreso;

import java.io.Serializable;
import java.util.List;


public class CategoriaIngresoDAO implements Serializable {
    private static final long serialVersionUID = 1L;

    private EntityManagerFactory emf = null;
    private EntityManager em = null;

    public CategoriaIngresoDAO() {
        emf = Persistence.createEntityManagerFactory("Contabilidad");
        em = emf.createEntityManager();
    }

    public List<CategoriaIngreso> obtenerTodo() {
        List<CategoriaIngreso> categoriasIngreso = null;
        try {
            // Consulta para obtener los Movimientos
            String jpql = "SELECT ci FROM CategoriaIngreso ci";
            TypedQuery<CategoriaIngreso> query = em.createQuery(jpql, CategoriaIngreso.class);

            categoriasIngreso = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace(); // Manejo básico de excepciones
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Hacer rollback en caso de excepción
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

    public CategoriaTransferencia obtenerCategoriaPorId(int idCategoria) {
        CategoriaEgreso categoriasEgreso = null;

        try {
            em = emf.createEntityManager();

            // Consulta para obtener los Movimientos
            String jpql = "SELECT ce FROM CategoriaEgreso ce WHERE ce.id = :idCategoria ";

            TypedQuery<CategoriaEgreso> query = em.createQuery(jpql, CategoriaEgreso.class);
            query.setParameter("idCategoria", idCategoria);

            categoriasEgreso = (CategoriaEgreso) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace(); // Manejo básico de excepciones
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
}
