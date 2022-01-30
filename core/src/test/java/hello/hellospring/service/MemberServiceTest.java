package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    void beforeEach(){
        memberRepository= new MemoryMemberRepository();
        //dependency injection
        memberService = new MemberService(memberRepository);
    }
    @AfterEach
    void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void join() {
        //BDD
        //given: 뭔가가 주어졌는데
        Member member = new Member();
        member.setName("hello");

        //when: 이걸 실행했는데
        Long saveId = memberService.join(member);
        //then: 결과가 이게 나와야되
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void joinDuplicateMember(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("Member exists");


//        try {
//            memberService.join(member2);
//            fail();
//        } catch (IllegalStateException e){
//            assertThat(e.getMessage()).isEqualTo("Member exists");
//        }
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}