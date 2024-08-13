package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpSession;
import modelo.dto.MovimientoDTO;
import modelo.entidades.*;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovimientoDAO implements Serializable {

    private static final long serialVersionUID = 1L;
    private EntityManagerFactory emf;

    public MovimientoDAO() {
        emf = Persistence.createEntityManagerFactory("chaucherita_PU");
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public List<MovimientoDTO> obtenerTodo(LocalDate desde, LocalDate hasta) {
        EntityManager em = getEntityManager();
        List<MovimientoDTO> movimientosDTO = new ArrayList<>();

        try {
            // Query to get transactions between two dates
            String jpql = "SELECT m FROM Movimiento m WHERE m.Fecha BETWEEN :desde AND :hasta";
            TypedQuery<Movimiento> query = em.createQuery(jpql, Movimiento.class);
            query.setParameter("desde", desde);
            query.setParameter("hasta", hasta);

            List<Movimiento> movimientos = query.getResultList();

            for (Movimiento movimiento : movimientos) {
                movimientosDTO.add(convertirMovimientoADTO(movimiento));
            }

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return movimientosDTO;
    }


    public void eliminarMovimiento(Integer idMovimiento) {
//        Delete a movement by its ID
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            Movimiento movimiento = em.find(Movimiento.class, idMovimiento);
            if (movimiento != null) {
                em.remove(movimiento);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public Movimiento obtenerMovimientoPorIdMovimiento(int idMovimiento) {
        EntityManager em = getEntityManager();
        Movimiento movimiento = null;

        try {
            String jpql = "SELECT m FROM Movimiento m WHERE m.Id = :idMovimiento";
            TypedQuery<Movimiento> query = em.createQuery(jpql, Movimiento.class);
            query.setParameter("idMovimiento", idMovimiento);

            movimiento = query.getSingleResult();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return movimiento;
    }

    public void actualizarMovimiento(Movimiento movimiento) {

//        Update the attributes of a movement
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(movimiento);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<MovimientoDTO> obtenerMovimientosPorIdCategoria(int idCategoria, LocalDate desde, LocalDate hasta) {
        EntityManager em = getEntityManager();
        List<MovimientoDTO> movimientosDTO = new ArrayList<>();

        try {
            // Query to get all Expenses associated with the category
            List<Egreso> egresos = obtenerEgresosPorIdCategoria(em, idCategoria, desde, hasta);

            // Query to get all Income associated with the category
            List<Ingreso> ingresos = obtenerIngresosPorIdCategoria(em, idCategoria, desde, hasta);

            // Query to get all Transfers associated with the category
            List<Transferencia> transferencias = obtenerTransferenciasPorIdCategoria(em, idCategoria, desde, hasta);

            // Combinar todos los resultados en la lista de DTOs
            for (Egreso egreso : egresos) {
                movimientosDTO.add(convertirMovimientoADTO(egreso));
            }

            for (Ingreso ingreso : ingresos) {
                movimientosDTO.add(convertirMovimientoADTO(ingreso));
            }

            for (Transferencia transferencia : transferencias) {
                movimientosDTO.add(convertirMovimientoADTO(transferencia));
            }

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return movimientosDTO;
    }

    public List<MovimientoDTO> obtenerMovimientosPorIdCuenta(int idCuenta) {
        EntityManager em = getEntityManager();
        List<MovimientoDTO> movimientosDTO = new ArrayList<>();

        try {
            // Consulta para obtener todos los Egresos asociados a la cuenta
            List<Egreso> egresos = obtenerEgresosPorIdCuenta(em, idCuenta);

            // Consulta para obtener todos los Ingresos asociados a la cuenta
            List<Ingreso> ingresos = obtenerIngresosPorIdCuenta(em, idCuenta);

            // Consulta para obtener todas las Transferencias asociadas a la cuenta
            List<Transferencia> transferencias = obtenerTransferenciasPorIdCuenta(em, idCuenta);

            // Combinar todos los resultados en la lista de DTOs
            for (Egreso egreso : egresos) {
                movimientosDTO.add(convertirMovimientoADTO(egreso));
            }

            for (Ingreso ingreso : ingresos) {
                movimientosDTO.add(convertirMovimientoADTO(ingreso));
            }

            for (Transferencia transferencia : transferencias) {
                movimientosDTO.add(convertirMovimientoADTO(transferencia));
            }

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return movimientosDTO;
    }

//    Support methods

    private MovimientoDTO convertirMovimientoADTO(Movimiento movimiento) {
        String cuentaOrigen = null;
        String cuentaDestino = null;

        if (movimiento instanceof Egreso) {
            Egreso egreso = (Egreso) movimiento;
            cuentaOrigen = egreso.getCuentaOrigen().getNombre();
            cuentaDestino = egreso.getCategoria().getNombre();
        } else if (movimiento instanceof Ingreso) {
            Ingreso ingreso = (Ingreso) movimiento;
            cuentaOrigen = ingreso.getCategoria().getNombre();
            cuentaDestino = ingreso.getCuentaDestino().getNombre();
        } else if (movimiento instanceof Transferencia) {
            Transferencia transferencia = (Transferencia) movimiento;
            cuentaOrigen = transferencia.getCuentaOrigen().getNombre();
            cuentaDestino = transferencia.getCuentaDestino().getNombre();
        }

        return new MovimientoDTO(
                movimiento.getId().toString(),
                Date.valueOf(movimiento.getFecha()).toLocalDate(),
                movimiento.getConcepto(),
                movimiento.getValor(),
                cuentaOrigen,
                cuentaDestino
        );
    }

    private List<Egreso> obtenerEgresosPorIdCuenta(EntityManager em, int idCuenta) {
        String jpql = "SELECT e FROM Egreso e WHERE e.CuentaOrigen.id = :idCuenta";
        TypedQuery<Egreso> query = em.createQuery(jpql, Egreso.class);
        query.setParameter("idCuenta", idCuenta);
        return query.getResultList();
    }

    private List<Ingreso> obtenerIngresosPorIdCuenta(EntityManager em, int idCuenta) {
        String jpql = "SELECT i FROM Ingreso i WHERE i.CuentaDestino.id = :idCuenta";
        TypedQuery<Ingreso> query = em.createQuery(jpql, Ingreso.class);
        query.setParameter("idCuenta", idCuenta);
        return query.getResultList();
    }

    private List<Transferencia> obtenerTransferenciasPorIdCuenta(EntityManager em, int idCuenta) {
        String jpql = "SELECT t FROM Transferencia t WHERE t.CuentaDestino.id = :idCuenta";
        TypedQuery<Transferencia> query = em.createQuery(jpql, Transferencia.class);
        query.setParameter("idCuenta", idCuenta);
        return query.getResultList();
    }

    private List<Egreso> obtenerEgresosPorIdCategoria(EntityManager em, int idCategoria, LocalDate desde, LocalDate hasta) {
        String jpql = "SELECT e FROM Egreso e WHERE e.categoria.ID = :idCategoria AND e.Fecha >= :desde AND e.Fecha <= :hasta";
        TypedQuery<Egreso> query = em.createQuery(jpql, Egreso.class);
        query.setParameter("idCategoria", idCategoria);
        query.setParameter("desde", desde);
        query.setParameter("hasta", hasta);
        return query.getResultList();
    }


    private List<Ingreso> obtenerIngresosPorIdCategoria(EntityManager em, int idCategoria, LocalDate desde, LocalDate hasta) {
        String jpql = "SELECT i FROM Ingreso i WHERE i.categoria.ID = :idCategoria AND i.Fecha >= :desde AND i.Fecha <= :hasta";
        TypedQuery<Ingreso> query = em.createQuery(jpql, Ingreso.class);
        query.setParameter("idCategoria", idCategoria);
        query.setParameter("desde", desde);
        query.setParameter("hasta", hasta);
        return query.getResultList();
    }


    private List<Transferencia> obtenerTransferenciasPorIdCategoria(EntityManager em, int idCategoria, LocalDate desde, LocalDate hasta) {
        String jpql = "SELECT t FROM Transferencia t WHERE t.categoria.ID = :idCategoria AND t.Fecha >= :desde AND t.Fecha <= :hasta";
        TypedQuery<Transferencia> query = em.createQuery(jpql, Transferencia.class);
        query.setParameter("idCategoria", idCategoria);
        query.setParameter("desde", desde);
        query.setParameter("hasta", hasta);
        return query.getResultList();
    }

}
