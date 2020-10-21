/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    private Person p1;
    private Person p2;
    private List<Person> personList;

    @BeforeAll
    public static void setUpClass() {

        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = HobbyFacade.getFacade(emf);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
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
        h1 = new Hobby();
        h1.setName("Basketball");
        h1.setDescription("Sport");

        h2 = new Hobby();
        h2.setName("Handball");
        h2.setDescription("Sport");

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Hobby h").executeUpdate();
            em.persist(h1);
            em.persist(h2);
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
        HobbyDTO h = facade.getHobbybyId(id);
        assertEquals("Basketball", h.getName());
    }

    @Test
    public void testAddHobby() {
        
    }

    @Test
    public void testEditHobby() {
<<<<<<< HEAD
        HobbyDTO h = facade.getHobbybyId(h1.getId());
        assertEquals("Basketball", h.getName());
        h1.setName("Baseball");
        HobbyDTO edited = new HobbyDTO(h1);
        HobbyDTO edit = facade.editHobby(edited);
        HobbyDTO getEdited = facade.getHobbybyId(h1.getId());
        assertEquals("Baseball", edit.getName());
=======
        HobbyDTO hbDTO = facade.getHobbyById(h1.getId());
        assertEquals("Basketball", hbDTO.getName());
        h1.setName("baseball");
        HobbyDTO edited = new HobbyDTO(h1);
        HobbyDTO edit = facade.editHobby(edited);
        HobbyDTO getEdited = facade.getHobbyById(h1.getId());
        
        assertEquals("baseball", edit.getName());
>>>>>>> 7cf5776a9475befe5e8e17a95e04842fd9e0dd42
    }

    @Test
    public void getHobbyByName() {
        Hobby result = facade.getHobbyByName("Basketball");
        assertEquals(result.getName(), "Basketball");
    }

    @Test
    public void testGetCountOfPersonsWithHobby() {
        
    }
}
