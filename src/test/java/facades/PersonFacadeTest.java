package facades;

import DTO.PersonDTO;
import entities.Address;
import utils.EMF_Creator;
import entities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private Person p1;
    private Person p2;

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {

        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getFacade(emf);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Person p").executeUpdate();
        em.createNativeQuery("ALTER TABLE `PERSON` AUTO_INCREMENT = 1").executeUpdate();

    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        p1 = new Person();
      
        p1.setEmail("john@email.dk");
        p1.setFirstName("John");
        p1.setLastName("JOhnson");

        p2 = new Person();
        p2.setEmail("Pete@mail.com");
        p2.setFirstName("Pete");
        p2.setLastName("Petersen");

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE from Person").executeUpdate();
            em.persist(p1);
            em.persist(p2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }

    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Person p").executeUpdate();
        em.createNativeQuery("ALTER TABLE `PERSON` AUTO_INCREMENT = 1").executeUpdate();
        em.getTransaction().commit();
    }

    
    @Test
    public void testGetAll() {
        List<PersonDTO> result = facade.getAllPersons();
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetById() {
        int id = p1.getId();
        PersonDTO p = facade.getPersonById(id);
        assertEquals("John", p.getFirstName() );
    }
    
    @Test
    public void testGetByEmail() {
        PersonDTO p = facade.getPersonByEmail("Pete@mail.com");
        assertEquals("Pete@mail.com", p.getEmail());
    }
    
    @Test
    public void testGetByName() {
        List<PersonDTO> p = facade.getPersonsByName("Pete", "Petersen");
        assertEquals(1, p.size());
    }
    
    
}
