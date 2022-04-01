package com.example.baekshopV2.api.dto.member.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginMemberRequestDTO {

    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
}
