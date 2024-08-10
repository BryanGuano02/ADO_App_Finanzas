package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import modelo.dto.MovimientoDTO;
import modelo.entidades.CategoriaEgreso;
import modelo.entidades.Cuenta;
import modelo.entidades.Movimiento;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovimientoDAO implements Serializable {

    private static final long serialVersionUID = 1L;
    private EntityManagerFactory emf;

    public List<MovimientoDTO> obtenerTodo(LocalDate desde, LocalDate hasta) {
        EntityManager em = null;
        List<MovimientoDTO> movimientoDTO = new ArrayList<>();

        try {
            em = emf.createEntityManager();

            // Iniciar una transacción solo si vas a persistir nuevos datos
            em.getTransaction().begin();

            // Agregar un nuevo objeto Movimiento y persistirlo
            Movimiento nuevoMovimiento = new Movimiento(
                    10.9,
                    1,
                    LocalDate.of(2024, 1, 7),
                    new Cuenta(1, "Billetera", 12.1),
                    new Cuenta(4, "Bajo del colchón", 15.24),
                    "hola",
                    new CategoriaEgreso(LocalDate.of(2029, 1, 7), 4.4, "comida", 1)
            );
            em.persist(nuevoMovimiento);

            // Confirmar la transacción
            em.getTransaction().commit();

            // Consulta para obtener los Movimientos
            String jpql = "SELECT m FROM Movimiento m WHERE m.fecha BETWEEN :desde AND :hasta";
            TypedQuery<Movimiento> query = em.createQuery(jpql, Movimiento.class);
            query.setParameter("desde", desde);
            query.setParameter("hasta", hasta);

            List<Movimiento> movimientos = query.getResultList();

            // Convertir la lista de Movimientos a MovimientoDTO
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
        return movimientoDTO;
    }


    public List<MovimientoDTO> obtenerMovimientosPorIdCuenta(int idCuenta) {
        EntityManager em = null;
        List<MovimientoDTO> movimientoDTO = new ArrayList<>();

        try {
            em = emf.createEntityManager();

            // Consulta para obtener los Movimientos
            String jpql = "SELECT m FROM Movimiento m WHERE m.cuentaOrigen.id = :idCuenta OR m.cuentaDestino.id = :idCuenta";

            TypedQuery<Movimiento> query = em.createQuery(jpql, Movimiento.class);
            query.setParameter("idCuenta", idCuenta);

            List<Movimiento> movimientos = query.getResultList();
            //TODO: Completar el retornar los movimientosDTO

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
}


