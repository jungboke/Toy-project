package com.example.baekshopV2.api.dto.member.response;

import com.example.baekshopV2.domain.Address;
import com.example.baekshopV2.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDTO {

    private String id;
    private String name;
    private String city;
    private String street;
    private String zipcode;
    private String loginId;
    private String token;

    public MemberResponseDTO(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.city = member.getAddress().getCity();
        this.street = member.getAddress().getStreet();
        this.zipcode = member.getAddress().getZipcode();
        this.loginId = member.getLoginId();
    }

    public static MemberResponseDTO create(String id, String name, Address address, String loginId) {
        MemberResponseDTO memberDTO = new MemberResponseDTO();
        memberDTO.id = id;
        memberDTO.name = name;
        memberDTO.city = address.getCity();
        memberDTO.street = address.getStreet();
        memberDTO.zipcode = address.getZipcode();
        memberDTO.loginId = loginId;
        return memberDTO;
    }
}
