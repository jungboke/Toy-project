package me.jungboke.baekshop.web.controller.form;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class MemberForm {

    private String name;

    private String city;
    private String street;
    private String zipcode;

    private String loginId;
    private String password;
}
