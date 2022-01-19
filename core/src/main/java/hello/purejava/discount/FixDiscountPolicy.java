package hello.purejava.discount;

import hello.purejava.member.Grade;
import hello.purejava.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("fixDiscountPolicy")
public class FixDiscountPolicy implements DiscountPolicy{

    private int discountFixAmount = 1000;

    @Override
    public int discount(Member member, int price) {
        if(Grade.VIP == member.getGrade()){
            return discountFixAmount;
        }

        return 0;
    }
}
