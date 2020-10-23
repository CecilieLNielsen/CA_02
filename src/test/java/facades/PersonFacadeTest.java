package facades;

import DTO.PersonDTO;
import entities.Address;
import entities.CityInfo;
import entities.Phone;
import entities.Hobby;
import utils.EMF_Creator;
import entities.Person;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;

    private Person p1, p2, p3;
    private Address a1, a2, a3;
    private CityInfo c1, c2, c3;
    private Hobby h1, h2, h3;
    private Phone ph1, ph2, ph3;

    @BeforeAll
    public static void setUpClass() {

        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getFacade(emf);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Person p").executeUpdate();
        em.createQuery("DELETE FROM Address a").executeUpdate();
        em.createQuery("DELETE FROM CityInfo c").executeUpdate();
        em.createQuery("DELETE FROM Hobby h").executeUpdate();
        em.createQuery("DELETE FROM Phone p").executeUpdate();
        em.createNativeQuery("ALTER TABLE `PERSON` AUTO_INCREMENT = 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE `CITYINFO` AUTO_INCREMENT = 1").executeUpdate();
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {

        p1 = new Person("john@email.dk", "John", "Johnson");

        p2 = new Person("pete@mail.com", "Pete", "Petersen");

        p3 = new Person("casperlund@mail.dk", "Casper", "Lund");

        a1 = new Address();
        a1.setStreet("Lyngbyvej");
        a1.setAdditionalInfo("100");
        a1.setCityInfo(c1);

        a2 = new Address();
        a2.setStreet("Gentoftegade");
        a2.setAdditionalInfo("100");
        a2.setCityInfo(c2);

        a3 = new Address();
        a3.setStreet("Holtevej");
        a3.setAdditionalInfo("100");
        a3.setCityInfo(c3);

        c1 = new CityInfo();
        c1.setZipCode(2800);
        c1.setCity("Lyngby");

        c2 = new CityInfo();
        c2.setZipCode(2820);
        c2.setCity("Gentofte");

        c3 = new CityInfo();
        c3.setZipCode(2840);
        c3.setCity("Holte");

        h1 = new Hobby();
        h1.setName("Basketball");
        h1.setDescription("Sport");

        h2 = new Hobby();
        h2.setName("Handball");
        h2.setDescription("Sport");

        h3 = new Hobby();
        h3.setName("Poker");
        h3.setDescription("Card games");

        ph1 = new Phone();
        ph1.setNumber("1111");

        ph2 = new Phone();
        ph2.setNumber("2222");

        ph3 = new Phone();
        ph3.setNumber("3333");

        List<Person> persons = new ArrayList();
        List<Person> persons2 = new ArrayList();
        persons2.add(p2);
        persons2.add(p3);
        persons.add(p1);
        a1.setPersons(persons);
        a2.setPersons(persons2);
        p1.setAddress(a1);
        a1.setCityInfo(c1);
        p2.setAddress(a2);
        p3.setAddress(a3);
        a1.setCityInfo(c2);

        List<Hobby> hobbies = new ArrayList();
        hobbies.add(h1);
        p1.setHobbies(hobbies);
        h1.setPersons(persons);

        //    List<Phone> phones = new ArrayList();
        //    phones.add(ph1);
        //    p1.setPhones(phones);
        //    ph1.setPerson(p1);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Person p").executeUpdate();
            em.createQuery("DELETE FROM Address a").executeUpdate();
            em.createQuery("DELETE FROM CityInfo c").executeUpdate();
            em.createQuery("DELETE FROM Hobby h").executeUpdate();
            //  em.createQuery("DELETE FROM Phone p").executeUpdate();
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.persist(a1);
            em.persist(h1);
            em.persist(a2);
            //  em.persist(ph1);
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
        em.createQuery("DELETE FROM Address a").executeUpdate();
        em.createQuery("DELETE FROM CityInfo c").executeUpdate();
        em.createQuery("DELETE FROM Hobby h").executeUpdate();  
        //    em.createQuery("DELETE FROM Phone p").executeUpdate();
        em.createNativeQuery("ALTER TABLE `PERSON` AUTO_INCREMENT = 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE `CITYINFO` AUTO_INCREMENT = 1").executeUpdate();
        em.getTransaction().commit();
    }

    @Test
    public void testGetPersonById() {
        int id = p1.getId();
        PersonDTO result = facade.getPersonById(id);
        assertEquals("John", result.getFirstName());
    }

    /*
    @Test
    public void testGetPersonByEmail() {
        PersonDTO result = facade.getPersonByEmail("pete@mail.com");
        assertEquals("pete@mail.com", result.getEmail());
    }

    @Test
    public void testGetPersonsByName() {
        List<PersonDTO> result = facade.getPersonsByName("Pete", "Petersen");
        assertEquals(1, result.size());
    }
     */
    @Test // HALVFÆRDIG
    public void testGetPersonsByCity() {
        List<PersonDTO> result = facade.getPersonsByCity(p1.getAddress().getCityInfo().getZipCode()); // Skal gerne være 2800.
        System.out.println(result);
        assertEquals(1, result.size());
    }

    @Test // VIRKER IKKE MED 2800 postnr. fejl i postnr id..
    public void testGetPersonsByAddress() {
        //List<PersonDTO> result = facade.getPersonsByAddress(p1.getAddress().getCityInfo().getZipCode(), p1.getAddress().getStreet());
        List<PersonDTO> result = facade.getPersonsByAddress(p1.getAddress().getCityInfo().getZipCode(), "Lyngbyvej");
        assertEquals(1, result.size());
    }

    @Test // OK!!!
    public void testAddPerson() {

    }

    @Test // OK!!!
    public void testDeletePerson() {

    }

    @Test // OK!!!
    public void testEditPerson() {

    }

//    @Test // OK!!!
//    public void testGetAllPersons() {
//        List<PersonDTO> result = facade.getAllPersons();
//        assertEquals(3, result.size());
//        
//    }

    @Test // OK!!!
    public void testGetPersonsByHobby() {
        List<PersonDTO> result = facade.getPersonsByHobby("Basketball");
        assertTrue(result.size() == 1);
    }
    /*
    @Test // OK!!!
    public void testGetPersonByPhoneNumber() {
        PersonDTO result = facade.getPersonByPhoneNumber(p1.getPhones().get(0).getNumber());
        assertEquals("John", result.getFirstName());
    }
    
    @Test // ??????
    public void testGetPhoneByNumber() {

    }
     */
}
