package com.adamzverka.springhomework;

import com.adamzverka.springhomework.phone.Phone;
import com.adamzverka.springhomework.phone.PhoneService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PhoneServiceImplTest {

    @Autowired
    private PhoneService phoneService;

    @BeforeEach
    public void initDatabase() {
        phoneService.savePhone(new Phone(1, "1234", "+421123456789"));
    }

    @AfterEach
    public void clearDatabase() {
        phoneService.deleteAllPhones();
    }

    @Test
    void validatePinTest() {
        Phone phoneInput = new Phone("1234", "+421123456789");
        Phone phoneInput2 = new Phone("0000", "+421123456789");
        phoneService.validatePin(phoneInput);
        assertEquals(true, phoneService.validatePin(phoneInput));
        assertEquals(false, phoneService.validatePin(phoneInput2));
    }

    @Test
    void getPhonesTest() {
        assertEquals(1, phoneService.getPhones().size());
    }

}
