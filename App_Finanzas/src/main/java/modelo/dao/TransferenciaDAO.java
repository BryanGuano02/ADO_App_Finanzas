package modelo.dao;

import jakarta.persistence.*;
import modelo.entidades.Cuenta;
import modelo.entidades.Egreso;
import modelo.entidades.Transferencia;

public class TransferenciaDAO extends CuentaDAO {
    @OneToOne
    @JoinColumn(name = "cuenta_origen_id")
    private Cuenta cuentaOrigen;

    @ManyToOne
    @JoinColumn(name = "cuenta_destino_id")
    private Cuenta cuentaDestino;

    private static final long serialVersionUID = 1L;
    private EntityManagerFactory emf;

    public Cuenta getCuentaOrigen() {
        return cuentaOrigen;
    }

    public Cuenta getCuentaDestino() {
        return cuentaDestino;
    }

    public void guardarTransferencia(Transferencia transferencia) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(transferencia);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }

    }
}
