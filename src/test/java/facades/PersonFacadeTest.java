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

    private Person p1, p2, p3, p4;
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
        deleteTables(em);  
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
        
        a1 = new Address("Lyngbyvej", "100", new CityInfo(2800, "Lyngby"));
        a2 = new Address("Gentoftegade", "100", new CityInfo(2820, "Gentofte"));
        a3 = new Address("Holtevej", "100", new CityInfo(2840, "Holte"));

        List<Phone> phones1 = new ArrayList();
        List<Phone> phones2 = new ArrayList();
        List<Phone> phones3 = new ArrayList();
        
        phones1.add(new Phone("1111", "Privatnummer"));
        phones2.add(new Phone("2222", "Privatnummer"));
        phones3.add(new Phone("3333", "Privatnummer"));
        
        List<Hobby> hobbies1 = new ArrayList();
        List<Hobby> hobbies2 = new ArrayList();
        List<Hobby> hobbies3 = new ArrayList();
        
        hobbies1.add(new Hobby("Basketball", "Sport"));
        hobbies2.add(new Hobby("Handball", "Sport"));
        hobbies3.add(new Hobby("Poker", "Card games"));
        
        p1.setAddress(a1);
        p1.setHobbies(hobbies1);
        p1.setPhones(phones1);
        
        p2.setAddress(a2);
        p2.setHobbies(hobbies2);
        p2.setPhones(phones2);
        
        p3.setAddress(a3);
        p3.setHobbies(hobbies3);
        p3.setPhones(phones3);

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            deleteTables(em);
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        deleteTables(em);
        em.createNativeQuery("ALTER TABLE `PERSON` AUTO_INCREMENT = 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE `CITYINFO` AUTO_INCREMENT = 1").executeUpdate();
        em.getTransaction().commit();
    }

    private static void deleteTables(EntityManager em) {
        em.createQuery("DELETE FROM Phone p").executeUpdate();
        em.createQuery("DELETE FROM Person p").executeUpdate();
        em.createQuery("DELETE FROM Address a").executeUpdate();
        em.createQuery("DELETE FROM CityInfo c").executeUpdate();
        em.createQuery("DELETE FROM Hobby h").executeUpdate();
        em.createNativeQuery("ALTER TABLE `PERSON` AUTO_INCREMENT = 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE `CITYINFO` AUTO_INCREMENT = 1").executeUpdate();
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
        List<PersonDTO> result = facade.getPersonsByCity(p1.getAddress().getCityInfo().getZipCode()); // Skal gerne være 2800. kan ikke med 2800 kan kun hvis vi bruger post nr på denne måde
        assertEquals(1, result.size());
    }

    @Test // VIRKER IKKE MED 2800 postnr. fejl i postnr id..
    public void testGetPersonsByAddress() {
        //List<PersonDTO> result = facade.getPersonsByAddress(p1.getAddress().getCityInfo().getZipCode(), p1.getAddress().getStreet());
        List<PersonDTO> result = facade.getPersonsByAddress(p1.getAddress().getCityInfo().getZipCode(), "Lyngbyvej");
        assertEquals(1, result.size());
    }

//    @Test // OK!!!
//    public void testAddPerson() {
//        p4.setFirstName("Jamie");
//        p4.setLastName("Jamieson");
//        p4.setEmail("jamie@jamieson.dk");
//        p4.setId(4);
//        PersonDTO p4DTO = new PersonDTO(p4);
//        facade.addPerson(p4DTO);
//        PersonDTO added = facade.getPersonByEmail(p4.getEmail());
//        assertEquals("jamie@jamieson", added.getEmail());
//        
//    }

    @Test // OK!!!
    public void testDeletePerson() {

    }

    @Test // OK!!!
    public void testEditPerson() {
        

    }

    @Test 
    public void testGetAllPersons() {
       List<PersonDTO> result = facade.getAllPersons();
       assertEquals(3, result.size());  
    }

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
