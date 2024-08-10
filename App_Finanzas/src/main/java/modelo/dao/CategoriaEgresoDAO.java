package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.dto.CategoriaEgresoDTO;
import modelo.dto.MovimientoDTO;
import modelo.entidades.CategoriaEgreso;
import modelo.entidades.Cuenta;
import modelo.entidades.Movimiento;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
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

    public List<CategoriaEgreso> obtenerTodo() {
        List<CategoriaEgreso> categoriasEgreso = null;
        try {
            em = emf.createEntityManager();

            // Consulta para obtener los Movimientos
            String jpql = "SELECT ce FROM CategoriaEgreso ce";
            TypedQuery<CategoriaEgreso> query = em.createQuery(jpql, CategoriaEgreso.class);

            categoriasEgreso = query.getResultList();


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
        return categoriasEgreso;
    }

    public void actualizarSaldo(CategoriaEgreso categoriaEgreso, double valor) {
        EntityManager em = emf.createEntityManager();

        try {
            em = emf.createEntityManager();

            // Consulta para obtener los movimientos entre dos fechas
            String jpql = "UPDATE CategoriaEgreso ce SET ce.total = ce.total + :valor WHERE ce.id = :idCategoria";
            TypedQuery query = (TypedQuery) em.createQuery(jpql);
            query.setParameter("valor", valor); // Asegúrate de que 'valor' esté definido en tu código
            query.setParameter("idCategoria", categoriaEgreso.getID());

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
