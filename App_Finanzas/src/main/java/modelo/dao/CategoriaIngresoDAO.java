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
        emf = Persistence.createEntityManagerFactory("chaucherita_PU");
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

    public CategoriaIngreso obtenerCategoriaPorId(int idCategoria) {
        CategoriaIngreso categoriasIngreso = null;

        try {
            em = emf.createEntityManager();

            // Consulta para obtener los Movimientos
            String jpql = "SELECT ci FROM CategoriaIngreso ci WHERE ce.id = :idCategoria ";

            TypedQuery<CategoriaIngreso> query = em.createQuery(jpql, CategoriaIngreso.class);
            query.setParameter("idCategoria", idCategoria);

            categoriasIngreso = (CategoriaIngreso) query.getResultList();

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

        return categoriasIngreso;
    }

    public void actualizarSaldo(CategoriaIngreso categoriaIngreso, double valor) {
        EntityManager em = emf.createEntityManager();

        try {
            em = emf.createEntityManager();

            // Consulta para obtener los movimientos entre dos fechas
            String jpql = "UPDATE CategoriaIngreso ci SET ci.total = ci.total + :valor WHERE ci.id = :idCategoria";
            TypedQuery query = (TypedQuery) em.createQuery(jpql);
            query.setParameter("valor", valor); // Asegúrate de que 'valor' esté definido en tu código
            query.setParameter("idCategoria", categoriaIngreso.getID());

            query.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
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
