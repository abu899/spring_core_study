package hello.purejava.discount;

import hello.purejava.member.Grade;
import hello.purejava.member.Member;
import org.springframework.stereotype.Component;

@Component
public class RateDiscountPolicy implements DiscountPolicy{
    private int discountPercent = 10;
    @Override
    public int discount(Member member, int price) {
        if(Grade.VIP == member.getGrade()){
            return price * discountPercent / 100;
        }
        return 0;
    }
}
