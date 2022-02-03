package me.jungboke.baekshop.controller;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class MemberForm {

    private String name;

    private String city;
    private String street;
    private String zipcode;
}
