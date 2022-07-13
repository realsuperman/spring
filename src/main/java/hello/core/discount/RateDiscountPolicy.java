package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

//@Component("memberService2") 이렇게하면 특정 이름 지정 가능
@Component
@Primary // DiscountPolicy로 조회했을 때 여러빈이 나온경우 이 빈이 우선순위를 갖는다
//@Qualifier("mainDiscountPolicy")
//@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy {
    final private int discountPercent=5;

    @Override
    public int discount(Member member, int price) {
        if(member.getGrade()== Grade.VIP){
            return price*discountPercent/100;
        }
        return 0;
    }
}
