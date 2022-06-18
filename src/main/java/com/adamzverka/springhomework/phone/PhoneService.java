package com.adamzverka.springhomework.phone;

import java.util.List;

/**
 * Service encapsulates JPA repository and implements required business logic
 */
public interface PhoneService {
    boolean validatePin(Phone phone);
    Phone savePhone(Phone phone);
    List<Phone> getPhones();
    void deleteAllPhones();
}
