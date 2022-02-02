package me.jungboke.baekshop.domain;

import me.jungboke.baekshop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("CreateMember")
    void createMember() throws Exception {
        //given
        Member member = new Member();
        member.setName("baek");
        //when
        Member saveMember = memberRepository.save(member);
        Optional<Member> findMember = memberRepository.findById(saveMember.getId());
        //then
        Assertions.assertThat(saveMember).isEqualTo(findMember.get());
    }

}