package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "person", cascade = {CascadeType.PERSIST})
    private List<Phone> phones;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "PERSON_HAS_HOBBY",
            joinColumns = @JoinColumn(name = "personId"),
            inverseJoinColumns = @JoinColumn(name = "hobbyId"))
    private List<Hobby> hobbies;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Address address;

    public Person(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phones = phones;
        this.hobbies = hobbies;
        this.address = address;
        
        for (Phone phone : phones) {
            phone.setPerson(this);
        }
        
        for (Hobby hobby : hobbies) {
            hobby.getPersons().add(this);
        }
        
        address.getPersons().add(this);          
    }
//<<<<<<< HEAD
//=======
//
//    public Person(String email, String firstName, String lastName) {
//        this.email = email;
//        this.firstName = firstName;
//        this.lastName = lastName;
//    }
//>>>>>>> d8e91e86c1f9179535861d4128966617c697c54a
    

    public Person(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        for (Phone phone : phones) {
            phone.setPerson(this);
        }
        this.phones = phones;
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
