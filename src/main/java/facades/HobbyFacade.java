package facades;

import DTO.HobbyDTO;
import entities.Hobby;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author hassanainali
 */
public class HobbyFacade {

    private static HobbyFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private HobbyFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static HobbyFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HobbyFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<HobbyDTO> getAllHobbies() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Hobby> query = em.createQuery("SELECT h FROM Hobby h", Hobby.class);
            List<HobbyDTO> hobbies = new ArrayList();
            for (Hobby h : query.getResultList()) {
                hobbies.add(new HobbyDTO(h));
            }

            return hobbies;
        } finally {
            em.close();
        }
    }
    
    public HobbyDTO getHobbyById(int id) {
        EntityManager em = emf.createEntityManager();
        Hobby hobby = em.find(Hobby.class, id);
        return new HobbyDTO(hobby);
    }

    public HobbyDTO addHobby(HobbyDTO hobbyDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Hobby hobby = new Hobby(hobbyDTO.getName(), hobbyDTO.getDescription());
            em.persist(hobby);
            em.getTransaction().commit();
            return new HobbyDTO(hobby);
        } finally {
            em.close();
        }
    }

    public HobbyDTO editHobby(HobbyDTO hobbyDTO) {
        EntityManager em = getEntityManager();
        Hobby hobby = em.find(Hobby.class, hobbyDTO.getId());

        hobby.setDescription(hobbyDTO.getDescription());
        hobby.setName(hobbyDTO.getName());

        try {
            em.getTransaction().begin();
            em.merge(hobby);
            em.getTransaction().commit();
            return new HobbyDTO(hobby);
        } finally {
            em.close();
        }
    }

    public Hobby getHobbyByName(String name) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Hobby> query = em.createQuery("SELECT h FROM Hobby h WHERE h.name = :name", Hobby.class).setParameter("name", name);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    public long getCountOfPersonsWithHobby(String hobbyName) {
        EntityManager em = getEntityManager();
        try {
            Hobby hobby = getHobbyByName(hobbyName);
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(p) FROM Person p WHERE :hobby MEMBER OF p.hobbies", Long.class).setParameter("hobby", hobby);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}