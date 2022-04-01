package com.example.baekshopV2.service;

import com.example.baekshopV2.domain.Member;
import com.example.baekshopV2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    //C
    @Transactional
    public Member join(final Member member) {
        validateDuplicateMember(member);
        Member saveMember = memberRepository.save(member);
        return saveMember;
    }

    //R
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //R
    public Member findOne(final String memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        if(!findMember.isPresent()) {
            throw new RuntimeException("Cannot find Entity.");
        }
        return findMember.get();
    }

    // U
    @Transactional
    public void updateMember(final String memberId, final String name) {
        Optional<Member> member = memberRepository.findById(memberId);
        if(!member.isPresent()) {
            throw new RuntimeException("Cannot find Entity.");
        }
        member.get().update(name);
    }

    // D
    @Transactional
    public void deleteMember(final String memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if(!member.isPresent()) {
            throw new RuntimeException("Cannot find Entity.");
        }
        try {
            memberRepository.delete(member.get());
        } catch (Exception e) {
            log.error("error deleting entity ", member.get().getId(), e);
            throw new RuntimeException("error deleting entity " + member.get().getId());
        }
    }

    public Member login(final String loginId, final String password, final PasswordEncoder encoder) {
        Optional<Member> member = memberRepository.findByLoginId(loginId);

        if(member.isPresent() && encoder.matches(password,member.get().getPassword())) {
            return member.get();
        }
        return null;
    }

    private void validateDuplicateMember(final Member member) {
        Optional<Member> Member = memberRepository.findByLoginId(member.getLoginId());
        if(Member.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    private void validate(final Member member) {
        if(member == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }

        if(member.getId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }
}
