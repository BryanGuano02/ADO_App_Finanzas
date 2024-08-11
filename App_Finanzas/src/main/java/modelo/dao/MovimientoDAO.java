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
    private EntityManager em = null;

    public MovimientoDAO() {
        emf = Persistence.createEntityManagerFactory("chaucherita_PU");
        em = emf.createEntityManager();
    }

    public List<MovimientoDTO> obtenerTodo(LocalDate desde, LocalDate hasta) {
        List<MovimientoDTO> movimientosDTO = new ArrayList<>();

        try {
            em = emf.createEntityManager();

            // Consulta para obtener los movimientos entre dos fechas
            String jpql = "SELECT m FROM Movimiento m WHERE m.Fecha BETWEEN :desde AND :hasta";
            TypedQuery<Movimiento> query = em.createQuery(jpql, Movimiento.class);
            query.setParameter("desde", desde);
            query.setParameter("hasta", hasta);

            List<Movimiento> movimientos = query.getResultList();

            // Convertir cada Movimiento a MovimientoDTO
            for (Movimiento movimiento : movimientos) {
                String cuentaOrigen = "null";
                String cuentaDestino = "null";

                if (movimiento instanceof Egreso) {
                    Egreso egreso = (Egreso) movimiento;
                    cuentaDestino = egreso.getCategoria().getNombre();
                    cuentaOrigen = egreso.getCuentaOrigen().getNombre();
                }

                if (movimiento instanceof Ingreso) {
                    Ingreso ingreso = (Ingreso) movimiento;
                    cuentaOrigen = ingreso.getCategoria().getNombre();
                    cuentaDestino = ingreso.getCuentaDestino().getNombre();
                }

                if (movimiento instanceof Transferencia) {
                    Transferencia transferencia = (Transferencia) movimiento;
                    cuentaOrigen = transferencia.getCuentaOrigen().getNombre();
                    cuentaDestino = transferencia.getCuentaDestino().getNombre();
                }


                MovimientoDTO dto = new MovimientoDTO(
                        movimiento.getId().toString(),
                        java.sql.Date.valueOf(movimiento.getFecha()),
                        movimiento.getConcepto(),
                        movimiento.getValor(),
                        cuentaOrigen,
                        cuentaDestino
                );
                movimientosDTO.add(dto);
            }

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

    public MovimientoDTO obtenerMovimientoPorIdMovimiento(int idMovimiento) {
        MovimientoDTO movimientoDTO = null;
        Movimiento movimiento = null;

        try {
            em = emf.createEntityManager();

            // Consulta para obtener los movimientos entre dos fechas
            String jpql = "SELECT m FROM Movimiento m WHERE m.Id = :idMovimiento";
            TypedQuery<Movimiento> query = em.createQuery(jpql, Movimiento.class);
            query.setParameter("idMovimiento", idMovimiento);

            movimiento = (Movimiento) query.getResultList();

            String cuentaOrigen = "null";
            String cuentaDestino = "null";

            if (movimiento instanceof Egreso) {
                Egreso egreso = (Egreso) movimiento;
                cuentaDestino = egreso.getCategoria().getNombre();
                cuentaOrigen = egreso.getCuentaOrigen().getNombre();
            }

            if (movimiento instanceof Ingreso) {
                Ingreso ingreso = (Ingreso) movimiento;
                cuentaOrigen = ingreso.getCategoria().getNombre();
                System.out.println(cuentaDestino);
                cuentaDestino = ingreso.getCuentaDestino().getNombre();

            }


            movimientoDTO = new MovimientoDTO(
                    movimiento.getId().toString(),
                    java.sql.Date.valueOf(movimiento.getFecha()),
                    movimiento.getConcepto(),
                    movimiento.getValor(),
                    cuentaOrigen,
                    cuentaDestino
            );


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

    public void eliminarMovimiento(int idMovimiento) {
        Movimiento movimiento = null;

        try {
            em = emf.createEntityManager();

            // Consulta para obtener los movimientos entre dos fechas
            String jpql = "DELETE FROM Movimiento m WHERE m.Id = :idMovimiento";
            TypedQuery<Movimiento> query = em.createQuery(jpql, Movimiento.class);
            query.setParameter("idMovimiento", idMovimiento);

            query.executeUpdate();
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
    }

    public Movimiento obtenerMovimientoPorIdMovimiento1(int idMovimiento) {
        EntityManager em = null;
        Movimiento movimiento = null;

        try {
            em = emf.createEntityManager();

            // Consulta para obtener el movimiento por id
            String jpql = "SELECT m FROM Movimiento m WHERE m.Id = :idMovimiento";
            TypedQuery<Movimiento> query = em.createQuery(jpql, Movimiento.class);
            query.setParameter("idMovimiento", idMovimiento);

            // Como esperamos un único resultado, usamos getSingleResult
            movimiento = query.getSingleResult();

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


    public void actualizarMovimiento(Movimiento movimiento) {
        EntityManager em = null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            // Actualizar el movimiento en la base de datos
            em.merge(movimiento);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Deshacer los cambios en caso de error
            }
            e.printStackTrace(); // Manejo básico de excepciones
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        }
    }

    public List<MovimientoDTO> obtenerMovimientosPorIdCuenta(int idCuenta) {
        List<MovimientoDTO> movimientosDTO = new ArrayList<>();

        try {
            em = emf.createEntityManager();

            // Consulta para obtener los Movimientos
            String jpql = "SELECT m FROM Movimiento m WHERE m.Id = :idCuenta OR m.cuentaDestinoID.id = :idCuenta";
            TypedQuery<Movimiento> query = em.createQuery(jpql, Movimiento.class);
            query.setParameter("idCuenta", idCuenta);

            List<Movimiento> movimientos = query.getResultList();

            for (Movimiento movimiento : movimientos) {
                String cuentaOrigen = "null";
                String cuentaDestino = "null";

                if (movimiento instanceof Egreso) {
                    Egreso egreso = (Egreso) movimiento;
                    cuentaDestino = egreso.getCategoria().getNombre();
                    cuentaOrigen = egreso.getCuentaOrigen().getNombre();
                }

                if (movimiento instanceof Ingreso) {
                    Ingreso ingreso = (Ingreso) movimiento;
                    cuentaOrigen = ingreso.getCategoria().getNombre();
                    cuentaDestino = ingreso.getCuentaDestino().getNombre();
                }

                if (movimiento instanceof Transferencia) {
                    Transferencia transferencia = (Transferencia) movimiento;
                    cuentaOrigen = transferencia.getCuentaOrigen().getNombre();
                    cuentaDestino = transferencia.getCuentaDestino().getNombre();
                }

                MovimientoDTO dto = new MovimientoDTO(
                        movimiento.getId().toString(),
                        java.sql.Date.valueOf(movimiento.getFecha()),
                        movimiento.getConcepto(),
                        movimiento.getValor(),
                        cuentaOrigen,
                        cuentaDestino
                );
                movimientosDTO.add(dto);
            }
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
    }*/