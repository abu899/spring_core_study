package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

//controller는 service에 의존적

@Controller
public class MemberController {
    private final MemberService memberService;

    // 필드 주입(DI)
    // 필드 주입은 spring 뜰때만 넣어주고 중간에 바꾸거나 할 수 없음.
    //@Autowired private MemberService memberService;

    // 세터 주입(DI)
    // setMemberService가 노출되어 있어야됨.
    // 한번 셋팅하고 나면 바꿀필요가 없는데, 중간에 잘못바꾸게 되면 문제가 됨.
    // 즉, 의존관계가 실행 중에 동적으로 변하는 경우는 거의 없기에! 생성자 주입 추천
//    private MemberService memberService
//    @Autowired
//    public void setMemberService(MemberService service){
//        this.memberService = service;
//    }

    //member service는 하나만 존재하고 다른데서 공용으로 쓰면되므로
    //spring container에 등록해서 쓰면 됨
    //dependency injection -> spring이 component scan을 하고 component를 넣어줌
    @Autowired
    public MemberController(MemberService memberService) { //생성자 주입 (DI)
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMember";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
