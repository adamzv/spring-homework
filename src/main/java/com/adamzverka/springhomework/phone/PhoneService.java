package com.adamzverka.springhomework.phone;

import java.util.List;

public interface PhoneService {
    boolean validatePin(String phoneNumber, String pin);
    List<Phone> getPhones();
}
