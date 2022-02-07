package me.jungboke.baekshop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    private String loginId;

    private String password;

    @OneToMany(mappedBy = "member")
    private List<Order> order = new ArrayList<>();

    public static Member createMember(String name, Address address, String loginId, String password) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);
        member.setLoginId(loginId);
        member.setPassword(password);
        return member;
    }
}
