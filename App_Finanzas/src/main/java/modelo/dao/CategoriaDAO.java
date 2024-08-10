package modelo.dao;

import modelo.entidades.Categoria;
import modelo.entidades.CategoriaEgreso;
import jakarta.persistence.EntityManager;


import java.util.List;

public class CategoriaDAO {


    /**
     * Default constructor
     */
    public CategoriaDAO() {
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
        /*EntityManager em = null;
        Categoria categoria = null;

        try {
            em = emf.createEntityManager();
            categoria = em.find(Categoria.class, idCategoria);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return categoria;*/
        return null;
    }

}
