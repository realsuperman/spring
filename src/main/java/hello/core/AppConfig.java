package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.apache.commons.logging.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 설정정보
public class AppConfig {

    @Bean // 스프링 빈 등록시 메소드 이름으로 기본 등록됨
    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }

    //@Bean(name="mmm") // 이렇게 하면 스프링 빈 이름 변경 가능
    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService(){
        // 1번 :  return new OrderServiceImpl(new MemberRepository(),new FixDiscountPolicy())
        //return new OrderServiceImpl(memberRepository(),discountPolicy());
        return null;
    }

    @Bean
    public DiscountPolicy discountPolicy(){
        //return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}

/*
    1번처럼 쓰면 오더서비스가 어떤걸 사용하는지 명확하지 않다 따라서 1번 아래처럼 구성해야 한다
    이런식으로 구성하면 맴버서비스 구현은 MemberServiceImpl을 쓸거고 그 레포지토리는 메모리 맴버 레포지토리를 쓴다라는
    점이 잘 들어나게 되므로 이런식으로 구성하자 또한 중복도 제거 되었다 (예를 들면 new MemberRepository를 두번쓰면 수정해야 하는 부분이 2부분이다)
 */

// Configuration에 Bean등록시 스프링 컨테이너에 등록이 된다 참고로 스프링 컨테이너에 MAP이랑 유사하게 등록이 된다 키는 이름 벨류는 반환하는 객체
// 일반적으로 빈의 이름이 같으면 문제가 복잡해지니까 그냥 빈의 이름은 유니크하게 관리하자!!

// 스프링 컨테이너 사용시 자동으로 싱글톤 컨테이너 방식으로 객체를 생성해준다

// 스프링이 CGLIB라는 바이트코드 조작 라이브러리를 사용해서 우리가 만든 AppConfig 클래스를 상속받은 임의의 클래스를 만들고 그 클래스를 스프링 빈으로 등록하였다
// CGLIB 내부에서 우리의 Bean들이 싱글톤이 되도록 보장 했을 것이다
// (Bean 어노테이션만 사용해도 빈은 등록되지만 CGLIB의 도움을 받아 싱글톤 패턴으로 하고 싶으면 Configuration 어노테이션을 사용해야 한다