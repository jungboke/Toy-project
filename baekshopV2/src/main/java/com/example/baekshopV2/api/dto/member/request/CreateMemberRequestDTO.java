package com.example.baekshopV2.api.dto.member.request;

import com.example.baekshopV2.domain.Address;
import com.example.baekshopV2.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberRequestDTO {

    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String city;
    @NotBlank
    private String street;
    @NotBlank
    private String zipcode;
    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
    private String token;

}
