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

    public String getDescription() {
        return description;
    }
}