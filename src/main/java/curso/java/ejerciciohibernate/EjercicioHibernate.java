package curso.java.ejerciciohibernate;

import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import curso.java.ejerciciohibernate.entity.Alumno;
import curso.java.ejerciciohibernate.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;

public class EjercicioHibernate {
	private static final Logger logger = LogManager.getLogger(EjercicioHibernate.class);
	public static void main(String[] args) {
		logger.debug("Creando alumnos e insertándolos en la BBDD.");
        EntityManager em = JpaUtil.getEntityManager();
        Alumno a1=new Alumno("Alejandro", "Ruiz Rubio", "12345678P");
        Alumno a2=new Alumno("Daniel", "García Rodriguez", "12345678P");
        Alumno a3=new Alumno("Cristina", "Rodriguez García", "13253567P");
        //Insertar alumnos en la BBDD.
        em.getTransaction().begin();
        em.persist(a1);
        em.persist(a2);
        em.persist(a3);
        em.getTransaction().commit();
        logger.debug("Alumnos insertados.");
        //Listar alumnos.
        logger.debug("Se listan los alumnos de la BBDD.");
        List<Alumno> alumnos= em.createQuery("from Alumno", Alumno.class).getResultList();
        alumnos.forEach(System.out::println);
        //Buscar por id.
        logger.debug("Buscar por id. Introduce el id");
        Scanner lector = new Scanner(System.in);
        Long id = lector.nextLong();
        Alumno a=em.find(Alumno.class, id);
        logger.debug("Alumno encontrado:");
        System.out.println(a);
        //Buscar por DNI (Sólo 1 resultado)
        logger.debug("Buscar por DNI. Introduce el DNI:");
        Query query = em.createQuery("from Alumno a where a.dni=?1", Alumno.class);
        lector.nextLine();
        String dni=lector.nextLine();
        query.setParameter(1, dni);
        try {
        Alumno alumno= (Alumno) query.getSingleResult();
        System.out.println(alumno);
        }catch(NonUniqueResultException e) {
        	logger.error("Error. Se ha obtenido más de 1 resultado.");
        }catch(NoResultException ex) {
        	logger.warn("No hay ningún alumno con el dni "+dni);
        }

        em.close();
	}
}
