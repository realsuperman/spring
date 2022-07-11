package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan( // @Component가 붙은 클래스를 찾아서 자동으로 스프링 빈 등록함
        basePackages = "hello.core", // 요 위치부터 시작해서 하위 디렉토리까지 컴포넌트를 찾는다(디폴트는 해당 파일이 속한 패키지부터 시작한다 여기서는 hello.core)
        // 디폴트 값을 잘 이용하기 위해서는 우리 로직이 들어가있는 패키지의 최상단에 만들면 된다 한마디로 스프링부트 main과 같은 위치에 속하면 된다
        // basePackageClasses = "XXX.class" 이렇게 지정하면 해당 클래스가 있는 패키지가 컴포넌트 스캔 대상이다
        excludeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION,classes = Configuration.class)
        /*
            위의 설정 정보는 type은 어노테이션이고 즉, Configuration이라는 어노테이션을 제외한다는 의미이다
            제외할 빈을 Configuration 어노테이션이 붙은 클래스는 제외하라고 필터링을 하였다
            수동으로 만들어둔 AppConfig는 컴포넌트 스캔의 대상이 아니다 (Configuration 어노테이션은 @Component가 있다)
         */
)
public class AutoAppConfig {
    @Bean(name="memoryMemberRepository")
    MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}

// 참고로 스프링 부트를 사용하면 이러한 Config정보를 만들 필요가 없다(왜냐면 스프링 부트 메인 프로그램인 CoreApplication에서 컴포넌트 스캔을 하기 때문이다 정확히는 @SpringBootApplication)
// 참고로 컴포넌트 스캔시 컴포넌트 이름이 겹치면 에러남
// 수동빈 vs 자동빈이면 수동빈이 우선권을 갖는다 메시지도 남겨준다(Overriding bean~~ ) 근데 요즘은 스프링 부트가 이런경우 에러를 낸다