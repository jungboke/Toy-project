package me.jungboke.baekshop.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jungboke.baekshop.domain.Member;
import me.jungboke.baekshop.exception.ExistMemberException;
import me.jungboke.baekshop.exception.NoMemberException;
import me.jungboke.baekshop.repository.MemberRepository;
import me.jungboke.baekshop.repository.query.member.MemberDTO;
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

    //회원가입
    @Transactional
    public Long join(Member member) {
        //중복회원검증
        validateDuplicateMember(member);
        log.info("중복검증완료");
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

    public Member login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    private void validateDuplicateMember(Member member) {
        List<Member> Member = memberRepository.findByName(member.getName());
        if (!Member.isEmpty()) {
            throw new ExistMemberException("이미 존재하는 회원입니다.");
        }
    }

}
