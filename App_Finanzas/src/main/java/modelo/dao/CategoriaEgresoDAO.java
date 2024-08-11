package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import modelo.entidades.CategoriaEgreso;
import modelo.entidades.CategoriaIngreso;
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

    public void ingresar(CategoriaEgreso categoriaEgreso) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();  // Inicia la transacción

            em.persist(categoriaEgreso); // Persiste la entidad en la base de datos

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
    public CategoriaEgreso obtenerCategoriaPorId(int idCategoria) {
        CategoriaEgreso categoriaEgreso = null;

        try {
            em = emf.createEntityManager();

            // Consulta para obtener los Movimientos
            String jpql = "SELECT ce FROM CategoriaEgreso ce WHERE ce.ID = :idCategoria ";

            TypedQuery<CategoriaEgreso> query = em.createQuery(jpql, CategoriaEgreso.class);
            query.setParameter("idCategoria", idCategoria);

            categoriaEgreso = query.getSingleResult();

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

        return categoriaEgreso;
    }

    public List<CategoriaEgreso> obtenerTodo() {
        List<CategoriaEgreso> categoriasEgreso = null;
        try {
            // Consulta para obtener los Movimientos
            String jpql = "SELECT ce FROM CategoriaEgreso ce";
            TypedQuery<CategoriaEgreso> query = em.createQuery(jpql, CategoriaEgreso.class);

            categoriasEgreso = query.getResultList();

        }catch (Exception e) {
            e.printStackTrace(); // Manejo básico de excepciones
            if (em != null && em.getTransaction().isActive())
                em.getTransaction().rollback(); // Hacer rollback en caso de excepción

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
/*
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
    }*/
}
