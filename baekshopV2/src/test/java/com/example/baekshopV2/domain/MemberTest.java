package com.example.baekshopV2.domain;

import com.example.baekshopV2.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.baekshopV2.domain.Member.createMember;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("addMember")
    void addMember() throws Exception {
        //given
        Member member = createMember("baek", new Address("11","11","11"), "test1", "12345");
        //when
        Member saveMember = memberRepository.save(member);
        Optional<Member> findMember = memberRepository.findById(saveMember.getId());
        //then
        Assertions.assertThat(saveMember).isEqualTo(findMember.get());
    }

    @Test
    @DisplayName("removeMember")
    void removeMember() throws Exception {
        //given
        Member member = createMember("baek", new Address("11","11","11"), "test2", "12345");
        //when
        Member saveMember = memberRepository.save(member);
        memberRepository.delete(saveMember);
        Optional<Member> findMember = memberRepository.findById(saveMember.getId());
        //then
        Assertions.assertThat(findMember).isNotPresent();
    }

}