/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.CityInfoDTO;
import DTO.PersonDTO;
import entities.CityInfo;
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
import utils.EMF_Creator;

/**
 *
 * @author rh
 */
public class CityInfoFacadeTest {

    private static EntityManagerFactory emf;
    private static CityFacade facade;
    private CityInfo c1;
    private CityInfo c2;
    

    @BeforeAll
    public static void setUpClass() {

        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = CityFacade.getFacade(emf);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM CityInfo c").executeUpdate();
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
        c1 = new CityInfo();
        c2 = new CityInfo();
        c1.setCity("Køge");
        c1.setZipCode(2830);
        
        c2.setCity("Virum");
        c2.setZipCode(2840);
      
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE from CityInfo c").executeUpdate();
            em.persist(c1);
            em.persist(c2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }

    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM CityInfo c").executeUpdate();
        em.createNativeQuery("ALTER TABLE `CITYINFO` AUTO_INCREMENT = 1").executeUpdate();
        em.getTransaction().commit();
    }

    
    @Test
    public void testGetAll() {
        List<CityInfoDTO> result = facade.getAllCities();
        assertEquals(2, result.size());
    }

    
    
}


