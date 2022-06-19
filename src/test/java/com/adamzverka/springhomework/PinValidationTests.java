package com.adamzverka.springhomework;

import com.adamzverka.springhomework.phone.Phone;
import com.adamzverka.springhomework.phone.PhoneDTO;
import com.adamzverka.springhomework.phone.PhoneService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PinValidationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PhoneService phoneService;

    private String url;

    @BeforeEach
    public void initDatabase() {
        phoneService.savePhone(new Phone(1, "1234", "+421123456789"));
        phoneService.savePhone(new Phone(2, "0000", "+421111111111"));
        phoneService.savePhone(new Phone(3, "1111", "+420111111112"));

        url = "http://localhost:" + port + "/api/v1/phones";
    }

    @AfterEach
    public void clearDatabase() {
        phoneService.deleteAllPhones();
    }

    @Test
    void validatePinWithCorrectInputs() {
        PhoneDTO phoneInput = new PhoneDTO("1234", "+421123456789");
        ResponseEntity<String> response = restTemplate.postForEntity(url, phoneInput, String.class);
        assertEquals("PIN je valídny.", response.getBody());
    }

    @ParameterizedTest
    @MethodSource("generateIncorrectPhoneInputs")
    void validateWithIncorrectPin(PhoneDTO phoneInput) {
        ResponseEntity<String> response = restTemplate.postForEntity(url, phoneInput, String.class);
        assertEquals("PIN nie je valídny.", response.getBody());
    }

    @ParameterizedTest
    @MethodSource("generateInvalidPhoneInputs")
    void validateWithNullPhoneNumber(PhoneDTO phoneInput) {
        ResponseEntity<String> response = restTemplate.postForEntity(url, phoneInput, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    private static Stream<PhoneDTO> generateIncorrectPhoneInputs() {
        return Stream.of(
                new PhoneDTO("1235", "+421123456789"),
                new PhoneDTO("1235", "+42177777777")
        );
    }

    private static Stream<PhoneDTO> generateInvalidPhoneInputs() {
        return Stream.of(
                new PhoneDTO("4567", null),
                new PhoneDTO(null, "+421123456789"),
                new PhoneDTO("12345", "+421123456789"),
                new PhoneDTO("abcd", "+421123456789")
        );
    }
}
