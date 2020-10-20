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
}
