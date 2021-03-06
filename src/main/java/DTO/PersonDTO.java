/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.Hobby;
import entities.Person;
import entities.Phone;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;  
/**
 *
 * @author rh
 */
public class PersonDTO implements Serializable {

    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private AddressDTO address;
    private List<PhoneDTO> phones = new ArrayList();
    private List<HobbyDTO> hobbies = new ArrayList();;

    public PersonDTO(Person person) {
        this.id = person.getId();
        this.email = person.getEmail();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.address = new AddressDTO(person.getAddress());    
        
        for (Phone phone : person.getPhones()) {
            phones.add(new PhoneDTO(phone));
        }
        
        for (Hobby hobby : person.getHobbies()) {
            hobbies.add(new HobbyDTO(hobby));
        }
    }
    
    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public List<PhoneDTO> getPhones() {
        return phones;
    }

    public List<HobbyDTO> getHobbies() {
        return hobbies;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PersonDTO{" + "id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", phones=" + phones + ", hobbies=" + hobbies + '}';
    }
}
