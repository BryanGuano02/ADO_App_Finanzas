package modelo.dao;

import jakarta.persistence.*;
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


    public void ingresar(CategoriaIngreso categoriaIngreso) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();  // Inicia la transacción

            em.persist(categoriaIngreso); // Persiste la entidad en la base de datos

            em.getTransaction().commit(); // Finaliza la transacción (commit)
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Hace rollback si ocurre una excepción
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close(); // Cierra el EntityManager
            }
        }
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
        CategoriaIngreso categoriaIngreso = null;

        try {
            em = emf.createEntityManager();

            // Consulta para obtener los Movimientos
            String jpql = "SELECT ci FROM CategoriaIngreso ci WHERE ci.ID = :idCategoria ";

            TypedQuery<CategoriaIngreso> query = em.createQuery(jpql, CategoriaIngreso.class);
            query.setParameter("idCategoria", idCategoria);

            categoriaIngreso =  query.getSingleResult();
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

        return categoriaIngreso;
    }

//    public void actualizarSaldo(CategoriaIngreso categoriaIngreso, double valor) {
//
//        try {
//            em = emf.createEntityManager();
//            em.getTransaction().begin();
//            // Consulta para obtener los movimientos entre dos fechas
//            String jpql = "UPDATE CategoriaIngreso ci SET WHERE ci.ID = :idCategoria";
//            Query query = em.createQuery(jpql);
//            query.setParameter("valor", valor); // Asegúrate de que 'valor' esté definido en tu código
//            query.setParameter("idCategoria", categoriaIngreso.getID());
//
//            query.executeUpdate();
//            em.getTransaction().commit();
//        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
}

