package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.dto.MovimientoDTO;
import modelo.entidades.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovimientoDAO implements Serializable {

    private static final long serialVersionUID = 1L;
    private EntityManagerFactory emf;

    public MovimientoDAO() {
        emf = Persistence.createEntityManagerFactory("chaucherita_PU");
    }
    public List<MovimientoDTO> obtenerTodo(LocalDate desde, LocalDate hasta) {
        EntityManager em = null;
        List<MovimientoDTO> movimientosDTO = null   ;

        try {
            em = emf.createEntityManager();

            // Consulta para obtener los movimientos entre dos fechas
            String jpql = "SELECT m FROM Movimiento m WHERE m.Fecha BETWEEN :desde AND :hasta";
            TypedQuery<MovimientoDTO> query = em.createQuery(jpql, MovimientoDTO.class);
            query.setParameter("desde", desde);
            query.setParameter("hasta", hasta);

           movimientosDTO = query.getResultList();

            // Convertir cada Movimiento a MovimientoDTO
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        }

        return movimientosDTO;
    }
}

























    /*
    public List<MovimientoDTO> obtenerMovimientosPorIdCuenta(int idCuenta) {
        EntityManager em = null;
        List<MovimientoDTO> movimientoDTO = new ArrayList<>();

        try {
            em = emf.createEntityManager();

            // Consulta para obtener los Movimientos
            String jpql = "SELECT m FROM Movimiento m WHERE m.cuentaOrigenID.id = :idCuenta OR m.cuentaDestinoID.id = :idCuenta";

            TypedQuery<Movimiento> query = em.createQuery(jpql, Movimiento.class);
            query.setParameter("idCuenta", idCuenta);

            List<Movimiento> movimientos = query.getResultList();

            for (Movimiento movimiento : movimientos) {
                String nombreCuentaOrigen;
                if (movimiento.getCuenta_Origen() == null || movimiento.getCuenta_Origen().getNombre() == null) {
                    nombreCuentaOrigen = movimiento.getCategoria().getNombre();
                } else {
                    nombreCuentaOrigen = movimiento.getCuenta_Origen().getNombre();
                }

                // Determinar el nombre de cuentaDestino. Si es null, usar el nombre de la categoría.
                String nombreCuentaDestino;
                if (movimiento.getCuenta_Destino() == null || movimiento.getCuenta_Destino().getNombre() == null) {
                    nombreCuentaDestino = movimiento.getCategoria().getNombre();
                } else {
                    nombreCuentaDestino = movimiento.getCuenta_Destino().getNombre();
                }

                MovimientoDTO dto = new MovimientoDTO(
                        movimiento.getId().toString(), // ID como String
                        java.sql.Date.valueOf(movimiento.getFecha()), // Convertir LocalDate a Date
                        movimiento.getConcepto(),
                        movimiento.getValor(),
                        nombreCuentaOrigen,
                        nombreCuentaDestino
                        // Tipo de Movimiento como nombre de la categoría
                );
                movimientoDTO.add(dto);
            }
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

        return movimientoDTO;
    }

    public List<MovimientoDTO> obtenerMovimientosPorIdCategoria(int idCategoria) {
        EntityManager em = null;
        List<MovimientoDTO> movimientosDTO = new ArrayList<>();

        try {
            em = emf.createEntityManager();

            // Consulta para obtener los movimientos por categoría
            String jpql = "SELECT m FROM Movimiento m WHERE m.categoria.id = :idCategoria";

            TypedQuery<Movimiento> query = em.createQuery(jpql, Movimiento.class);
            query.setParameter("idCategoria", idCategoria);

            List<Movimiento> movimientos = query.getResultList();

            // Convertir cada Movimiento a MovimientoDTO
            for (Movimiento movimiento : movimientos) {
                String cuentaOrigenNombre = (movimiento.getCuenta_Origen() != null) ? movimiento.getCuenta_Origen().getNombre() : "No especificado";
                String cuentaDestinoNombre = (movimiento.getCuenta_Destino() != null) ? movimiento.getCuenta_Destino().getNombre() : "No especificado";

                MovimientoDTO movimientoDTO = new MovimientoDTO(
                        movimiento.getId().toString(), // Convertir el ID a String
                        java.sql.Date.valueOf(movimiento.getFecha()), // Convertir LocalDate a Date
                        movimiento.getConcepto(),
                        movimiento.getValor(),
                        cuentaOrigenNombre,
                        cuentaDestinoNombre
                );
                movimientosDTO.add(movimientoDTO);
            }

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

        return movimientosDTO;
//        return null;
    }


    public Movimiento obtenerMovimientoPorIdMovimiento(int idMovimiento) {
        EntityManager em = null;
        Movimiento movimiento = null;

        try {
            em = emf.createEntityManager();

            // Buscar el Movimiento por ID
            movimiento = em.find(Movimiento.class, idMovimiento);
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

        return movimiento;
    }


    public void eliminarMovimiento(int idMovimiento) {
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            // Obtener el Movimiento a eliminar
            Movimiento movimiento = em.find(Movimiento.class, idMovimiento);
            if (movimiento != null) {
                em.remove(movimiento); // Eliminar el objeto
            }

            em.getTransaction().commit(); // Confirmar la transacción
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
    }

*/



