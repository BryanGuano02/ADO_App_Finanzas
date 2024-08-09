package modelo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.entidades.CategoriaIngreso;
import modelo.entidades.Cuenta;

import java.time.LocalDate;
import java.util.List;

public class CategoriaIngresoDAO {
    private EntityManagerFactory emf;

    public CategoriaIngresoDAO() {
        emf = Persistence.createEntityManagerFactory("chaucherita_PU");
    }


    public List<CategoriaIngreso> obtenerTodoPorFecha(LocalDate desde, LocalDate hasta) {
        EntityManager em = emf.createEntityManager();

        List<CategoriaIngreso> categoriasIngreso = null;

        try {
            TypedQuery<CategoriaIngreso> query = em.createQuery("SELECT ca FROM CategoriaIngreso ca", CategoriaIngreso.class);
            categoriasIngreso = query.getResultList();
            if (categoriasIngreso.isEmpty()) {
                categoriasIngreso.add(new CategoriaIngreso(1, "Sueldo"));
                categoriasIngreso.add(new CategoriaIngreso(2, "Sueldo2"));
                categoriasIngreso.add(new CategoriaIngreso(3, "Beca"));
                categoriasIngreso.add(new CategoriaIngreso(4, "Subvención"));

                em.getTransaction().begin();
                em.persist(categoriasIngreso);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
        return categoriasIngreso;
    }

}
