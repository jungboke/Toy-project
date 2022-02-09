package me.jungboke.baekshop.web.controller.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jungboke.baekshop.domain.Member;
import me.jungboke.baekshop.repository.query.member.MemberDTO;
import me.jungboke.baekshop.repository.query.member.MemberQueryRepository;
import me.jungboke.baekshop.service.member.MemberService;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.transform.Result;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final MemberQueryRepository memberQueryRepository;

    @PostMapping("/api/members/save")
    public MemberResponse saveMember(@RequestBody @Valid CreateMemberRequest request) {

        log.info("saveMember완료");
        Member member = Member.createMember(request.getName(),null, request.getLoginId(), request.getPassword());
        Long id = memberService.join(member);
        return new MemberResponse(id);
    }

    @GetMapping("/api/members")
    public Result findMember() {
        List<MemberDTO> result = memberQueryRepository.findMemberDTO();
        return new Result(result);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    static class CreateMemberRequest {
        private String name;
        private String loginId;
        private String password;
    }

    @Data
    static class MemberResponse {
        private Long id;

        public MemberResponse(Long id) {
            this.id = id;
        }
    }
}
