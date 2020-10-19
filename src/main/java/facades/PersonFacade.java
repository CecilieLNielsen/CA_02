package facades;

import DTO.PersonDTO;
import entities.Person;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private PersonFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    //TODO Remove/Change this before use
    public PersonDTO getUserById(int id){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            PersonDTO pDTO = new PersonDTO(em.find(Person.class, id));
            em.getTransaction().commit();
            return pDTO;
        }finally{  
            em.close();
        }
    }
    
    public String addPerson(PersonDTO pDTO){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(new Person(pDTO.getEmail(), pDTO.getFirstName(), pDTO.getLastName()));
            em.getTransaction().commit();
            return "Person added successfully. ";
        }finally{  
            em.close();
        }
    }
    
    public String deletePerson(int id){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.remove(em.find(Person.class, id));
            em.getTransaction().commit();
            return "Person deleted successfully. ";
        }finally{  
            em.close();
        }
    }
    
    public List<PersonDTO> allPersons(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
        List<PersonDTO> persons = new ArrayList();
        for(Person p : query.getResultList()){
            persons.add(new PersonDTO(p));
        }
        return persons;
    }
    
}
