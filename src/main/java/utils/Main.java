/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import DTO.PersonDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import facades.HobbyFacade;
import facades.PersonFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author cecilie
 */
public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        HobbyFacade hf = HobbyFacade.getFacade(emf);
        PersonFacade pf = PersonFacade.getFacade(emf);
        
        List<Phone> phones = new ArrayList();
        List<Hobby> hobbies = new ArrayList();
        Address address = new Address("Gadevang 14", "Adresse 2", new CityInfo(3370, "Melby"));
        phones.add(new Phone("34324634", "Privatnummer"));
        hobbies.add(new Hobby("Håndbold", "Et boldspil med hånden"));
        Person person = new Person("test@outlook.dk", "Sanne", "Larsen");
        person.setAddress(address);
        person.setHobbies(hobbies);
        person.setPhones(phones);
        PersonDTO personDTO = new PersonDTO(person);
        
        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
        System.out.println(person.toString());
        
        for (PersonDTO p : pf.getPersonsByCity(3370)){
            System.out.println("test : " + p.toString());
        }
        for (PersonDTO p : pf.getPersonsByName(person.getFirstName(), person.getLastName())){
            System.out.println("test2 : " + p.toString());
        }
        
        em.close();
        
        
        
        
        
        // TO ADD
        //pf.addPerson(personDTO);
        // TO EDIT
        /*personDTO.setId(10);
        pf.editPerson(personDTO);
        pf.editPersonAddress(personDTO.getId(), personDTO.getAddress());
        pf.editPersonHobbies(personDTO.getId(), personDTO.getHobbies());
        pf.editPersonPhones(personDTO.getId(), personDTO.getPhones());*/
        
    }
    
}
