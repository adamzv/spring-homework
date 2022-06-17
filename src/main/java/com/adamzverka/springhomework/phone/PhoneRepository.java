package com.adamzverka.springhomework.phone;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    boolean existsPhoneByPhoneNumberAndPin(String phoneNumber, String pin);
}
