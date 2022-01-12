package hello.purejava.order;

import hello.purejava.discount.DiscountPolicy;
import hello.purejava.member.MemberRepository;

public interface OrderService {
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
