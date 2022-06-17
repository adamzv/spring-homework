package com.adamzverka.springhomework.configuration;

import com.adamzverka.springhomework.phone.Phone;
import com.adamzverka.springhomework.phone.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class PhoneLoader implements ApplicationRunner {

    // TODO: Change to service
    private PhoneRepository phoneRepository;

    @Autowired
    public PhoneLoader(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        phoneRepository.save(new Phone(1, "1234", "+421 123456789"));
    }
}
