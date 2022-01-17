package hello.purejava.order;

import hello.purejava.discount.DiscountPolicy;
import hello.purejava.member.Member;
import hello.purejava.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService { // 클라이언트 객체 ( 실행만 담당)
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(member.getId(), itemName, itemPrice, discountPrice);
    }

    //for Test
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
