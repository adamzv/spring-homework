package com.adamzverka.springhomework.phone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/phones")
public class PhoneController {

    private final PhoneService phoneService;

    @Autowired
    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @PostMapping
    public ResponseEntity<String> validatePhonePin(@Valid @RequestBody PhoneDTO phoneDTO) {
        boolean isValid = phoneService.validatePin(phoneDTO.toPhone());
        return isValid ? new ResponseEntity<>("PIN je valídny.", HttpStatus.OK) : new ResponseEntity<>("PIN nie je valídny.", HttpStatus.OK);
    }

    @GetMapping
    public List<Phone> get() {
        return phoneService.getPhones();
    }
}
