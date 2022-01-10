package hello.purejava.order;

import hello.purejava.discount.DiscountPolicy;
import hello.purejava.discount.FixDiscountPolicy;
import hello.purejava.discount.RateDiscountPolicy;
import hello.purejava.member.Member;
import hello.purejava.member.MemberRepository;
import hello.purejava.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {
    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(member.getId(), itemName, itemPrice, discountPrice);
    }
}
