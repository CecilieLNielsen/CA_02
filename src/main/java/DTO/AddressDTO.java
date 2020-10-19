/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author rh
 */
public class AddressDTO {
    
    private String street;
    private String addtional;

    public AddressDTO() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddtional() {
        return addtional;
    }

    public void setAddtional(String addtional) {
        this.addtional = addtional;
    }
    
    
}
