/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.PhoneDTO;
import entities.Person;
import entities.Phone;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author hassanainali
 */
public class PhoneFacade {
    
    private static PhoneFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private PhoneFacade() {}
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PhoneFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PhoneFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    //TODO Remove/Change this before use
    public PhoneDTO getPhoneByNumber(int id){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            PhoneDTO phDTO = new PhoneDTO(em.find(Phone.class, id));
            em.getTransaction().commit();
            return phDTO;
        }finally{  
            em.close();
        }
    }
    /*
    public String addPhone(PhoneDTO phDTO){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(new Phone(phDTO.getNumber(), phDTO.getDescription()));
            em.getTransaction().commit();
            return "Phone added successfully. ";
        }finally{  
            em.close();
        }
    }
    */
    public String deletePhone(int id){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.remove(em.find(Phone.class, id));
            em.getTransaction().commit();
            return "Phone deleted successfully. ";
        }finally{  
            em.close();
        }
    }
    
    public List<PhoneDTO> allPhones(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Phone> query = em.createQuery("SELECT p FROM Phone p", Phone.class);
        List<PhoneDTO> phones = new ArrayList();
        for(Phone p : query.getResultList()){
            phones.add(new PhoneDTO(p));
        }
        return phones;
    }
}
