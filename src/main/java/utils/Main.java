/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import facades.HobbyFacade;
import facades.PersonFacade;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author cecilie
 */
public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        
        
        HobbyFacade hf = HobbyFacade.getFacade(emf);
        System.out.println(hf.getCountOfPersonsWithHobby("Fodbold"));
        
        PersonFacade pf = PersonFacade.getFacade(emf);
        System.out.println(pf.getPersonsByHobby("Fodbold"));
        System.out.println(pf.getPersonByPhoneNumber("72727272"));
    }
    
}
