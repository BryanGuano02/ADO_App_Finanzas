package modelo.dao;

import jakarta.persistence.*;
import modelo.entidades.Cuenta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CuentaDAO implements Serializable {

    private static final long serialVersionUID = 1L;
    private EntityManagerFactory emf;

    public CuentaDAO() {
        emf = Persistence.createEntityManagerFactory("chaucherita_PU");
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public List<Cuenta> obtenerTodo() {
        EntityManager em = getEntityManager();
        List<Cuenta> cuentas = new ArrayList<>();

        try {
            TypedQuery<Cuenta> query = em.createQuery("SELECT c FROM Cuenta c", Cuenta.class);
            cuentas = query.getResultList();

            if (cuentas.isEmpty()) {
                inicializarCuentas(em, cuentas);
            }

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return cuentas;
    }


    public Cuenta obtenerCuentaPorId(int idCuenta) {
        EntityManager em = getEntityManager();
        Cuenta cuenta = null;

        try {
            cuenta = em.find(Cuenta.class, idCuenta);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return cuenta;
    }

    public void actualizarSaldo(Cuenta cuenta, double valor) {
        EntityManager em = getEntityManager();

        int idCuenta = cuenta.getId();

        try {
            em.getTransaction().begin();
            String jpql = "UPDATE Cuenta c SET c.total = c.total + :valor WHERE c.id = :idCuenta";
            Query query = em.createQuery(jpql);
            query.setParameter("valor", valor);
            query.setParameter("idCuenta", idCuenta);

            query.executeUpdate();
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

    //Support methods

    private void inicializarCuentas(EntityManager em, List<Cuenta> cuentas) {
        // Crear y añadir cuentas por defecto
        cuentas.add(new Cuenta(1, "Bnc. Pichincha", 0));
        cuentas.add(new Cuenta(2, "Bnc. Pichincha Papá", 0));
        cuentas.add(new Cuenta(3, "Bnc. Guayaquil", 0));
        cuentas.add(new Cuenta(4, "Bajo del colchón", 0));
        cuentas.add(new Cuenta(5, "Billetera", 0));

        // Persistir las cuentas por defecto
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            for (Cuenta cuenta : cuentas) {
                em.persist(cuenta);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }


}
