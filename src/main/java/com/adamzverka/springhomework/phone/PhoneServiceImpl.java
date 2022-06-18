package com.adamzverka.springhomework.phone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRepository phoneRepository;

    @Autowired
    public PhoneServiceImpl(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @Override
    public boolean validatePin(Phone phone) {
        return phoneRepository.existsPhoneByPhoneNumberAndPin(phone.getPhoneNumber(), phone.getPin());
    }

    @Override
    public Phone savePhone(Phone phone) {
        return phoneRepository.save(phone);
    }

    /**
     * Returns all phones saved in a database. (Used for debugging purposes)
     * @return
     */
    @Override
    public List<Phone> getPhones() {
        return phoneRepository.findAll();
    }

    @Override
    public void deleteAllPhones() {
        phoneRepository.deleteAll();
    }
}
