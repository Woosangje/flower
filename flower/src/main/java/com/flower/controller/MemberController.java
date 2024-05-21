package com.flower.controller;

import com.flower.dto.MemberJoinDto;
import com.flower.repository.MemberRepository;
import com.flower.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.flower.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;

import java.security.Principal;

@RequestMapping("/member")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final MemberRepository memberRepository;

    @GetMapping(value = "/join")
    public String memberForm(Model model){
        model.addAttribute("memberJoinDto", new MemberJoinDto());
        return "member/join";
    }

    @PostMapping(value = "/join")
    public String newMember(@Valid MemberJoinDto memberJoinDto, BindingResult bindingResult, Model model){
        // spring-boot-starter-validation를 활용한 검증 bindingResult객체 추가
        if(bindingResult.hasErrors()){
            return "member/join";
            // 검증 후 결과를 bindingResult에 담아 준다.
        }

        try {
            Member member = Member.createMember(memberJoinDto, passwordEncoder);
            memberService.saveMember(member);
            // 가입 처리시 이메일이 중복이면 메시지를 전달한다.
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/join";
        }

        return "redirect:/member/login";
    }

    @GetMapping(value = "/login")
    public String loginMember(){
        return "/member/login";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/login";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage")
    public String memberInfo(Model model, Principal principal) {

        String loginId = principal.getName();
        Member member = memberRepository.findByMemail(loginId);
        model.addAttribute("member", member);

        return "member/mypage";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/check-pwd")
    public String checkpwdView(){
        return "member/check-pwd";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify")
    public String updateMember(Principal principal, Model model) {

        String loginId = principal.getName();
        Member member = memberRepository.findByMemail(loginId);
        model.addAttribute("member", member);



        return "/member/modify";
    }

/*    @PostMapping(value = "/modify")
    public String updateMember(@Valid MemberUpdateDto memberUpdateDto, Model model) {

        model.addAttribute("member", memberUpdateDto);

        memberService.updateMember(memberUpdateDto);

        return "redirect:/member/mypage";
    } */

}