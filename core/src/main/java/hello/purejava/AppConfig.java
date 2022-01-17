package hello.purejava;

import hello.purejava.discount.DiscountPolicy;
import hello.purejava.discount.FixDiscountPolicy;
import hello.purejava.member.MemberRepository;
import hello.purejava.member.MemberService;
import hello.purejava.member.MemberServiceImpl;
import hello.purejava.member.MemoryMemberRepository;
import hello.purejava.order.OrderService;
import hello.purejava.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 외부에서 구현을 생성과 연결을 담당 (구성영역)
@Configuration
public class AppConfig {

    //@Bean memberService -> new MemoryMemberRepository()
    //@Bean orderService -> new MemoryMemberRepository()

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository()); // Dependency Injection(DI)
    }

    @Bean
    public MemberRepository memberRepository() { // 역할
        return new MemoryMemberRepository(); // 구현
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new FixDiscountPolicy();
    }
}
