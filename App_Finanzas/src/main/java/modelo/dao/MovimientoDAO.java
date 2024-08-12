package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.dto.MovimientoDTO;
import modelo.entidades.*;

import java.io.Serializable;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MovimientoDAO implements Serializable {

    private static final long serialVersionUID = 1L;
    private EntityManagerFactory emf = null;
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

    public void eliminarMovimiento(Integer idMovimiento) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            // Crear y ejecutar la consulta para eliminar el movimiento por su ID
            em.getTransaction().begin();
            Movimiento movimiento = em.find(Movimiento.class, idMovimiento);
            if (movimiento != null) {
                em.remove(movimiento);
                em.getTransaction().commit();
            } else {
                em.getTransaction().rollback();
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


    public List<MovimientoDTO> obtenerMovimientosPorIdCategoria(int idCategoria) {
        List<MovimientoDTO> movimientosDTO = new ArrayList<>();
        EntityManager em = null;

        try {
            em = emf.createEntityManager();

            // Consulta para obtener todos los Egresos asociados a la categoría
            String jpql1 = "SELECT e FROM Egreso e WHERE e.categoria.ID = :idCategoria";
            TypedQuery<Egreso> query1 = em.createQuery(jpql1, Egreso.class);
            query1.setParameter("idCategoria", idCategoria);
            List<Egreso> egresos = query1.getResultList();

            // Consulta para obtener todos los Ingresos asociados a la categoría
            String jpql2 = "SELECT i FROM Ingreso i WHERE i.categoria.ID = :idCategoria";
            TypedQuery<Ingreso> query2 = em.createQuery(jpql2, Ingreso.class);
            query2.setParameter("idCategoria", idCategoria);
            List<Ingreso> ingresos = query2.getResultList();

            // Consulta para obtener todas las Transferencias asociadas a la categoría
            String jpql3 = "SELECT t FROM Transferencia t WHERE t.categoria.ID = :idCategoria";
            TypedQuery<Transferencia> query3 = em.createQuery(jpql3, Transferencia.class);
            query3.setParameter("idCategoria", idCategoria);
            List<Transferencia> transferencias = query3.getResultList();

            // Combinar todos los resultados en la lista de DTOs
            for (Egreso egreso : egresos) {
                MovimientoDTO dto = new MovimientoDTO(
                        egreso.getId().toString(),
                        java.sql.Date.valueOf(egreso.getFecha()),
                        egreso.getConcepto(),
                        egreso.getValor(),
                        egreso.getCuentaOrigen().getNombre(),
                        egreso.getCategoria().getNombre()
                );
                movimientosDTO.add(dto);
            }


            for (Ingreso ingreso : ingresos) {
                MovimientoDTO dto = new MovimientoDTO(
                        ingreso.getId().toString(),
                        java.sql.Date.valueOf(ingreso.getFecha()),
                        ingreso.getConcepto(),
                        ingreso.getValor(),
                        ingreso.getCategoria().getNombre(),
                        ingreso.getCuentaDestino().getNombre()

                );
                movimientosDTO.add(dto);
            }

            for (Transferencia transferencia : transferencias) {
                MovimientoDTO dto = new MovimientoDTO(
                        transferencia.getId().toString(),
                        java.sql.Date.valueOf(transferencia.getFecha()),
                        transferencia.getConcepto(),
                        transferencia.getValor(),
                        transferencia.getCuentaOrigen().getNombre(),
                        transferencia.getCuentaDestino().getNombre()
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


    public List<MovimientoDTO> obtenerMovimientosPorIdCuenta(int idCuenta) {
        List<MovimientoDTO> movimientosDTO = new ArrayList<>();
        try {
            em = emf.createEntityManager();

            // Consulta para obtener todos los Egresos asociados a la categoría
            String jpql1 = "SELECT e FROM Egreso e WHERE e.CuentaOrigen.id = :idCuenta";
            TypedQuery<Egreso> query1 = em.createQuery(jpql1, Egreso.class);
            query1.setParameter("idCuenta", idCuenta);
            List<Egreso> egresos = query1.getResultList();


            for (Egreso egreso : egresos) {
                System.out.println("ID: " + egreso.getId());
                System.out.println("---------");
            }

            // Consulta para obtener todos los Ingresos asociados a la categoría
            String jpql2 = "SELECT i FROM Ingreso i WHERE i.CuentaDestino.id = :idCuenta";
            TypedQuery<Ingreso> query2 = em.createQuery(jpql2, Ingreso.class);
            query2.setParameter("idCuenta", idCuenta);
            List<Ingreso> ingresos = query2.getResultList();


            for (Ingreso ingreso : ingresos) {
                System.out.println("ID: " + ingreso.getId());
                System.out.println("---------");
            }

            // Consulta para obtener todas las Transferencias asociadas a la categoría
            String jpql3 = "SELECT t FROM Transferencia t WHERE t.CuentaDestino.id = :idCuenta OR t.CuentaOrigen.id = :idCuenta";
            TypedQuery<Transferencia> query3 = em.createQuery(jpql3, Transferencia.class);
            query3.setParameter("idCuenta", idCuenta);
            List<Transferencia> transferencias = query3.getResultList();


            for (Transferencia trans : transferencias) {
                System.out.println("DAO " + trans.getId());
                System.out.println("---------");
            }

            // Combinar todos los resultados en la lista de DTOs
            for (Egreso egreso : egresos) {
                MovimientoDTO dto = new MovimientoDTO(
                        egreso.getId().toString(),
                        java.sql.Date.valueOf(egreso.getFecha()),
                        egreso.getConcepto(),
                        egreso.getValor(),
                        egreso.getCuentaOrigen().getNombre(),
                        egreso.getCategoria().getNombre()
                );
                movimientosDTO.add(dto);
            }


            for (Ingreso ingreso : ingresos) {
                MovimientoDTO dto = new MovimientoDTO(
                        ingreso.getId().toString(),
                        java.sql.Date.valueOf(ingreso.getFecha()),
                        ingreso.getConcepto(),
                        ingreso.getValor(),
                        ingreso.getCategoria().getNombre(),
                        ingreso.getCuentaDestino().getNombre()

                );
                movimientosDTO.add(dto);
            }

            for (Transferencia transferencia : transferencias) {
                MovimientoDTO dto = new MovimientoDTO(
                        transferencia.getId().toString(),
                        java.sql.Date.valueOf(transferencia.getFecha()),
                        transferencia.getConcepto(),
                        transferencia.getValor(),
                        transferencia.getCuentaOrigen().getNombre(),
                        transferencia.getCuentaDestino().getNombre()
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

        for (MovimientoDTO movi : movimientosDTO) {
            System.out.println("DAOOOO " + movi.getConcepto());
            System.out.println("---------");
        }

        return movimientosDTO;
    }


}

