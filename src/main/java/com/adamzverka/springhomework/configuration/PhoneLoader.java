package com.adamzverka.springhomework.configuration;

import com.adamzverka.springhomework.phone.Phone;
import com.adamzverka.springhomework.phone.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * The purpose of this class is to load sample data to the inmemory DB on application startup.
 */
@Component
public class PhoneLoader implements ApplicationRunner {

    private final PhoneService phoneService;

    @Autowired
    public PhoneLoader(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @Override
    public void run(ApplicationArguments args) {
        phoneService.savePhone(new Phone(1, "1234", "+421123456789"));
    }
}
