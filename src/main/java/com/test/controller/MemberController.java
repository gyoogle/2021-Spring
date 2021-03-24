package com.test.controller;

import com.test.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

// Controller 어노테이션 보고, 스프링이 뜰때 해당 컨트롤러에 대한 객체를 생성를 생성하여 들고있음
// 스프링 컨테이너에서 스프링 빈이 관리된다고 말할 수 있음

@Controller
public class MemberController {

    private final MemberService memberService;

    // 스프링이 바로 MemberService를 가져다가 쓸 수 있도록 해줌

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
