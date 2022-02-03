package me.jungboke.baekshop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jungboke.baekshop.domain.Address;
import me.jungboke.baekshop.domain.Member;
import me.jungboke.baekshop.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        log.info("CreateForm");
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = Member.createMember(form.getName(), address);
        log.info("member 생성완료");
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String ListMember(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        log.info("ListMember");
        return "members/memberList";
    }
}
