package com.example.baekshopV2.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "member_id")
    private String id;

    private String name;

    @Embedded
    private Address address;

    private String loginId;
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Order> order = new ArrayList<>();

    public static Member createMember(String name, Address address, String loginId, String password) {
        Member member = new Member();
        member.name = name;
        member.address = address;
        member.loginId = loginId;
        member.password = password;
        return member;
    }

    public void update(String name) {
        this.name = name;
    }
}
