package rest;

import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import facades.PersonFacade;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private Person p1, p2, p3;
    private Address a1, a2, a3;
    private CityInfo c1, c2, c3;
    private Hobby h1, h2, h3;
    private Phone ph1, ph2, ph3;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private static PersonFacade facade;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

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
        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
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
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/person").then().statusCode(200);
    }
    
    @Test
    public void getPersonByIdTest(){
       given()
                .contentType("application/json")
                .get("/person/{id}", p1.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("id", equalTo(p1.getId()));
                 
        
    }
    
    @Test
    public void getPersonByEmailTest(){
        given()
                .contentType("application/json")
                .get("/person/email/{email}", p1.getEmail())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("email", equalTo("john@email.dk"));
    }
    
    // virker ikke
    /*@Test
    public void getPersonAllTest(){
        given()
                .contentType("application/json")
                .get("/person/all")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("person", hasItems(3));
    }
    
    @Test
    public void getPersonByCityTest(){
        given()
                .contentType("application/json")
                .get("/person/zip/{zip}", p1.getAddress().getCityInfo().getZipCode())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("personer", hasItem(1));
    }
    */
}
