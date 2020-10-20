/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.Address;
import java.io.Serializable;

/**
 *
 * @author rh
 */
public class AddressDTO implements Serializable {

    private String street;
    private String addtionalInfo;

    public AddressDTO(Address address) {
        this.street = address.getStreet();
        this.addtionalInfo = address.getAdditionalInfo();
    }
}
