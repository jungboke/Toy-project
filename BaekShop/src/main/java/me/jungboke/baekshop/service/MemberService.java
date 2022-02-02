package me.jungboke.baekshop.service;

import lombok.RequiredArgsConstructor;
import me.jungboke.baekshop.domain.Member;
import me.jungboke.baekshop.exception.ExistMemberException;
import me.jungboke.baekshop.exception.NoMemberException;
import me.jungboke.baekshop.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입
    @Transactional
    public Long join(Member member) {
        //중복회원검증
        validateDuplicateMember(member);
        Member saveMember = memberRepository.save(member);
        return saveMember.getId();
    }

    //회원전체조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원단일조회
    public Member findOne(Long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        if (!findMember.isPresent()) {
            throw new NoMemberException("회원이 존재하지 않습니다.");
        }
        return findMember.get();
    }

    private void validateDuplicateMember(Member member) {
        Optional<Member> findMember = memberRepository.findById(member.getId());
        if (findMember.isPresent()) {
            throw new ExistMemberException("이미 존재하는 회원입니다.");
        }
    }
}