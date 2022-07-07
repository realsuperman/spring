package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {
    AppConfig appConfig = new AppConfig();
    MemberService memberService;
    OrderService orderService;

    @BeforeEach // 테스트 시작전 실행됨
    public void beforeEach(){
        AppConfig appconfig = new AppConfig();
        memberService = appconfig.memberService();
        orderService = appconfig.orderService();
    }
   @Test
   void createOrder(){
       Long memberId = 1L;
       Member member = new Member(memberId,"memberA",Grade.VIP);
       memberService.join(member);
       Order order = orderService.createOrder(memberId,"ItemA",10000);
       Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
   }
}
