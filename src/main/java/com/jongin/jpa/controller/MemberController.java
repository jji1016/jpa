package com.jongin.jpa.controller;

import com.jongin.jpa.dto.MemberDto;
import com.jongin.jpa.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/signup")
    public String signin(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "member/signup";
    }

    @PostMapping("/signup")
    //@ResponseBody
    public String signin(@Valid @ModelAttribute MemberDto memberDto,
                         BindingResult bindingResult) {
        System.out.println("signUp inin");
        //view에 자동으로 들어간다.
        if (bindingResult.hasErrors()) {
            System.out.println("hasErrors: "+bindingResult.getAllErrors());
            return "member/signup";  //forward
        }
        //service 영역을 business logic 여기가 핵심이다.
        int result = memberService.signUp(memberDto);
        System.out.println("result===" + result);
        if (result > 0) {
            return "redirect:/";  //redirect
        }
        //여기다다 예외 상황 터트린다.
        return "member/signup";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "member/login";
    }

}
