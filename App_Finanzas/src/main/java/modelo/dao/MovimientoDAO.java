package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.dto.MovimientoDTO;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class MovimientoDAO implements Serializable {

    private static final long serialVersionUID = 1L;
    private EntityManagerFactory emf;

    public List<MovimientoDTO> obtenerTodo(LocalDate desde, LocalDate hasta) {
        // Inicializa el EntityManagerFactory
        emf = Persistence.createEntityManagerFactory("chaucherita_PU");
        EntityManager em = null;
        List<MovimientoDTO> movimientos = null;

        try {
            // Crea el EntityManager
            em = emf.createEntityManager();

            // Create and execute the JPQL query
            String jpql = "SELECT m FROM Movimiento m WHERE m.fecha BETWEEN :desde AND :hasta";
            TypedQuery<MovimientoDTO> query = em.createQuery(jpql, MovimientoDTO.class);
            query.setParameter("desde", desde);
            query.setParameter("hasta", hasta);

            // Obtén la lista de movimientos
            movimientos = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(); // Manejo básico de excepciones
        } finally {
            // Cierre del EntityManager
            if (em != null && em.isOpen()) {
                em.close();
            }
            // Cierre del EntityManagerFactory
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        }

        return movimientos;
    }

    public List<MovimientoDTO> obtenerMovimientosPorIdCuenta(int idCuenta) {
        EntityManager em = emf.createEntityManager();
        List<Movimiento> movimientos = null;

        try {
            // Consulta JPQL para obtener movimientos por id de cuenta
            movimientos = em.createQuery(
                            "SELECT m FROM Movimiento m WHERE m.cuenta.id = :idCuenta", Movimiento.class)
                    .setParameter("idCuenta", idCuenta)
                    .getResultList();
        } finally {
            em.close(); // Asegurarse de cerrar el EntityManager
        }

        return movimientos;
    }
}


