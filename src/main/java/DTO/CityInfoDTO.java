/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.CityInfo;
import java.io.Serializable;

/**
 *
 * @author rh
 */
public class CityInfoDTO implements Serializable {

    private int zipCode;
    private String city;

    public CityInfoDTO(CityInfo cityInfo) {
        this.zipCode = cityInfo.getZipCode();
        this.city = cityInfo.getCity();
    }

    public int getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }
    
}
