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

    private EntityManagerFactory emf;

    public CategoriaDAO() {
        emf = Persistence.createEntityManagerFactory("chaucherita_PU");
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void getName() {
        // Implementación futura
    }

    public List<CategoriaEgreso> getExpenseCategories() {
        EntityManager em = getEntityManager();
        List<CategoriaEgreso> categorias = null;
        try {
            // Implementación futura
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return categorias;
    }

    public List<CategoriaIngreso> getIncomeCategories() {
        EntityManager em = getEntityManager();
        List<CategoriaIngreso> categorias = null;
        try {
            // Implementación futura
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return categorias;
    }

    public void updateBalance(double value) {
        EntityManager em = getEntityManager();
        try {
            // Implementación futura
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public Categoria obtenerCategoriaPorId(int idCategoria) {
        EntityManager em = getEntityManager();
        Categoria categoria = null;

        try {
            // Consulta para obtener la Categoría por ID
            String jpql = "SELECT c FROM Categoria c WHERE c.ID = :idCategoria";
            TypedQuery<Categoria> query = em.createQuery(jpql, Categoria.class);
            query.setParameter("idCategoria", idCategoria);

            categoria = query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace(); // Manejo básico de excepciones
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return categoria;
    }
}
