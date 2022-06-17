package com.adamzverka.springhomework.phone;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Phone implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 4)
    @NotNull
    @Pattern(regexp = "[\\d]{4}")
    private String pin;

    @Column(unique = true)
    @NotNull
    private String phoneNumber;

    public Phone(long id, String pin, String phoneNumber) {
        this.id = id;
        this.pin = pin;
        this.phoneNumber = phoneNumber;
    }
}
