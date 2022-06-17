package com.adamzverka.springhomework.phone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneServiceImpl implements PhoneService {

    private PhoneRepository phoneRepository;

    @Autowired
    public PhoneServiceImpl(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @Override
    public boolean validatePin(String phoneNumber, String pin) {
        return phoneRepository.existsPhoneByPhoneNumberAndPin(phoneNumber, pin);
    }

    @Override
    public List<Phone> getPhones() {
        return phoneRepository.findAll();
    }
}
