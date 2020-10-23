package facades;

import DTO.HobbyDTO;
import DTO.PersonDTO;
import entities.Hobby;
import entities.Person;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author rh
 */
//Uncomment the line below, to temporarily disable this test
//@Disabled
public class HobbyFacadeTest {

    private static EntityManagerFactory emf;
    private static HobbyFacade facade;
    private Hobby h1;
    private Hobby h2;
    private Hobby h3;
    private Person p1;
    private Person p2;
    private List<Person> personList;

    @BeforeAll
    public static void setUpClass() {

        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = HobbyFacade.getFacade(emf);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Hobby h").executeUpdate();
        em.createQuery("DELETE FROM Person p").executeUpdate();

        em.createNativeQuery("ALTER TABLE `HOBBY` AUTO_INCREMENT = 1").executeUpdate();

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
        h1 = new Hobby();
        h1.setName("Basketball");
        h1.setDescription("Sport");

        h2 = new Hobby();
        h2.setName("Handball");
        h2.setDescription("Sport");

        p1.setFirstName("The");
        p1.setLastName("Basketball player");
        List<Hobby> hobbies = new ArrayList();
        hobbies.add(h1);

        p1.setHobbies(hobbies);

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Hobby h").executeUpdate();
            em.createQuery("DELETE FROM Person p").executeUpdate();
            em.persist(h1);
            em.persist(h2);
            em.persist(p1);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Hobby h").executeUpdate();
         em.createQuery("DELETE FROM Person p").executeUpdate();
        em.createNativeQuery("ALTER TABLE `HOBBY` AUTO_INCREMENT = 1").executeUpdate();
        em.getTransaction().commit();
    }

    @Test
    public void testGetAllHobbies() {
        List<HobbyDTO> hb = facade.getAllHobbies();
        assertEquals(2, hb.size());
    }

    @Test
    public void testGetHobbyById() {
        int id = h1.getId();
        HobbyDTO h = facade.getHobbyById(id);
        assertEquals("Basketball", h.getName());
    }

    @Test
    public void testAddHobby() {
        h3 = new Hobby();
        h3.setName("Bowling");
        h3.setDescription("Hit the pins");
        h3.setId(3);
        HobbyDTO addThis = new HobbyDTO(h3);
        facade.addHobby(addThis);
        HobbyDTO getNew = facade.getHobbyById(h3.getId());
        assertEquals("Bowling", getNew.getName());
        assertEquals("Hit the pins", getNew.getDescription());
                
        

    }

    @Test
    public void testEditHobby() {
        HobbyDTO hbDTO = facade.getHobbyById(h1.getId());
        assertEquals("Basketball", hbDTO.getName());
        h1.setName("baseball");
        HobbyDTO edited = new HobbyDTO(h1);
        HobbyDTO edit = facade.editHobby(edited);
        HobbyDTO getEdited = facade.getHobbyById(h1.getId());
        assertEquals("baseball", getEdited.getName());
    }

    @Test
    public void getHobbyByName() {
        Hobby result = facade.getHobbyByName("Basketball");
        assertEquals(result.getName(), "Basketball");
    }

    @Test
    public void testGetCountOfPersonsWithHobby() {
        long count = facade.getCountOfPersonsWithHobby("Basketball");
        assertEquals(1, count);
    }
}
