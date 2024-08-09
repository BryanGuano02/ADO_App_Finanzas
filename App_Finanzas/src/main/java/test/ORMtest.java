package test;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import modelo.entidades.Cuenta;


public class ORMtest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("chaucherita_PU");
		EntityManager em = emf.createEntityManager();

		Cuenta cuenta = new Cuenta(1, "Bryan", 1.2);
		em.getTransaction().begin();
		em.persist(cuenta);
		em.getTransaction().commit();

	}

}
