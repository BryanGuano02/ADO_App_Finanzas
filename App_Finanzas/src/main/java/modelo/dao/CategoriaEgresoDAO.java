package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import modelo.entidades.CategoriaEgreso;

import java.io.Serializable;


public class CategoriaEgresoDAO implements Serializable {

    private static final long serialVersionUID = 1L;
    private EntityManagerFactory emf;


    public CategoriaEgreso obtenerCategoriaPorId(int idCategoria) {
        EntityManager em = null;
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
