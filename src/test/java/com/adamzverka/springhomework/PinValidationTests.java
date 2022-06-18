package com.adamzverka.springhomework;

import com.adamzverka.springhomework.phone.Phone;
import com.adamzverka.springhomework.phone.PhoneDTO;
import com.adamzverka.springhomework.phone.PhoneService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// change the lifecycle of the test instance to be able to use @BeforeAll on a non-static method
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PinValidationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PhoneService phoneService;

    private String url;

    @BeforeAll
    public void initDatabase() {
        phoneService.savePhone(new Phone(1, "1234", "+421123456789"));
        phoneService.savePhone(new Phone(2, "0000", "+421111111111"));
        phoneService.savePhone(new Phone(3, "1111", "+420111111112"));

        url = "http://localhost:" + port + "/api/v1/phones";
    }

    @AfterAll
    public void clearDatabase() {
        phoneService.deleteAllPhones();
    }

    @Test
    public void validatePinWithCorrectInputs() {
        PhoneDTO phoneInput = new PhoneDTO("1234", "+421 123456789");
        ResponseEntity<String> response = restTemplate.postForEntity(url, phoneInput, String.class);
        assertEquals("PIN je valídny.", response.getBody());
    }

    @Test
    public void validateWithIncorrectInputs() {
        PhoneDTO phoneInput = new PhoneDTO("1235", "+421 77777777");
        ResponseEntity<String> response = restTemplate.postForEntity(url, phoneInput, String.class);
        assertEquals("PIN nie je valídny.", response.getBody());
    }

    @Test
    public void validateWithIncorrectPin() {
        PhoneDTO phoneInput = new PhoneDTO("1235", "+421 123456789");
        ResponseEntity<String> response = restTemplate.postForEntity(url, phoneInput, String.class);
        assertEquals("PIN nie je valídny.", response.getBody());
    }

    @Test
    public void validateWithIncorrectPinContainingLetters() {
        PhoneDTO phoneInput = new PhoneDTO("abcd", "+421 123456789");
        ResponseEntity<String> response = restTemplate.postForEntity(url, phoneInput, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void validateWithIncorrectPinContainingMoreNumbers() {
        PhoneDTO phoneInput = new PhoneDTO("12345", "+421 123456789");
        ResponseEntity<String> response = restTemplate.postForEntity(url, phoneInput, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void validateWithNullPin() {
        PhoneDTO phoneInput = new PhoneDTO(null, "+421 123456789");
        ResponseEntity<String> response = restTemplate.postForEntity(url, phoneInput, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void validateWithNullPhoneNumber() {
        PhoneDTO phoneInput = new PhoneDTO("4567", null);
        ResponseEntity<String> response = restTemplate.postForEntity(url, phoneInput, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
