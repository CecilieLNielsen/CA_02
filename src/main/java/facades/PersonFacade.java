package facades;

import DTO.AddressDTO;
import DTO.HobbyDTO;
import DTO.PersonDTO;
import DTO.PhoneDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public PersonDTO getPersonById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.id = :id", Person.class).setParameter("id", id);
            Person person = query.getSingleResult();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    public PersonDTO getPersonByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.email = :email", Person.class).setParameter("email", email);
            Person person = query.getSingleResult();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    public List<PersonDTO> getPersonsByName(String firstName, String lastName) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.firstName = :firstName AND p.lastName = :lastName", Person.class).setParameter("firstName", firstName).setParameter("lastName", lastName);
            List<PersonDTO> persons = new ArrayList();
            for (Person p : query.getResultList()) {
                persons.add(new PersonDTO(p));
            }
            return persons;
        } finally {
            em.close();
        }
    }

    public List<PersonDTO> getPersonsByCity(int zipCode) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.address.cityInfo.zipCode = :zipCode", Person.class).setParameter("zipCode", zipCode);
            List<PersonDTO> persons = new ArrayList();
            for (Person p : query.getResultList()) {
                persons.add(new PersonDTO(p));
            }
            return persons;
        } finally {
            em.close();
        }
    }

    public List<PersonDTO> getPersonsByAddress(int zipCode, String street) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.address.cityInfo.zipCode = :zipCode AND p.address.street = :street", Person.class).setParameter("zipCode", zipCode).setParameter("street", street);
            List<PersonDTO> persons = new ArrayList();
            for (Person p : query.getResultList()) {
                persons.add(new PersonDTO(p));
            }
            return persons;
        } finally {
            em.close();
        }
    }

    public PersonDTO addPerson(PersonDTO personDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            List<Phone> phones = new ArrayList();
            for (PhoneDTO phone : personDTO.getPhones()) {
                phones.add(new Phone(phone.getNumber(), phone.getDescription()));
            }
            List<Hobby> hobbies = new ArrayList();
            for (HobbyDTO hobby : personDTO.getHobbies()) {
                hobbies.add(new Hobby(hobby.getName(), hobby.getDescription()));
            }
            AddressDTO addressDTO = personDTO.getAddress();
            CityInfo cityInfo = em.find(CityInfo.class, addressDTO.getCityInfo().getZipCode());
            Address address = new Address(addressDTO.getStreet(), addressDTO.getAddtionalInfo(), cityInfo);
            
            Person person = new Person(personDTO.getEmail(), personDTO.getFirstName(), personDTO.getLastName(), phones, hobbies, address);
            em.persist(person);
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    public PersonDTO deletePerson(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Person person = em.find(Person.class, id);
            em.remove(person);
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    public PersonDTO editPerson(PersonDTO personDTO) {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, personDTO.getId());

        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setEmail(personDTO.getEmail());

        try {
            em.getTransaction().begin();
            em.merge(person);
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    public List<PersonDTO> getAllPersons() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
            List<PersonDTO> persons = new ArrayList();
            for (Person p : query.getResultList()) {
                persons.add(new PersonDTO(p));
            }
            return persons;
        } finally {
            em.close();
        }
    }

    public List<PersonDTO> getPersonsByHobby(String hobbyName) {
        EntityManager em = emf.createEntityManager();
        try {
            Hobby hobby = HobbyFacade.getFacade(emf).getHobbyByName(hobbyName);
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE :hobby MEMBER OF p.hobbies", Person.class).setParameter("hobby", hobby);
            List<PersonDTO> persons = new ArrayList();
            for (Person p : query.getResultList()) {
                persons.add(new PersonDTO(p));
            }
            return persons;
        } finally {
            em.close();
        }
    }

    public PersonDTO getPersonByPhoneNumber(String number) {
        EntityManager em = emf.createEntityManager();
        try {
            Phone phone = getPhoneByNumber(number);
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE :phone MEMBER OF p.phones", Person.class).setParameter("phone", phone);
            return new PersonDTO(query.getSingleResult());
        } finally {
            em.close();
        }
    }

<<<<<<< HEAD
    private Phone getPhoneByNumber(String number) { // ?????
=======
    private Phone getPhoneByNumber(String number) {
>>>>>>> 7cf5776a9475befe5e8e17a95e04842fd9e0dd42
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Phone> query = em.createQuery("SELECT p FROM Phone p WHERE p.number = :number", Phone.class).setParameter("number", number);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
    /* 
    public String deletePhone(int number){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.remove(em.find(Phone.class, number));
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

     */

}
