package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor // 필수값인 final 속성에 대한 생성자를 자동으로 만들어준다
public class OrderServiceImpl implements OrderService {
    //@Autowired
    private final MemberRepository memberRepository;
    //@Autowired
    private final DiscountPolicy discountPolicy;

    //@Autowired // 생성자 주입의 경우 딱 1번만 호출됨이 보장된다(불변) -> 이렇게만 짜면 해당 값이 안바뀜
    // 이 사례처럼 생성자가 1개만 있는경우(변수값개수말고) Autowired생략 가능
/*    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }*/

    //@Autowired(required = =false) 필수 여부를 no로 하였다(꼭 안넣어줘도 된다)
/*    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
    }*/

/*    @Autowired
    public void init(MemberRepository memberRepository,DiscountPolicy discountPolicy){
        this.memberRepository=memberRepository;
        this.discountPolicy=discountPolicy;
    }*/
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member,itemPrice);
        return new Order(memberId,itemName,itemPrice,discountPrice);
    }

    //테스트용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}

/*
    생성자 주입의 경우 이 객체가 만들어지는 시점에 생성자를 호출하게 되므로 생성자 주입과 의존관계 설정이 같이 발생한다(스프링 컨테이너에 해당 값이 없으면 그걸 미리 생성한다)
    생성자 주입의 경우를 제외하면 전부 다 같은 라이프 사이클이다(빈생성->의존관계주입)

    setter 주입의 경우 set Method를 통해서 주입을 한다 -> 잘 안씀(빈이 선택적이거나 변경이 일어나는 경우만 씀)

    필드 주입의 경우 간결하지만 스프링이 없는 테스트 환경을 만들 수 없다는 단점이 있으므로 잘 안쓴다 이게 무슨말이냐면
    Autowired를 해주는건 스프링이다 즉 스프링 컨테이너의 도움 없이는 이 객체를 생성할 수 없다 그러므로 스프링 없이 테스트가 불가능하다
    약간 스프링의 설정 정보 파일및 테스트 코드에 쓰면 딱이다(설정 파일은 스프링의 도움을 받기 때문에)

    일반 메소드 주입의 경우 아무 메소드에다가 @Autowired하는 것이다 setter랑 다를게 없다

    -> 참고로 스프링 컨테이너가 관리하는 빈의 경우만 의존성 주입이 가능하다
 */

/*
    일반적으로 DI시 DI할 빈이 없으면 에러가 난다 근데 여러 옵션을 통해서 등록할 빈이 없어도 에러가 안생기게 할 수 있다
    Autowired의 경우 타입으로 빈을 조회한다
 */