package com.example.baekshopV2.api.controller;

import com.example.baekshopV2.api.dto.member.request.CreateMemberRequestDTO;
import com.example.baekshopV2.api.dto.member.request.LoginMemberRequestDTO;
import com.example.baekshopV2.api.dto.member.request.UpdateMemberRequestDTO;
import com.example.baekshopV2.api.dto.member.response.MemberResponseDTO;
import com.example.baekshopV2.domain.Address;
import com.example.baekshopV2.domain.Member;
import com.example.baekshopV2.security.TokenProvider;
import com.example.baekshopV2.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // C signin
    @PostMapping("/auth/v1/members/signin")
    public ResponseEntity<?> saveMemberV1(@RequestBody @Validated CreateMemberRequestDTO DTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.info("검증 오류 발생 error = {}", bindingResult);
            throw new IllegalArgumentException();
        }
        Member member = Member.createMember(DTO.getName(), new Address(DTO.getCity(), DTO.getStreet(), DTO.getZipcode()),
                                            DTO.getLoginId(), passwordEncoder.encode(DTO.getPassword()));
        Member saveMember = memberService.join(member);
        MemberResponseDTO dto = MemberResponseDTO.create(saveMember.getId(), saveMember.getName(),
                saveMember.getAddress(), saveMember.getLoginId());
        log.info("멤버 생성 완료");

        return ResponseEntity.ok(dto);
    }

    // signup
    @PostMapping("/auth/v1/members/signup")
    public ResponseEntity<?> authenticate(@RequestBody @Validated LoginMemberRequestDTO DTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.info("검증 오류 발생 error = {}", bindingResult);
            throw new IllegalArgumentException();
        }
        Member member = memberService.login(DTO.getLoginId(), DTO.getPassword(),passwordEncoder);
        if(member != null) {
            String token = tokenProvider.create(member);
            MemberResponseDTO dto = MemberResponseDTO.create(member.getId(),  member.getName(),
                    member.getAddress(), member.getLoginId());
            dto.setToken(token);
            log.info("로그인 완료");

            return ResponseEntity.ok(dto);
        }
        else {
            throw new IllegalStateException();
        }
    }

    // R
    @GetMapping("/v1/members")
    public ResponseEntity<?> findMembersV1() {
        List<Member> members = memberService.findMembers();
        List<MemberResponseDTO> dtos = members.stream().map(MemberResponseDTO::new).collect(Collectors.toList());
        log.info("멤버 리스트 조회 완료");

        return ResponseEntity.ok(dtos);
    }

    // R
    @GetMapping("/v1/members/{id}")
    public ResponseEntity<?> findMemberV1(@PathVariable("id") String id) {
        Member findMember = memberService.findOne(id);
        MemberResponseDTO dto = MemberResponseDTO.create(findMember.getId(), findMember.getName(), findMember.getAddress(),
                findMember.getLoginId());
        log.info("멤버 단일 조회 완료");

        return ResponseEntity.ok(dto);
    }

    // U
    @PutMapping("/v1/members/update")
    public ResponseEntity<?> updateMemberV1(@RequestBody @Validated UpdateMemberRequestDTO DTO,
                                            BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.info("검증 오류 발생 error = {}", bindingResult);
            throw new IllegalArgumentException();
        }
        memberService.updateMember(DTO.getId(), DTO.getName());
        Member findMember = memberService.findOne(DTO.getId());
        MemberResponseDTO dto = MemberResponseDTO.create(findMember.getId(), findMember.getName(),
                findMember.getAddress(),findMember.getLoginId());
        log.info("멤버 수정 완료");

        return ResponseEntity.ok(dto);
    }

    //D
    @DeleteMapping("/v1/members/delete/{id}")
    public ResponseEntity<?> deleteMemberV1(@PathVariable("id") String id) {
        memberService.deleteMember(id);
        log.info("멤버 삭제 완료");

        return ResponseEntity.ok(id);
    }
}
