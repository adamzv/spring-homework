package com.adamzverka.springhomework.phone;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class PhoneDTO implements Serializable {

    @NotNull
    @Pattern(regexp = "[\\d]{4}")
    @Schema(example = "1234")
    private String pin;

    @NotNull
    @NotEmpty
    @Schema(example = "+421123456789")
    private String phoneNumber;

    public PhoneDTO(String pin, String phoneNumber) {
        this.pin = pin;
        this.phoneNumber = phoneNumber;
    }

    // For a bigger project it may be better to create a separate class that handles mapping
    public Phone toPhone() {
        return new Phone(this.pin, this.phoneNumber);
    }
}
