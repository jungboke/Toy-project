package com.example.baekshopV2.service;

import com.example.baekshopV2.domain.Address;
import com.example.baekshopV2.domain.Member;
import com.example.baekshopV2.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.baekshopV2.domain.Member.createMember;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("가입")
    void 가입() throws Exception {
        //given
        Member member = createMember("baek", new Address("11","11","11"));
        //when
        Member saveMember = memberService.join(member);
        Optional<Member> findMember = memberRepository.findById(saveMember.getId());
        //then

        assertThat(member).isEqualTo(findMember.get());
    }

    @Test
    @DisplayName("조회")
    void 조회() throws Exception {
        //given
        Member member = createMember("baek", new Address("11","11","11"));
        //when
        Member saveMember = memberService.join(member);
        Member findMember = memberService.findOne(saveMember.getId());
        //then
        assertThat(member).isEqualTo(findMember);
    }

    @Test
    @DisplayName("업데이트")
    void 업데이트() throws Exception {
        //given
        Member member = createMember("baek", new Address("11","11","11"));
        //when
        Member saveMember = memberService.join(member);
        memberService.updateMember(saveMember.getId(),"seung");
        Member findMember = memberService.findOne(saveMember.getId());
        //then
        assertThat(findMember.getName()).isEqualTo("seung");
    }


}