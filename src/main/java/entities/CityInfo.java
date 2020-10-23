/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author cecilie
 */
@Entity
public class CityInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int zipCode;
    private String city;

    @OneToMany(mappedBy = "cityInfo")
    private List<Address> addresses;

    public CityInfo() {
    }
    
    public CityInfo(String city, List<Address> addresses) {
    public CityInfo(int zipCode, String city, List<Address> addresses) {
        this.zipCode = zipCode;
        this.city = city;
        this.addresses = addresses;
    }
    public CityInfo(int zipCode, String city) {
        this.zipCode = zipCode;
        this.city = city;
    }


    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

}
