package spring.singleton;

import hello.purejava.AppConfig;
import hello.purejava.member.MemberRepository;
import hello.purejava.member.MemberService;
import hello.purejava.member.MemberServiceImpl;
import hello.purejava.order.OrderServiceImpl;
import hello.spring.singleton.SingletonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {
    @Test
    @DisplayName("DI container without spring")
    void pureContainer(){
        AppConfig appConfig = new AppConfig();

        MemberService memberService1 = appConfig.memberService();
        MemberService memberService2 = appConfig.memberService();

        // 참조값 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("Singleton pattern test")
    void singletonPatternTest(){
        // 밖에서는 singletonService를 호출할 수 없다!
        // SingletonService singletonService = new SingletonService();

        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        // 참조값 확인
        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);

        assertThat(singletonService1).isEqualTo(singletonService2); // Equal은 equals를 비교하는 것
        assertThat(singletonService1).isSameAs(singletonService2); // Same은 "=="를 수행하는 것

        singletonService1.logic();
    }

    @Test
    @DisplayName("Spring container test")
    void springContainerTest(){
//        AppConfig appConfig = new AppConfig();
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        // 참조값 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        assertThat(memberService1).isSameAs(memberService2);
    }

    @Test
    @DisplayName("AppConfig Configuration singleton test")
    void configurationTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        System.out.println("memberService.getMemberRepository() = " + memberService.getMemberRepository());
        System.out.println("orderService.getMemberRepository() = " + orderService.getMemberRepository());
        System.out.println("memberRepository = " + memberRepository);
        
        assertThat(memberService.getMemberRepository()).isEqualTo(orderService.getMemberRepository());
        assertThat(memberService.getMemberRepository()).isEqualTo(memberRepository);
        assertThat(orderService.getMemberRepository()).isEqualTo(memberRepository);
    }

    @Test
    @DisplayName("Configuration deep dive")
    void configurationDeepTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean.getClass() = " + bean.getClass());
    }
}

