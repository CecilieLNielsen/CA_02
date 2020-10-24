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
        deleteTables(em);
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
        //    em.createQuery("DELETE FROM Phone p").executeUpdate();
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
    
    @Test
    public void getPersonAllTest(){
        given()
                .contentType("application/json")
                .get("/person/all")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("person", hasSize(3));
    }
    
    @Test
    public void getPersonByCityTest(){
        given()
                .contentType("application/json")
                .get("/person/zip/{zip}", p1.getAddress().getCityInfo().getZipCode())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("person", hasSize(1));
    }
    
    @Test
    public void getPersonByNamesTest(){
        given()
                .contentType("application/json")
                .get("/person/firstName/{firstName}/lastName/{lastName}", p1.getFirstName(), p1.getLastName())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("firstName", hasItem("" + p1.getFirstName() + ""))
                .body("lastName", hasItem(p1.getLastName()));
    }
    
    @Test
    public void getPersonsByCityTest(){
        given()
                .contentType("application/json")
                .get("/person/zip/{zip}", p1.getAddress().getCityInfo().getZipCode())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("person", hasSize(1));
    }
    
    @Test
    public void getPersonByCityAndStreetTest(){
        given()
                .contentType("application/json")
                .get("/person/zip/{zip}/street/{street}", p1.getAddress().getCityInfo().getZipCode(), p1.getAddress().getStreet())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("person", hasSize(1));
    }
    
    /*@Test
    public void deleteTest(){
        given()
                .contentType("application/json")
                .delete("/person/{id}", p1.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("id", hasItem(p1.getId()));
    }*/
    
    
}
