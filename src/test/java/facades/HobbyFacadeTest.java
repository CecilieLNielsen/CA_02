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
public class HobbyFacadeTest {
    

//Uncomment the line below, to temporarily disable this test
//@Disabled


    private static EntityManagerFactory emf;
    private static HobbyFacade facade;
    private Hobby h1;
    private Hobby h2;



    @BeforeAll
    public static void setUpClass() {

        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = HobbyFacade.getFacade(emf);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Person p").executeUpdate();
        em.createNativeQuery("ALTER TABLE `MEMBER` AUTO_INCREMENT = 1").executeUpdate();

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
       h1.setDescription("Boldsport");
       
       h2 = new Hobby();
       h2.setName("Handball");
       h2.setDescription("Boldsport");
       
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE from Hobby").executeUpdate();
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
        em.createNativeQuery("ALTER TABLE `MEMBER` AUTO_INCREMENT = 1").executeUpdate();
        em.getTransaction().commit();
    }

    @Test
    public void getAllTest(){
        List<HobbyDTO> hb = facade.getAllHobbies();
        assertEquals(2, hb.size());
    }
   
    


    
}
