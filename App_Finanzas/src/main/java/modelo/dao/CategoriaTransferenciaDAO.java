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
            em = emf.createEntityManager();

            // Consulta para obtener los Movimientos
            String jpql = "SELECT ct FROM CategoriaTransferencia ct";
            TypedQuery<CategoriaTransferencia> query = em.createQuery(jpql, CategoriaTransferencia.class);

            categoriasTransferencia = query.getResultList();

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
        return categoriasTransferencia;
    }

    public CategoriaTransferencia obtenerCategoriaPorId(int idCategoria) {
        CategoriaTransferencia categoriasTransferencia = null;

        try {
            em = emf.createEntityManager();

            // Consulta para obtener los Movimientos
            String jpql = "SELECT ct FROM CategoriaTransferencia ct WHERE ct.id = :idCategoria ";

            TypedQuery<CategoriaTransferencia> query = em.createQuery(jpql, CategoriaTransferencia.class);
            query.setParameter("idCategoria", idCategoria);

            categoriasTransferencia = (CategoriaTransferencia) query.getResultList();

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

        return categoriasTransferencia;
    }

    public void actualizarSaldo(CategoriaTransferencia categoriaTransferencia, double valor) {
        EntityManager em = emf.createEntityManager();

        try {
            em = emf.createEntityManager();

            // Consulta para obtener los movimientos entre dos fechas
            String jpql = "UPDATE CategoriaTransferencia ct SET ct.total = ct.total + :valor WHERE ct.id = :idCategoria";
            TypedQuery query = (TypedQuery) em.createQuery(jpql);
            query.setParameter("valor", valor); // Asegúrate de que 'valor' esté definido en tu código
            query.setParameter("idCategoria", categoriaTransferencia.getID());
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
