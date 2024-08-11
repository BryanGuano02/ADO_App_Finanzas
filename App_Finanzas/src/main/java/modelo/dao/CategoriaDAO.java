package modelo.dao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.entidades.Categoria;
import modelo.entidades.CategoriaEgreso;
import jakarta.persistence.EntityManager;
import modelo.entidades.CategoriaIngreso;


import java.io.Serializable;
import java.util.List;

public class CategoriaDAO implements Serializable {
    private static final long serialVersionUID = 1L;

    private EntityManagerFactory emf = null;
    private EntityManager em = null;

    /**
     * Default constructor
     */
    public CategoriaDAO() {
        emf = Persistence.createEntityManagerFactory("chaucherita_PU");
        em = emf.createEntityManager();
    }

    /**
     *
     */
    public void getName() {
        // TODO implement here
    }

    /**
     *
     */
    public List<CategoriaEgreso> getExpenseCategories() {
        // TODO implement here
        return null;
    }

    /**
     *
     */
    public void getIncomeCategories() {
        // TODO implement here
    }

    public void updateBalance(double value) {
        // TODO Auto-generated method stub

    }

    public Categoria obtenerCategoriaPorId(int idCategoria) {
        Categoria categoria = null;

        try {
            em = emf.createEntityManager();

            // Consulta para obtener los Movimientos
            String jpql = "SELECT c FROM Categoria c WHERE c.ID = :idCategoria ";

            TypedQuery<Categoria> query = em.createQuery(jpql, Categoria.class);
            query.setParameter("idCategoria", idCategoria);

            categoria =  query.getSingleResult();
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

        return categoria;
    }

}
