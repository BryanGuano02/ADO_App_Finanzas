package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.entidades.Cuenta;

import java.util.List;

public class CuentaDAO {

    private EntityManagerFactory emf;

    public CuentaDAO() {
        emf = Persistence.createEntityManagerFactory("chaucherita_PU");
    }

    public List<Cuenta> obtenerTodo() {
        EntityManager em = emf.createEntityManager();

        List<Cuenta> cuentas = null;

        try {
            TypedQuery<Cuenta> query = em.createQuery("SELECT c FROM Cuenta c", Cuenta.class);
            cuentas = query.getResultList();
            if (cuentas.isEmpty()) {
                cuentas.add(new Cuenta(1, "Bnc. Pichincha", 10));
                cuentas.add(new Cuenta(2, "Bnc. Pichincha Papá", 21));
                cuentas.add(new Cuenta(3, "Bnc. Guayaquil", 41.82));
                cuentas.add(new Cuenta(4, "Bajo del colchón", 15.24));
                cuentas.add(new Cuenta(5, "Billetera", 158));

                em.getTransaction().begin();
                for (Cuenta cuenta : cuentas) {
                    em.persist(cuenta); // Persiste cada objeto individualmente
                }
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }

        return cuentas;
    }

    public Cuenta obtenerCuentaPorId(int idCuenta) {
        EntityManager em = emf.createEntityManager();
        Cuenta cuenta = null;

        try {
            cuenta = em.find(Cuenta.class, idCuenta);
        } finally {
            em.close();
        }

        return cuenta;
    }

    public void actualizarSaldo(int idCuenta, double valor) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Cuenta cuenta = em.find(Cuenta.class, idCuenta);
            if (cuenta != null) {
                cuenta.setTotal(cuenta.getTotal() + valor);
                em.merge(cuenta);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void cerrar() {
        emf.close();
    }
}
