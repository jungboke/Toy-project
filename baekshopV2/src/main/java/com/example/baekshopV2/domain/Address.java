package com.example.baekshopV2.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
@Getter // 값타입 참조가 발생하지 않도록 Setter 생략
@NoArgsConstructor
public class Address {

    @NotBlank
    private String city;
    @NotBlank
    private String street;
    @NotBlank
    private String zipcode;

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
