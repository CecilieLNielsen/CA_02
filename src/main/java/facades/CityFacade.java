/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.CityDTO;
import entities.CityInfo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author hassanainali
 */
public class CityFacade {
    
    private static CityFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private CityFacade() {}
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CityFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CityFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    //TODO Remove/Change this before use
    public CityDTO getCityById(int id){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            CityDTO cDTO = new CityDTO(em.find(CityInfo.class, id));
            em.getTransaction().commit();
            return cDTO;
        }finally{  
            em.close();
        }
    }
    
    // ...
    /*
    public String addCity(CityDTO cDTO){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(new CityInfo(cDTO.getCity(), cDTO.getZipCode()));
            em.getTransaction().commit();
            return "City added successfully. ";
        }finally{  
            em.close();
        }
    }
    
    public String deleteCity(int id){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.remove(em.find(CityInfo.class, id));
            em.getTransaction().commit();
            return "City deleted successfully. ";
        }finally{  
            em.close();
        }
    }
    */
    
    // EKSTRA 
    public List<CityDTO> allCities(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<CityInfo> query = em.createQuery("SELECT c FROM CityInfo c", CityInfo.class);
        List<CityDTO> cities = new ArrayList();
        for(CityInfo c : query.getResultList()){
            cities.add(new CityDTO(c));
        }
        return cities;
    }
}