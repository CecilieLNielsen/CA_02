package facades;

import DTO.AddressDTO;
import DTO.HobbyDTO;
import DTO.PersonDTO;
import DTO.PhoneDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Phone;
import entities.Person;
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
            
            Person person = new Person(personDTO.getEmail(), personDTO.getFirstName(), personDTO.getLastName());
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

    public PersonDTO editPersonAddress(int personId, AddressDTO address) {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, personId);
        CityInfo cityInfo = em.find(CityInfo.class, address.getCityInfo().getZipCode());
        
        person.getAddress().setStreet(address.getStreet());
        person.getAddress().setAdditionalInfo(address.getAddtionalInfo());
        person.getAddress().setCityInfo(cityInfo);

        try {
            em.getTransaction().begin();
            em.merge(person);
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    public PersonDTO editPersonPhones(int personId, List<PhoneDTO> phoneDTOs) {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, personId);
        List<Phone> oldPhones = person.getPhones();
        List<Phone> phones = new ArrayList();
        for (PhoneDTO phone : phoneDTOs) {
            phones.add(new Phone(phone.getNumber(), phone.getDescription()));
        }
        person.setPhones(phones);

        try {
            em.getTransaction().begin();
            for (Phone oldPhone : oldPhones) {
                em.remove(oldPhone);
            }
            em.merge(person);
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    public PersonDTO editPersonHobbies(int personId, List<HobbyDTO> hobbyDTOs) {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, personId);
        
        List<Hobby> hobbies = new ArrayList();
        for (HobbyDTO hobby : hobbyDTOs) {
            hobbies.add(new Hobby(hobby.getName(), hobby.getDescription()));
        }
        person.setHobbies(hobbies);

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
            List<Person> res = query.getResultList();
            List<PersonDTO> persons = new ArrayList();
            for (Person p : res) {
                persons.add(new PersonDTO(p));
            }
            System.out.println("Size persons" + persons.size());
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

    private Phone getPhoneByNumber(String number) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Phone> query = em.createQuery("SELECT p FROM Phone p WHERE p.number = :number", Phone.class).setParameter("number", number);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}
