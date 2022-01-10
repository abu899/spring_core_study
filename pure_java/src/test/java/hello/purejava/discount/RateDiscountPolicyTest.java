package hello.purejava.discount;

import hello.purejava.member.Grade;
import hello.purejava.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RateDiscountPolicyTest {
    DiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인이 적용되어야 한다")
    void vip_ok(){
        //given
        Member member = new Member(1L, "memberVIP", Grade.VIP);

        //when
        int discountPrice = discountPolicy.discount(member, 10000);

        //then
        assertThat(discountPrice).isEqualTo(1000);
    }

    @Test
    @DisplayName("VIP가 아니면 할인이 적용되면 안된다")
    void no_discount(){
        //given
        Member member = new Member(1L, "memberVIP", Grade.BASIC);

        //when
        int discountPrice = discountPolicy.discount(member, 10000);

        //then
        assertThat(discountPrice).isEqualTo(0);
    }
}