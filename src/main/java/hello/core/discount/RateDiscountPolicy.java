package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.stereotype.Component;

//@Component("memberService2") 이렇게하면 특정 이름 지정 가능
@Component
public class RateDiscountPolicy implements DiscountPolicy {
    final private int discountPercent=10;

    @Override
    public int discount(Member member, int price) {
        if(member.getGrade()== Grade.VIP){
            return price*discountPercent/100;
        }
        return 0;
    }
}
