/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.Phone;
import java.io.Serializable;

/**
 *
 * @author rh
 */
public class PhoneDTO implements Serializable {

    private String number;
    private String description;

    public PhoneDTO(Phone phone) {
        this.number = phone.getNumber();
        this.description = phone.getDescription();
    }

    public String getNumber() {
        return number;
    }

<<<<<<< HEAD
    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
=======
    public String getDescription() {
        return description;
    }
>>>>>>> 7cf5776a9475befe5e8e17a95e04842fd9e0dd42
    
}
