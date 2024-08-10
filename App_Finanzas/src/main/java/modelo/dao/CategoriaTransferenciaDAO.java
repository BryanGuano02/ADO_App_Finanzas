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
        emf = Persistence.createEntityManagerFactory("Contabilidad");
        em = emf.createEntityManager();
    }

    public List<CategoriaTransferencia> obtenerTodo() {
        List<CategoriaTransferencia> categoriasTransferencia = null;
        try {
            em = emf.createEntityManager();

            // Consulta para obtener los Movimientos
            String jpql = "SELECT ci FROM CategoriaTransferencia ci";
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

    public CategoriaEgreso obtenerCategoriaPorId(int idCategoria) {
    }
}
