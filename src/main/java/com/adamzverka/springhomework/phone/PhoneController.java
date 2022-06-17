package com.adamzverka.springhomework.phone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("/api/v1/phones")
public class PhoneController {

    private final PhoneService phoneService;

    @Autowired
    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    // TODO: probably rework to POST, so that it works like a login request
    // TODO: create PhoneDTO
    @GetMapping("/{phoneNumber}")
    @ResponseBody
    public ResponseEntity<String> validatePhonePin(@PathVariable String phoneNumber, @RequestParam @NotNull @Size(min = 4, max = 4) String pin) {
        if (!pin.matches("[\\d]{4}"))
            return new ResponseEntity<>("PIN musí obsahovať len čísla.", HttpStatus.BAD_REQUEST);
        boolean isValid = phoneService.validatePin(phoneNumber, pin);

        return isValid ? new ResponseEntity<>("PIN je valídny.", HttpStatus.OK) : new ResponseEntity<>("PIN nie je valídny.", HttpStatus.OK);
    }

    @GetMapping
    public List<Phone> get() {
        return phoneService.getPhones();
    }
}
