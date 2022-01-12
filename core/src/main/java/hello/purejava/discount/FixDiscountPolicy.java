package hello.purejava.discount;

import hello.purejava.member.Grade;
import hello.purejava.member.Member;

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
